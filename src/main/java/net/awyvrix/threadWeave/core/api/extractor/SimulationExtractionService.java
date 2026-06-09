package net.awyvrix.threadWeave.core.api.extractor;

import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

public final class SimulationExtractionService {
    private final SimulationExtractorRegistry registry;

    public SimulationExtractionService(SimulationExtractorRegistry registry) {
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    public <T> SimulationUnit extract(T source) {
        SimulationExtractor<T, ?> extractor = (SimulationExtractor<T, ?>) registry.get(source.getClass());
        return extractor.extract(source);
    }
}