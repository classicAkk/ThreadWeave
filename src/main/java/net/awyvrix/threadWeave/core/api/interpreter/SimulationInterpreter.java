package net.awyvrix.threadWeave.core.api.interpreter;

import net.awyvrix.threadWeave.core.api.runtime.SimulationDelta;

public interface SimulationInterpreter<D extends SimulationDelta, T> {
    void apply(D delta, T target);
}