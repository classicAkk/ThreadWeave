package net.awyvrix.threadWeave.content.test.general.custom;

import net.awyvrix.threadWeave.ThreadWeave;
import net.awyvrix.threadWeave.content.test.ThreadBlockEntities;
import net.awyvrix.threadWeave.content.test.TickableBE;
import net.awyvrix.threadWeave.core.api.TickMode;
import net.awyvrix.threadWeave.core.api.annotation.SimulatedThread;
import net.awyvrix.threadWeave.core.api.annotation.Simulations;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static net.awyvrix.threadWeave.ThreadWeave.MOD_ID;

public class ReactorBlockBE extends BlockEntity implements TickableBE {
    private int heat;
    private int fuel;

    public ReactorBlockBE(BlockPos pos, BlockState state) {
        super(ThreadBlockEntities.REACTOR_BE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide()) return;
        Simulations.submit(this);
    }

    @SimulatedThread(value = MOD_ID + ":reactor", mode = TickMode.WORLD_SYNCED)
    public ReactorData simulate(ReactorData data) {
        int heat = data.heat() + 20;
        int fuel = data.fuel() - 1;
        return new ReactorData(heat, fuel);
    }
}