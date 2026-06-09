package net.awyvrix.threadWeave.core.api.unit;

import net.awyvrix.threadWeave.core.api.TickMode;

public record SimulationUnitType<T>(Class<T> dtoClass, TickMode tickMode, int isolatedRate) {}