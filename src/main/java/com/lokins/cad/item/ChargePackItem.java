package com.lokins.cad.item;

import net.minecraft.world.item.Item;

/**
 * Propellant charge for fire cannons.
 * Combined with a shell to form a complete round.
 */
public class ChargePackItem extends Item {

    public enum Grade {
        LIGHT(1.0f, 1.0f, 1.0f),
        STANDARD(1.3f, 1.3f, 1.0f),
        HEAVY(1.6f, 1.6f, 1.5f); // Heavy increases barrel wear

        public final float rangeModifier;
        public final float damageModifier;
        public final float wearModifier;

        Grade(float range, float damage, float wear) {
            this.rangeModifier = range;
            this.damageModifier = damage;
            this.wearModifier = wear;
        }
    }

    private final Grade grade;

    public ChargePackItem(Properties properties, Grade grade) {
        super(properties);
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }
}
