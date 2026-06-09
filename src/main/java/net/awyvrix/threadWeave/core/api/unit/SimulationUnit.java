package net.awyvrix.threadWeave.core.api.unit;

import net.awyvrix.threadWeave.core.api.runtime.SimulationTypeId;

public interface SimulationUnit {
    SimulationUnitId id();
    SimulationTypeId typeId();
}