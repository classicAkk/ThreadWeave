package net.awyvrix.threadWeave.content.test.general.custom;

import net.awyvrix.threadWeave.core.api.runtime.SimulationDelta;

public record ReactorData(int heat, int fuel) implements SimulationDelta {}