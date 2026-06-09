package net.awyvrix.threadWeave.core.api.annotation;

import net.awyvrix.threadWeave.core.api.instance.SimulationInstance;
import net.awyvrix.threadWeave.core.api.validator.SimulationValidator;

import java.lang.reflect.Method;

public final class SimulationInstanceFactory {
    public static SimulationInstance<?, ?> create(Method method, Class<?> owner) {
        SimulationValidator.validate(method);

        return new SimulationInstance(method, method.getParameterTypes()[0]);
    }
}