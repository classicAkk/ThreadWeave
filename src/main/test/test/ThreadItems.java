package net.awyvrix.threadWeave.content.test;

import net.awyvrix.threadWeave.ThreadWeave;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ThreadItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThreadWeave.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}