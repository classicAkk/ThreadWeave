package net.awyvrix.threadWeave;

import net.awyvrix.threadWeave.core.api.SimulationsBootstrap;
import net.awyvrix.threadWeave.core.api.registry.SimulationUnitRegistry;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ThreadWeave.MOD_ID)
public class ThreadWeave {
    public static final String MOD_ID = "thread_weave";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final SimulationUnitRegistry register = new SimulationUnitRegistry();

    public ThreadWeave(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        SimulationsBootstrap.init();

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Setting up Isolated Simulations...");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Isolated Simulations Set-upped");
    }
}