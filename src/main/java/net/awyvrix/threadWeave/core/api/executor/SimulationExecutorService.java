package net.awyvrix.threadWeave.core.api.executor;

import net.awyvrix.threadWeave.core.threading.SimulationThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SimulationExecutorService {
    private final ExecutorService executor;

    public SimulationExecutorService(int threads) {
        this.executor = Executors.newFixedThreadPool(threads, new SimulationThreadFactory());
    }

    public ExecutorService executor() {
        return executor;
    }

    public void shutdown() {
        executor.shutdown();
    }
}