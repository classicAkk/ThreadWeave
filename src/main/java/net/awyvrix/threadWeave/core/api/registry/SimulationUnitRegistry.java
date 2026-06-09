package net.awyvrix.threadWeave.core.api.registry;

import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnitId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class SimulationUnitRegistry {
    private final Map<SimulationUnitId, SimulationUnit> units = new HashMap<>();

    public void register(SimulationUnit unit) {
        units.put(unit.id(), unit);
    }

    public SimulationUnit get(SimulationUnitId id) {
        return units.get(id);
    }

    public void remove(SimulationUnitId id) {
        units.remove(id);
    }

    public Collection<SimulationUnit> all() {
        return units.values();
    }

    public boolean contains(SimulationUnitId id) {
        return units.containsKey(id);
    }

    public int size() {
        return units.size();
    }
}