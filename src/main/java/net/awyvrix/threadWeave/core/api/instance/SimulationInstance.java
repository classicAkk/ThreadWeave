package net.awyvrix.threadWeave.core.api.instance;

import java.lang.reflect.Method;

public final class SimulationInstance<I, O> {
    private final Method method;
    private final Class<I> inputType;

    public SimulationInstance(Method method, Class<I> inputType) {
        this.method = method;
        this.inputType = inputType;
    }

    @SuppressWarnings("unchecked")
    public O execute(Object owner, Object input) {
        try {
            return (O) method.invoke(owner, input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Class<I> inputType() {
        return inputType;
    }
}