package net.awyvrix.threadWeave.core.api.extractor;

import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

public interface SimulationExtractor<T, U extends SimulationUnit> {
    U extract(T target);
}