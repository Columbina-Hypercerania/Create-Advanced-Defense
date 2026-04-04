package com.lokins.cad.block.sensor;

import com.lokins.cad.blockentity.sensor.PhasedArrayRadarBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Phased Array Radar - Tier 3 advanced detection.
 * Range: 128 blocks. Continuous tracking with velocity vectors. 16 CU + 80 FE/t.
 * Countered by: Electronic Jammer.
 */
public class PhasedArrayRadarBlock extends BaseSensorBlock {

    public PhasedArrayRadarBlock(Properties properties) {
        super(properties);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CADBlockEntityTypes.PHASED_ARRAY_RADAR.create(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, st, be) -> { if (be instanceof PhasedArrayRadarBlockEntity r) r.tick(); };
    }
}
