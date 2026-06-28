package net.awyvrix.threadWeave.core.api.annotation;

import net.awyvrix.threadWeave.core.api.TickMode;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstance;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstanceRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationTypeRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationTypeId;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnitType;

import java.lang.reflect.Method;

public final class SimulationScanner {
    private final SimulationInstanceRegistry registry;
    private final SimulationTypeRegistry typeRegistry;

    public SimulationScanner(SimulationInstanceRegistry registry, SimulationTypeRegistry typeRegistry) {
        this.registry = registry;
        this.typeRegistry = typeRegistry;
    }

    public void scan(Class<?> owner) {
        Method simulation = null;
        Method extractor = null;
        Method interpreter = null;
        SimulationTypeId typeId = null;
        SimulationUnitType type = null;

        for (Method method : owner.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SimulatedThread.class)) {
                simulation = method;
                SimulatedThread annotation = method.getAnnotation(SimulatedThread.class);

                TickMode mode = annotation.mode();
                int rate = annotation.tickRate();
                typeId = new SimulationTypeId(annotation.value());

                Class<?> dtoClass = method.getParameterTypes()[0];
                type = new SimulationUnitType<Object>((Class<Object>) dtoClass, mode, rate);
            }

            if (method.isAnnotationPresent(SimulatedExtractor.class)) {
                extractor = method;
            }

            if (method.isAnnotationPresent(SimulatedInterpreter.class)) {
                interpreter = method;
            }
        }
        if (simulation == null) return;
        SimulationInstance<?, ?> instance = SimulationInstanceFactory.create(simulation, extractor, interpreter, owner);
        typeRegistry.register(typeId , type);
        registry.register(owner, instance);
    }
}