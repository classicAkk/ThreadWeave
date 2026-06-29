package net.awyvrix.threadWeave.content.test;

import net.awyvrix.threadWeave.ThreadWeave;
import net.awyvrix.threadWeave.content.test.general.custom.ReactorBlockBE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ThreadBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ThreadWeave.MOD_ID);

    public static final Supplier<BlockEntityType<ReactorBlockBE>> REACTOR_BE =
            BLOCK_ENTITIES.register("reactor_block",
                    ()-> BlockEntityType.Builder.of(ReactorBlockBE::new, ThreadBlocks.REACTOR_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}