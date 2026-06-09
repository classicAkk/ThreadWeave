package net.awyvrix.threadWeave.core.api.extractor;

import net.awyvrix.threadWeave.core.api.annotation.Simulations;
import net.awyvrix.threadWeave.core.api.unit.SimulationUnit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public final class AutoExtractor {

    @SuppressWarnings("unchecked")
    public static <T> T extract(Object source, Class<T> dtoClass) {
        try {
            Field[] dtoFields = dtoClass.getDeclaredFields();
            Object[] values = new Object[dtoFields.length];

            for (int i = 0; i < dtoFields.length; i++) {
                Field f = dtoFields[i];
                f.setAccessible(true);
                Field sourceField = source.getClass().getDeclaredField(f.getName());
                sourceField.setAccessible(true);
                values[i] = sourceField.get(source);
            }
            Constructor<?> ctor = dtoClass.getDeclaredConstructors()[0];
            ctor.setAccessible(true);
            //Simulations.submit((SimulationUnit) source, dtoClass);

            return (T) ctor.newInstance(values);

        } catch (Exception e) {
            throw new RuntimeException("AutoExtractor failed", e);
        }
    }
}