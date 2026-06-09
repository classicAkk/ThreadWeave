package net.awyvrix.threadWeave.core.api.interpreter;

import net.awyvrix.threadWeave.core.api.runtime.SimulationResult;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

public record CompletedSimulation<D>(SimulationUnit unit, SimulationResult<D> result) {}