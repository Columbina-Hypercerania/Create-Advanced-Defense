package com.lokins.cad.compat;

import com.lokins.cad.item.LightweightBootsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

/**
 * Curios API integration for CAD equipment.
 * Lightweight Boots use the "feet" curios slot.
 */
public class CuriosCompat {

    /**
     * Check if a living entity is wearing lightweight boots via Curios.
     */
    public static boolean isWearingLightweightBoots(LivingEntity entity) {
        Optional<SlotResult> result = CuriosApi.getCuriosInventory(entity)
                .flatMap(inv -> inv.findFirstCurio(stack -> stack.getItem() instanceof LightweightBootsItem));
        return result.isPresent();
    }
}
