package net.awyvrix.threadWeave.core.api.binding;

import net.awyvrix.threadWeave.core.api.unit.SimulationUnitId;

import java.util.HashMap;
import java.util.Map;

public final class SimulationBindingRegistry {
    private final Map<SimulationUnitId, Object> bindings = new HashMap<>();

    public <T> void bind(SimulationUnitId id, T target) {
        bindings.put(id, target);
    }

    public void unbind(SimulationUnitId id) {
        bindings.remove(id);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(SimulationUnitId id) {
        return (T) bindings.get(id);
    }
}