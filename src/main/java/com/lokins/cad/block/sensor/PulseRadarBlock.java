package com.lokins.cad.block.sensor;

import com.lokins.cad.blockentity.sensor.PulseRadarBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Pulse Radar - Tier 2 active detection.
 * Range: 64 blocks. Periodic electromagnetic scan. 4 CU + 20 FE/t.
 * Scan interval inversely proportional to FE supply.
 * Countered by: Electronic Jammer.
 */
public class PulseRadarBlock extends BaseSensorBlock {

    public PulseRadarBlock(Properties properties) {
        super(properties);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CADBlockEntityTypes.PULSE_RADAR.create(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, st, be) -> { if (be instanceof PulseRadarBlockEntity r) r.tick(); };
    }
}
