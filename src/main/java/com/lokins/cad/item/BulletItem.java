package com.lokins.cad.item;

import net.minecraft.world.item.Item;

/**
 * Base class for all bullet/projectile ammo items.
 */
public class BulletItem extends Item {

    private final float damageModifier;
    private final float armorPenetration;

    public BulletItem(Properties properties, float damageModifier, float armorPenetration) {
        super(properties);
        this.damageModifier = damageModifier;
        this.armorPenetration = armorPenetration;
    }

    /** Damage multiplier applied to the base weapon damage */
    public float getDamageModifier() {
        return damageModifier;
    }

    /** Fraction of target armor to ignore (0.0 = none, 1.0 = all) */
    public float getArmorPenetration() {
        return armorPenetration;
    }
}
