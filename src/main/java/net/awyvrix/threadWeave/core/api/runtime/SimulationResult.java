package net.awyvrix.threadWeave.core.api.runtime;

public record SimulationResult<D>(D delta, long executionTimeNs) {}