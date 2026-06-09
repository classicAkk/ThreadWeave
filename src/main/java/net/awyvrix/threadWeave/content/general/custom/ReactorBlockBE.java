package net.awyvrix.threadWeave.content.general.custom;

import net.awyvrix.threadWeave.ThreadWeave;
import net.awyvrix.threadWeave.content.ThreadBlockEntities;
import net.awyvrix.threadWeave.content.TickableBE;
import net.awyvrix.threadWeave.core.api.TickMode;
import net.awyvrix.threadWeave.core.api.annotation.SimulatedThread;
import net.awyvrix.threadWeave.core.api.annotation.Simulations;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorBlockBE extends BlockEntity implements TickableBE {
    private int heat;
    private int fuel;

    public ReactorBlockBE(BlockPos pos, BlockState state) {
        super(ThreadBlockEntities.REACTOR_BE.get(), pos, state);
    }

    public int getHeat() {
        return heat;
    }

    public int getFuel() {
        return fuel;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    @SimulatedThread(value = ThreadWeave.MOD_ID + ":reactor", mode = TickMode.WORLD_SYNCED)
    public ReactorData simulate(ReactorData data) {
        for (int i = 0; i < 10; i++) {
            //Calculations.heavyProcessor(worldPosition);
        }
        //int heat = data.heat() + 20;
        //int fuel = data.fuel() - 1;
        return new ReactorData(heat, fuel);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide()) return;
        for (int i = 0; i < 10; i++) {
            Calculations.heavyProcessor(worldPosition);
        }

        //Simulations.submit(this);
        //System.out.println("heat=" + this.getHeat() + " fuel=" + this.getFuel());
    }
}