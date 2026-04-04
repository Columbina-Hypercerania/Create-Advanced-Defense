package com.lokins.cad.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Counter-jamming module - installed on sensors to reduce jamming effectiveness.
 * Three types matching the three jammer domains.
 */
public class CounterModuleItem extends Item {

    public enum CounterType {
        ADAPTIVE_FILTER("adaptive_filter", 2, 10),
        MULTI_SPECTRUM("multi_spectrum", 2, 10),
        FREQUENCY_HOPPING("frequency_hopping", 4, 20);

        public final String id;
        public final int extraCU;
        public final int extraFEPerTick;

        CounterType(String id, int extraCU, int extraFE) {
            this.id = id;
            this.extraCU = extraCU;
            this.extraFEPerTick = extraFE;
        }
    }

    private final CounterType counterType;

    public CounterModuleItem(Properties properties, CounterType counterType) {
        super(properties.stacksTo(1));
        this.counterType = counterType;
    }

    public CounterType getCounterType() { return counterType; }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.cad.counter." + counterType.id));
        tooltip.add(Component.literal(String.format("+%d CU, +%d FE/t", counterType.extraCU, counterType.extraFEPerTick)));
    }
}
