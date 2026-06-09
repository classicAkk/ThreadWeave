package net.awyvrix.threadWeave.core.api.interpreter;

import net.awyvrix.threadWeave.core.api.runtime.SimulationDelta;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

import java.util.HashMap;
import java.util.Map;

public final class SimulationInterpreterRegistry {
    private final Map<Class<?>, SimulationInterpreter<?, ?>> interpreters = new HashMap<>();

    public void register(Class<?> type, SimulationInterpreter<?, ?> interpreter) {
        interpreters.put(type, interpreter);
    }

    @SuppressWarnings("unchecked")
    public <T, D extends SimulationDelta>
    SimulationInterpreter<D, T> get(Class<T> type) {
        return (SimulationInterpreter<D, T>) interpreters.get(type);
    }
}