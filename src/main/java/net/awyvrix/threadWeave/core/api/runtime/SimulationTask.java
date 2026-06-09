package net.awyvrix.threadWeave.core.api.runtime;

public interface SimulationTask<S extends SimulationState, D> {
    D execute(S state, SimulationContext context);
}