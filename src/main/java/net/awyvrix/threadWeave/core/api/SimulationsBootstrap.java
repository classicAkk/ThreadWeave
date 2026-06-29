package net.awyvrix.threadWeave.core.api;

import net.awyvrix.threadWeave.core.api.annotation.Simulations;

public final class SimulationsBootstrap {
    private static final SimulationInitContext CONTEXT = new SimulationInitContext();

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;
        Simulations.init(CONTEXT.getRuntime(), CONTEXT.getInstanceRegistry(), CONTEXT.getTypeRegistry());
    }

    public static SimulationInitContext getContext() {
        return CONTEXT;
    }
}