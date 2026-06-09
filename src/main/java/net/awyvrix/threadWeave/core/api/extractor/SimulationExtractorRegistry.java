package net.awyvrix.threadWeave.core.api.extractor;

import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

import java.util.HashMap;
import java.util.Map;

public final class SimulationExtractorRegistry {
    private final Map<Class<?>, SimulationExtractor<?, ?>> extractors = new HashMap<>();

    public <T, U extends SimulationUnit> void register(Class<T> sourceType, SimulationExtractor<T, U> extractor) {
        extractors.put(sourceType, extractor);
    }

    @SuppressWarnings("unchecked")
    public <T, U extends SimulationUnit>
    SimulationExtractor<T, U> get(Class<T> sourceType) {
        return (SimulationExtractor<T, U>) extractors.get(sourceType);
    }
}