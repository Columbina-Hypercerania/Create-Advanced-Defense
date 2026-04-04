package com.lokins.cad.item;

import net.minecraft.world.item.Item;

/**
 * Lightweight Boots - Curios trinket that prevents triggering pressure sensor plates.
 * Crafted with feathers + leather + phantom membrane.
 */
public class LightweightBootsItem extends Item {

    public LightweightBootsItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    // TODO: Curios integration - register as feet slot trinket
    // TODO: Check in PressureSensorBlockEntity to skip detection
}
