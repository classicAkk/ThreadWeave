package net.awyvrix.threadWeave.core.api.unit;

import net.awyvrix.threadWeave.core.api.instance.SimulationInstance;
import net.awyvrix.threadWeave.core.api.instance.SimulationInstanceRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationTypeRegistry;
import net.awyvrix.threadWeave.core.api.registry.SimulationUnitRegistry;
import net.awyvrix.threadWeave.core.api.runtime.SimulationTypeId;

public final class UnitFactory {
    private final SimulationUnitRegistry unitRegistry;
    private final SimulationInstanceRegistry instanceRegistry;
    private final SimulationTypeRegistry typeRegistry;

    public UnitFactory(SimulationUnitRegistry unitRegistry, SimulationInstanceRegistry instanceRegistry, SimulationTypeRegistry typeRegistry) {
        this.unitRegistry = unitRegistry;
        this.instanceRegistry = instanceRegistry;
        this.typeRegistry = typeRegistry;
    }

    public SimulationUnit create(Object source) {
        SimulationInstance<?, ?> instance = instanceRegistry.get(source.getClass());

        if (instance == null) throw new IllegalStateException("No @SimulatedThread found for " + source.getClass().getName());
        Class<?> dtoClass = instance.inputType();
        SimulationTypeId typeId = resolveType(dtoClass);
        SimulationUnitId id = new SimulationUnitId(generateId(source));
        SimulationUnit unit = new BasicSimulationUnit(id, typeId);

        if (!unitRegistry.contains(id)) {
            unitRegistry.register(unit);
        }

        return unit;
    }

    private SimulationTypeId resolveType(Class<?> dtoClass) {
        for (SimulationTypeId id : typeRegistry.allIds()) {
            SimulationUnitType<?> type = typeRegistry.get(id);

            if (type.dtoClass().equals(dtoClass)) {
                return id;
            }
        }

        throw new IllegalStateException(
                "No SimulationType registered for DTO " + dtoClass.getName()
        );
    }

    private long generateId(Object source) {
        return System.identityHashCode(source);
    }
}