package net.awyvrix.threadWeave.core.api.scheluder;

import net.awyvrix.threadWeave.core.api.TickMode;
import net.awyvrix.threadWeave.core.api.binding.SimulationBindingRegistry;
import net.awyvrix.threadWeave.core.api.executor.DirectSimulationExecutor;
import net.awyvrix.threadWeave.core.api.executor.SimulationExecutorService;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstance;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstanceRegistry;
import net.awyvrix.threadWeave.core.api.interpreter.AutoInterpreter;
import net.awyvrix.threadWeave.core.api.interpreter.CompletedSimulation;
import net.awyvrix.threadWeave.core.api.interpreter.SimulationInterpreterRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationTypeRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationUnitRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationContext;
import net.awyvrix.threadWeave.core.api.runtime.SimulationResult;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnitTask;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnitType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class SimulationScheduler {
    private final SimulationTypeRegistry registry;
    private final DirectSimulationExecutor executor;
    private final SimulationExecutorService executorService;

    private final SimulationBindingRegistry bindingRegistry;
    private final SimulationInstanceRegistry instanceRegistry;

    private final SimulationUnitRegistry unitRegistry;
    private final List<SimulationUnit> worldUnits = new ArrayList<>();
    private final List<SimulationUnit> isolatedUnits = new ArrayList<>();

    private final SimulationTaskFactory taskFactory;
    private final Queue<CompletedSimulation<?>> results = new ConcurrentLinkedQueue<>();
    private long tick;

    public SimulationScheduler(SimulationTypeRegistry registry, SimulationUnitRegistry unitRegistry,
                               DirectSimulationExecutor executor, SimulationExecutorService executorService,
                               SimulationInstanceRegistry instanceRegistry, SimulationBindingRegistry bindingRegistry) {
        this.registry = registry;
        this.executor = executor;
        this.unitRegistry = unitRegistry;
        this.executorService = executorService;
        this.instanceRegistry = instanceRegistry;
        this.bindingRegistry = bindingRegistry;
        this.taskFactory = new SimulationTaskFactory(registry, instanceRegistry, bindingRegistry, executor);
    }

    public void register(SimulationUnit unit) {
        unitRegistry.register(unit);
    }

    public void tick() {
        tick++;
        groupUnits(unitRegistry.all());

        executeWorldSync();
        executeIsolated();

        applyResults();
    }

    private void executeWorldSync() {
        List<Callable<CompletedSimulation<?>>> tasks = new ArrayList<>();

        for (SimulationUnit unit : worldUnits) {
            tasks.add(taskFactory.create(unit, tick));
        }

        invoke(tasks);
    }

    private void executeIsolated() {
        List<Callable<CompletedSimulation<?>>> tasks = new ArrayList<>();

        for (SimulationUnit unit : isolatedUnits) {
            SimulationUnitType<?> type = registry.get(unit.typeId());
            int rate = type.isolatedRate();

            if (rate > 1 && tick % rate != 0) continue;
            tasks.add(taskFactory.create(unit, tick));
        }

        invoke(tasks);
    }

    private void groupUnits(Collection<SimulationUnit> all) {
        worldUnits.clear();
        isolatedUnits.clear();

        for (SimulationUnit unit : all) {
            SimulationUnitType<?> type = registry.get(unit.typeId());
            if (type.tickMode() == TickMode.ISOLATED) {
                isolatedUnits.add(unit);
            } else {
                worldUnits.add(unit);
            }
        }
    }

    private void invoke(Collection<Callable<CompletedSimulation<?>>> tasks) {
        try {
            List<Future<CompletedSimulation<?>>> futures = executorService.executor().invokeAll(tasks);

            for (Future<CompletedSimulation<?>> f : futures) {
                results.add(f.get());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {
            throw new RuntimeException("Simulation execution failed", e.getCause());
        }
    }

    private void applyResults() {
        CompletedSimulation<?> completed;

        while ((completed = results.poll()) != null) {
            SimulationUnit unit = completed.unit();
            Object target = bindingRegistry.get(unit.id());

            if (target == null) continue;
            SimulationInstance<?, ?> instance = instanceRegistry.get(target.getClass());
            instance.apply(target, completed.result().delta());
        }
    }
}