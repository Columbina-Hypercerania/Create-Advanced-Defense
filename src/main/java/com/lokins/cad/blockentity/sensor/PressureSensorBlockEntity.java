package com.lokins.cad.blockentity.sensor;

import com.lokins.cad.compat.CuriosCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Pressure Sensor BE - detects entities on top of it (single block range).
 * Distinguishes by weight class. Immune to jammers.
 * Countered by Lightweight Boots (Curios trinket).
 */
public class PressureSensorBlockEntity extends BaseSensorBlockEntity {

    public PressureSensorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override protected int getRange() { return 1; }
    @Override protected int getScanInterval() { return 5; }

    @Override
    protected boolean canDetect(LivingEntity entity) {
        BlockPos above = getBlockPos().above();
        if (!entity.isAlive() || !entity.blockPosition().equals(above)) return false;
        // Lightweight boots counter: entity wearing them is undetectable
        try {
            if (CuriosCompat.isWearingLightweightBoots(entity)) return false;
        } catch (NoClassDefFoundError ignored) {
            // Curios not installed, skip check
        }
        return true;
    }
}
