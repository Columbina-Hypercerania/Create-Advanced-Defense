package com.lokins.cad.block.utility;

import net.minecraft.world.level.block.Block;

/**
 * Water Cooling Pipe - active cooling using Create fluid pipes.
 * Must be connected to a coolant source. Provides higher cooling capacity than heat sinks.
 * Required for mid-to-high compute setups and high-energy weapons.
 */
public class WaterCoolingPipeBlock extends Block {

    private final int coolingCapacity;

    public WaterCoolingPipeBlock(Properties properties) {
        super(properties);
        this.coolingCapacity = 50; // much higher than heat sinks
    }

    public int getCoolingCapacity() { return coolingCapacity; }
}
