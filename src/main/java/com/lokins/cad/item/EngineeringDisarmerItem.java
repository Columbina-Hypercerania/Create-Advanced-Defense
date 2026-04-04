package com.lokins.cad.item;

import net.minecraft.world.item.Item;

/**
 * Engineering Disarmer - hand-held tool to remotely disable pressure sensor plates.
 * 8 block range, sneak + right-click, 3 second operation time.
 */
public class EngineeringDisarmerItem extends Item {

    public static final int RANGE = 8;
    public static final int OPERATION_TICKS = 60; // 3 seconds

    public EngineeringDisarmerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    // TODO: Right-click action with progress to disable target pressure plate
}
