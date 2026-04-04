package com.lokins.cad.block.sensor;

import com.lokins.cad.blockentity.sensor.VibrationMonitorBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Vibration Monitor - Tier 1 passive detection.
 * Range: 12 blocks. Detects moving entities. Can penetrate walls.
 * Countered by: Acoustic Jammer.
 */
public class VibrationMonitorBlock extends BaseSensorBlock {

    public VibrationMonitorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CADBlockEntityTypes.VIBRATION_MONITOR.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, st, be) -> { if (be instanceof VibrationMonitorBlockEntity sensor) sensor.tick(); };
    }
}
