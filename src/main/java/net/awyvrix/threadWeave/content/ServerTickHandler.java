package net.awyvrix.threadWeave.content;

import net.awyvrix.threadWeave.ThreadWeave;
import net.awyvrix.threadWeave.core.api.annotation.Simulations;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = ThreadWeave.MOD_ID)
public final class ServerTickHandler {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Simulations.tick();
    }
}