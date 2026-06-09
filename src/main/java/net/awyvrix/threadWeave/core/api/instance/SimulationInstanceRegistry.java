package net.awyvrix.threadWeave.core.api.instance;

import java.util.HashMap;
import java.util.Map;

public final class SimulationInstanceRegistry {
    private final Map<Class<?>, SimulationInstance<?, ?>> instances = new HashMap<>();

    public void register(Class<?> owner, SimulationInstance<?, ?> instance) {
        instances.put(owner, instance);
    }

    @SuppressWarnings("unchecked")
    public <I, O> SimulationInstance<I, O> get(Class<?> owner) {
        return (SimulationInstance<I, O>) instances.get(owner);
    }
}