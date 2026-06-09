package net.awyvrix.threadWeave.core.api.interpreter;

import net.awyvrix.threadWeave.core.api.binding.SimulationBindingRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationDelta;
import net.awyvrix.threadWeave.core.api.runtime.SimulationResult;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

public final class SimulationApplyService {
    private final SimulationInterpreterRegistry interpreters;
    private final SimulationBindingRegistry bindings;

    public SimulationApplyService(SimulationInterpreterRegistry interpreters, SimulationBindingRegistry bindings) {
        this.interpreters = interpreters;
        this.bindings = bindings;
    }

    @SuppressWarnings("unchecked")
    public <D extends SimulationDelta> void apply(SimulationUnit unit, SimulationResult<D> result) {
        Object target = bindings.get(unit.id());

        if (target == null) return;
        SimulationInterpreter<D, Object> interpreter = (SimulationInterpreter<D, Object>) interpreters.get(target.getClass());
        interpreter.apply(result.delta(), target);
    }
}
