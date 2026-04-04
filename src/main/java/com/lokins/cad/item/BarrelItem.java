package com.lokins.cad.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Cannon barrel component. Caliber determines damage/ammo type, material determines precision/durability.
 */
public class BarrelItem extends Item {

    public enum Caliber {
        SMALL("small", 1.0f),
        MEDIUM("medium", 1.5f),
        LARGE("large", 2.5f);

        public final String id;
        public final float damageMultiplier;

        Caliber(String id, float damageMultiplier) {
            this.id = id;
            this.damageMultiplier = damageMultiplier;
        }
    }

    public enum Material {
        CAST_IRON("cast_iron", 0.7f, 200),
        BRASS("brass", 1.0f, 400),
        STEEL("steel", 1.3f, 800);

        public final String id;
        public final float accuracyModifier;
        public final int maxDurability;

        Material(String id, float accuracyModifier, int maxDurability) {
            this.id = id;
            this.accuracyModifier = accuracyModifier;
            this.maxDurability = maxDurability;
        }
    }

    private final Caliber caliber;
    private final Material material;

    public BarrelItem(Properties properties, Caliber caliber, Material material) {
        super(properties.durability(material.maxDurability));
        this.caliber = caliber;
        this.material = material;
    }

    public Caliber getCaliber() { return caliber; }
    public Material getBarrelMaterial() { return material; }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.cad.caliber." + caliber.id));
        tooltip.add(Component.translatable("tooltip.cad.material." + material.id));
    }
}
