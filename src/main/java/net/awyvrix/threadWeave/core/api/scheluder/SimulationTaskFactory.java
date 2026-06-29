package net.awyvrix.threadWeave.core.api.scheluder;

import net.awyvrix.threadWeave.core.api.binding.SimulationBindingRegistry;
import net.awyvrix.threadWeave.core.api.executor.DirectSimulationExecutor;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstance;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstanceRegistry;
import net.awyvrix.threadWeave.core.api.interpreter.CompletedSimulation;
import net.awyvrix.threadWeave.core.api.registry.SimulationTypeRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationContext;
import net.awyvrix.threadWeave.core.api.runtime.SimulationResult;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

import java.util.concurrent.Callable;

public final class SimulationTaskFactory {
    private final SimulationTypeRegistry registry;
    private final SimulationInstanceRegistry instanceRegistry;
    private final SimulationBindingRegistry bindingRegistry;
    private final DirectSimulationExecutor executor;

    public SimulationTaskFactory(SimulationTypeRegistry registry, SimulationInstanceRegistry instanceRegistry, SimulationBindingRegistry bindingRegistry,
            DirectSimulationExecutor executor) {
        this.registry = registry;
        this.instanceRegistry = instanceRegistry;
        this.bindingRegistry = bindingRegistry;
        this.executor = executor;
    }

    public Callable<CompletedSimulation<?>> create(SimulationUnit unit, long tick) {
        return () -> {
            Object target = bindingRegistry.get(unit.id());

            if (target == null) return null;
            SimulationInstance<?, ?> instance = instanceRegistry.get(target.getClass());

            if (instance == null) throw new IllegalStateException("No simulation instance for " + target.getClass().getName());
            Object dto = instance.extract(target);
            SimulationResult<?> result = executor.execute(instance, target, dto, new SimulationContext(tick));

            return new CompletedSimulation<>(unit, result);
        };
    }
}