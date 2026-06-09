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
        for (Method method : owner.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(SimulatedThread.class)) continue;
            SimulatedThread annotation = method.getAnnotation(SimulatedThread.class);
            TickMode mode = annotation.mode();
            int rate = annotation.tickRate();
            SimulationTypeId typeId  = new SimulationTypeId(annotation.value());

            SimulationInstance<?, ?> instance = SimulationInstanceFactory.create(method, owner);
            Class<?> dtoClass = method.getParameterTypes()[0];
            SimulationUnitType type = new SimulationUnitType<Object>((Class<Object>) dtoClass, mode, rate);

            typeRegistry.register(typeId , type);
            registry.register(owner, instance);
        }
    }
}