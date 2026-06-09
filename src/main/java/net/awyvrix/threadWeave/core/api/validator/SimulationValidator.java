package net.awyvrix.threadWeave.core.api.validator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

public class SimulationValidator {
    private static final Set<Class<?>> FORBIDDEN_TYPES = Set.of(
            Level.class,
            LevelAccessor.class,
            LevelReader.class,

            Entity.class,
            BlockEntity.class,

            MinecraftServer.class
    );

    public static void validate(Method method) {
        validateMethodSignature(method);
        validateParameters(method);
        validateReturnType(method);
    }

    private static void validateMethodSignature(Method method) {
        if (method.getParameterCount() != 1) throw new SimulationValidationException("Simulation method must have exactly 1 parameter");
        if (method.getReturnType() == void.class) throw new SimulationValidationException("Simulation method cannot return void");
    }

    private static void validateParameters(Method method) {
        for (Parameter parameter : method.getParameters()) {
            validateType(parameter.getType(), "parameter");
        }
    }

    private static void validateReturnType(Method method) {
        validateType(method.getReturnType(), "return type");
    }

    private static void validateType(Class<?> type, String context) {
        if (isForbidden(type)) throw new SimulationValidationException("Forbidden Minecraft type in " + context + ": " + type.getName());
        if (isJDKType(type)) return;
        if (isMinecraftAllowedValue(type)) return;

        for (Field field : type.getDeclaredFields()) {
            validateField(field);
        }
    }

    private static void validateField(Field field) {
        Class<?> type = field.getType();

        if (isForbidden(type)) throw new SimulationValidationException("Forbidden field type: " + type.getName() + " in " + field.getDeclaringClass().getSimpleName());
        if (isJDKType(type)) return;
        if (isMinecraftAllowedValue(type)) return;
        validateType(type, "nested DTO field");
    }

    private static boolean isJDKType(Class<?> type) {
        return type.isPrimitive()
                || type == String.class
                || Number.class.isAssignableFrom(type)
                || type.isEnum()
                || type.getName().startsWith("java.");
    }

    private static boolean isMinecraftAllowedValue(Class<?> type) {
        return type == BlockPos.class
                || type == Direction.class
                || type == Vec3.class
                || type == ItemStack.class
                || type == FluidStack.class
                || type == ResourceLocation.class;
    }

    private static boolean isForbidden(Class<?> type) {
        for (Class<?> forbidden : FORBIDDEN_TYPES) {
            if (forbidden.isAssignableFrom(type)) {
                return true;
            }
        }

        return false;
    }
}
