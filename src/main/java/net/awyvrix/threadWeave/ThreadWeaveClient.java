package net.awyvrix.threadWeave;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = ThreadWeave.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ThreadWeave.MOD_ID, value = Dist.CLIENT)
public class ThreadWeaveClient {
    public ThreadWeaveClient(ModContainer container) {}

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ThreadWeave.LOGGER.info("Setting up client isolated simulations...");
    }
}
