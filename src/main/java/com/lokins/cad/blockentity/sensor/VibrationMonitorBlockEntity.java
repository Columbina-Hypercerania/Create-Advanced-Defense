package com.lokins.cad.blockentity.sensor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Vibration Monitor BE - detects moving entities within 12 blocks.
 * Can detect through walls (no line-of-sight check).
 */
public class VibrationMonitorBlockEntity extends BaseSensorBlockEntity {

    public VibrationMonitorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override protected int getRange() { return 12; }
    @Override protected int getScanInterval() { return 15; }

    @Override
    protected boolean canDetect(LivingEntity entity) {
        // Detects entities that are moving (speed > threshold)
        return entity.isAlive() && entity.getDeltaMovement().lengthSqr() > 0.001;
    }
}
