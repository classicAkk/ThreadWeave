package net.awyvrix.threadWeave.core.threading;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static net.awyvrix.threadWeave.ThreadWeave.LOGGER;

public final class SimulationThreadFactory implements ThreadFactory {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "Simulated thread-" + COUNTER.incrementAndGet());
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler((t, e) -> LOGGER.error("Simulation thread crashed", e));
        System.out.println("Created new thread");

        return thread;
    }
}