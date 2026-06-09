package net.awyvrix.threadWeave.content.general.custom;

import net.awyvrix.threadWeave.core.api.runtime.SimulationDelta;

public record ReactorData(int heat, int fuel) implements SimulationDelta {}