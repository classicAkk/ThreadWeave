package net.awyvrix.threadWeave;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final int availableThreads = Runtime.getRuntime().availableProcessors();

    public static final ModConfigSpec.IntValue MAX_THREADS = BUILDER
            .comment(" Maximal count of active threads (1 - 256)",
                    " Recommended to set this value equals to your CPU cores",
                    " Delete this cfg file to automatically set threads count to max CPU cores",
                    " Warning! Setting more than your CPU cores may highly reduce performance")
            .defineInRange("max_threads", availableThreads, 1, 256);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static void validateThreads() {
        if (MAX_THREADS.get() > availableThreads) {
            ThreadWeave.LOGGER.warn("ThreadWeave: Configured thread count ({}) is dangerously higher than available CPU cores ({}). Performance may degrade",
                    MAX_THREADS.get(), availableThreads);
        }
    }
}
