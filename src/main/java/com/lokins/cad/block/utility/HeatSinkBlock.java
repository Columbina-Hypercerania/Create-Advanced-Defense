package com.lokins.cad.block.utility;

import net.minecraft.world.level.block.Block;

/**
 * Heat Sink - passive cooling block.
 * Place adjacent to heat-generating devices (compute units, laser weapons, etc.)
 * to provide passive cooling. No power required.
 */
public class HeatSinkBlock extends Block {

    private final int coolingCapacity;

    public HeatSinkBlock(Properties properties, int coolingCapacity) {
        super(properties);
        this.coolingCapacity = coolingCapacity;
    }

    public int getCoolingCapacity() { return coolingCapacity; }
}
