package net.awyvrix.threadWeave.core.api.annotation;

import net.awyvrix.threadWeave.core.api.instance.SimulationInstance;
import net.awyvrix.threadWeave.core.api.validator.SimulationValidator;

import java.lang.reflect.Method;

public final class SimulationInstanceFactory {
    public static SimulationInstance<?, ?> create(Method simulation, Method extractor, Method interpreter, Class<?> owner) {
        SimulationValidator.validate(simulation);

        return new SimulationInstance(simulation, extractor, interpreter, simulation.getParameterTypes()[0]);
    }
}