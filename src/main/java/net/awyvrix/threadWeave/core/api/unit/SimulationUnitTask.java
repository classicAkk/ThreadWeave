package net.awyvrix.threadWeave.core.api.unit;

import net.awyvrix.threadWeave.core.api.runtime.SimulationContext;

public interface SimulationUnitTask<T, D> {
    Class<T> inputType();
    D execute(T unit, SimulationContext context);
}