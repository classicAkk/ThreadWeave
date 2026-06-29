package net.awyvrix.threadWeave.core.api.executor;

import net.awyvrix.threadWeave.core.api.instance.SimulationInstance;
import net.awyvrix.threadWeave.core.api.runtime.*;

public final class DirectSimulationExecutor {
    public <D> SimulationResult<D> execute(SimulationInstance<?, D> instance, Object target, Object dto, SimulationContext context) {
        long start = System.nanoTime();
        D delta = instance.execute(target, dto);
        //System.out.println("[" + Thread.currentThread().getName() + "] ");

        return new SimulationResult<>(delta, System.nanoTime() - start);
    }
}