package net.awyvrix.threadWeave.core.api.annotation;

import net.awyvrix.threadWeave.core.api.SimulationsBootstrap;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstanceRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationTypeRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationRuntime;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

public final class Simulations {
    private static SimulationScanner SCANNER;
    private static  SimulationInstanceRegistry INSTANCE_REGISTRY;
    private static SimulationRuntime runtime;

    public static void init(SimulationRuntime runtimeInstance, SimulationInstanceRegistry instanceRegistry, SimulationTypeRegistry typeRegistry) {
        INSTANCE_REGISTRY = instanceRegistry;
        runtime = runtimeInstance;
        SCANNER = new SimulationScanner(INSTANCE_REGISTRY, typeRegistry);
    }

    public static void bootstrapScan(Class<?>... owners) {
        for (Class<?> owner : owners) {
            SCANNER.scan(owner);
        }
    }

    public static void tick() {
        if (runtime == null) throw new IllegalStateException("Simulations not initialized");
        runtime.tick();
    }

    public static void submit(Object source) {
        SimulationUnit unit = SimulationsBootstrap.getContext().getUnitFactory().create(source);
        runtime.bind(unit.id(), source);
    }

    public static SimulationInstanceRegistry registry() {
        return INSTANCE_REGISTRY;
    }
}