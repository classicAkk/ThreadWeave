package net.awyvrix.threadWeave.core.api.runtime;

import net.awyvrix.threadWeave.core.api.binding.SimulationBindingRegistry;
import net.awyvrix.threadWeave.core.api.scheluder.SimulationScheduler;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnitId;

public final class SimulationRuntime {
    private final SimulationScheduler scheduler;
    private final SimulationBindingRegistry bindingRegistry;

    public SimulationRuntime(SimulationScheduler scheduler, SimulationBindingRegistry bindingRegistry) {
        this.scheduler = scheduler;
        this.bindingRegistry = bindingRegistry;
    }

    public void register(SimulationUnit unit) {
        scheduler.register(unit);
    }

    public void bind(SimulationUnitId id, Object be) {
        bindingRegistry.bind(id, be);
    }

    public void tick() {
        scheduler.tick();
    }
}