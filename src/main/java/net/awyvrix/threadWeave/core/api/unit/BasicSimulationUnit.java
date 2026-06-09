package net.awyvrix.threadWeave.core.api.unit;

import net.awyvrix.threadWeave.core.api.runtime.SimulationTypeId;

public record BasicSimulationUnit(SimulationUnitId id, SimulationTypeId typeId) implements SimulationUnit {}