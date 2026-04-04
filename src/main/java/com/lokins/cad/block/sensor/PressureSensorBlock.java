package com.lokins.cad.block.sensor;

import com.lokins.cad.blockentity.sensor.PressureSensorBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Pressure Sensor Plate - Tier 1 passive detection.
 * Single block range. Detects entities standing on it by weight.
 * Immune to electronic jammers (physical mechanism).
 * Countered by: Lightweight Boots (physical countermeasure).
 */
public class PressureSensorBlock extends BaseSensorBlock {

    public PressureSensorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CADBlockEntityTypes.PRESSURE_SENSOR.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, st, be) -> { if (be instanceof PressureSensorBlockEntity sensor) sensor.tick(); };
    }
}
