package com.lokins.cad.item;

import net.minecraft.world.item.Item;

/**
 * Artillery shell for fire cannons.
 * Combined with a charge pack to form a complete round.
 */
public class ShellItem extends Item {

    public enum ShellType {
        SOLID(6.0f, 0.0f, 0.0f, false),
        HIGH_EXPLOSIVE(4.0f, 3.0f, 0.0f, false),
        INCENDIARY(3.0f, 2.0f, 0.0f, true),
        ARMOR_PIERCING(5.0f, 0.0f, 0.5f, false),
        BUCKSHOT(2.0f, 0.0f, 0.0f, false); // damage per pellet, spread handled by cannon

        public final float directDamage;
        public final float explosionRadius;
        public final float armorPenetration;
        public final boolean setsFire;

        ShellType(float directDamage, float explosionRadius, float armorPenetration, boolean setsFire) {
            this.directDamage = directDamage;
            this.explosionRadius = explosionRadius;
            this.armorPenetration = armorPenetration;
            this.setsFire = setsFire;
        }
    }

    private final ShellType shellType;

    public ShellItem(Properties properties, ShellType shellType) {
        super(properties);
        this.shellType = shellType;
    }

    public ShellType getShellType() {
        return shellType;
    }
}
