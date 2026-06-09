package net.awyvrix.threadWeave.core.api.annotation;

import net.awyvrix.threadWeave.core.api.TickMode;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface SimulatedThread {
    String value();
    TickMode mode() default TickMode.WORLD_SYNCED;
    int tickRate() default 20;
}