package net.awyvrix.threadWeave.core.api;

import net.awyvrix.threadWeave.core.api.binding.SimulationBindingRegistry;
import net.awyvrix.threadWeave.core.api.executor.DirectSimulationExecutor;
import net.awyvrix.threadWeave.core.api.executor.SimulationExecutorService;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstanceRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationTypeRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationUnitRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationRuntime;
import net.awyvrix.threadWeave.core.api.scheluder.SimulationScheduler;
import net.awyvrix.threadWeave.core.api.unit.UnitFactory;

import static net.awyvrix.threadWeave.Config.MAX_THREADS;

public final class SimulationInitContext {
    private final SimulationBindingRegistry bindingRegistry;
    private final SimulationTypeRegistry typeRegistry;
    private final SimulationUnitRegistry unitRegistry;
    private final SimulationInstanceRegistry instanceRegistry;
    private final SimulationScheduler scheduler;
    private final SimulationRuntime runtime;
    private final UnitFactory unitFactory;

    public SimulationInitContext() {
        this.bindingRegistry = new SimulationBindingRegistry();
        this.typeRegistry = new SimulationTypeRegistry();
        this.unitRegistry = new SimulationUnitRegistry();
        this.instanceRegistry = new SimulationInstanceRegistry();

        this.scheduler = new SimulationScheduler(
                typeRegistry, unitRegistry,
                new DirectSimulationExecutor(), new SimulationExecutorService(MAX_THREADS.get()),
                instanceRegistry, bindingRegistry
        );
        this.runtime = new SimulationRuntime(scheduler, bindingRegistry);
        this.unitFactory = new UnitFactory(unitRegistry, instanceRegistry, typeRegistry);
    }

    public SimulationBindingRegistry getBindingRegistry() {
        return bindingRegistry;
    }

    public SimulationTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    public SimulationUnitRegistry getUnitRegistry() {
        return unitRegistry;
    }

    public SimulationInstanceRegistry getInstanceRegistry() {
        return instanceRegistry;
    }

    public SimulationScheduler getScheduler() {
        return scheduler;
    }

    public SimulationRuntime getRuntime() {
        return runtime;
    }

    public UnitFactory getUnitFactory() {
        return unitFactory;
    }
}