package net.awyvrix.threadWeave.content;

import net.awyvrix.threadWeave.Config;
import net.awyvrix.threadWeave.ThreadWeave;
import net.awyvrix.threadWeave.core.api.SimulationsBootstrap;
import net.awyvrix.threadWeave.core.api.annotation.Simulations;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = ThreadWeave.MOD_ID)
public final class ServerTickHandler {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Simulations.tick();
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == Config.SPEC) {
            Config.validateThreads();
            SimulationsBootstrap.init();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == Config.SPEC) {
            Config.validateThreads();
            ThreadWeave.LOGGER.warn("To update active threads count, restart Minecraft");
        }
    }
}