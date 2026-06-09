package net.awyvrix.threadWeave.core.api.registry;

import net.awyvrix.threadWeave.core.api.runtime.SimulationTypeId;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnitType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SimulationTypeRegistry {
    private final Map<SimulationTypeId, SimulationUnitType<?>> types = new HashMap<>();

    public <U extends SimulationUnit> void register(SimulationTypeId id, SimulationUnitType<U> type) {
        types.put(id, type);
    }

    @SuppressWarnings("unchecked")
    public <U extends SimulationUnit> SimulationUnitType<U> get(SimulationTypeId id) {
        return (SimulationUnitType<U>) types.get(id);
    }

    public Set<SimulationTypeId> allIds() {
        return types.keySet();
    }

    public SimulationUnitType<?> rawGet(SimulationTypeId id) {
        return types.get(id);
    }
}