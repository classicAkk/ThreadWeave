package net.awyvrix.threadWeave.core.api.instance;

import net.awyvrix.threadWeave.core.api.extractor.AutoExtractor;
import net.awyvrix.threadWeave.core.api.interpreter.AutoInterpreter;

import java.lang.reflect.Method;

public final class SimulationInstance<I, O> {
    private final Method simulation;
    private final Method extractor;
    private final Method interpreter;

    private final Class<I> inputType;

    public SimulationInstance(Method simulation, Method extractor, Method interpreter, Class<I> inputType) {
        this.simulation = simulation;
        this.extractor = extractor;
        this.interpreter = interpreter;
        this.inputType = inputType;
    }

    @SuppressWarnings("unchecked")
    public O execute(Object owner, Object input) {
        try {
            return (O) simulation.invoke(owner, input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public I extract(Object owner) {
        if (extractor == null) {
            return AutoExtractor.extract(owner, inputType);
        }

        try {
            return (I) extractor.invoke(owner);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void apply(Object owner, Object delta) {
        if (interpreter == null) {
            AutoInterpreter.apply(owner, delta);
            return;
        }

        try {
            interpreter.invoke(owner, delta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Class<I> inputType() {
        return inputType;
    }
}