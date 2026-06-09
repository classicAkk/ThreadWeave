package net.awyvrix.threadWeave.core.api.interpreter;

import java.lang.reflect.Field;

public final class AutoInterpreter {

    public static <T> void apply(Object target, T dto) {
        try {
            for (Field field : dto.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Field targetField = target.getClass().getDeclaredField(field.getName());
                targetField.setAccessible(true);
                targetField.set(target, field.get(dto));
            }

        } catch (Exception e) {
            throw new RuntimeException("AutoInterpreter failed", e);
        }
    }
}