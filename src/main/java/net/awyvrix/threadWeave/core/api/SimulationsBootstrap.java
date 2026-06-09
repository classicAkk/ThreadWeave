package net.awyvrix.threadWeave.core.api;

import net.awyvrix.threadWeave.core.api.annotation.Simulations;
import net.awyvrix.threadWeave.core.api.binding.SimulationBindingRegistry;
import net.awyvrix.threadWeave.core.api.executor.DirectSimulationExecutor;
import net.awyvrix.threadWeave.core.api.executor.SimulationExecutorService;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstanceRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationTypeRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationUnitRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationRuntime;
import net.awyvrix.threadWeave.core.api.scheluder.SimulationScheduler;
import net.awyvrix.threadWeave.core.api.unit.UnitFactory;

public final class SimulationsBootstrap {
    public static UnitFactory unitFactory;

    public static void init() {
        SimulationBindingRegistry bindingRegistry = new SimulationBindingRegistry();
        SimulationTypeRegistry typeRegistry = new SimulationTypeRegistry();
        SimulationUnitRegistry unitRegistry = new SimulationUnitRegistry();
        SimulationInstanceRegistry instanceRegistry = new SimulationInstanceRegistry();

        SimulationScheduler scheduler = new SimulationScheduler(
                typeRegistry, unitRegistry,
                new DirectSimulationExecutor(), new SimulationExecutorService(12),
                instanceRegistry, bindingRegistry
        );
        SimulationRuntime runtime = new SimulationRuntime(scheduler, bindingRegistry);
        Simulations.init(runtime, instanceRegistry, typeRegistry);

        unitFactory = new UnitFactory(unitRegistry, instanceRegistry, typeRegistry);
    }
}