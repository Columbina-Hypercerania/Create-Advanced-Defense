package com.lokins.cad.block.sensor;

import com.lokins.cad.blockentity.sensor.InfraredSensorBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Infrared Sensor - Tier 1 passive detection.
 * Range: 16 blocks. Detects living entities by heat signature.
 * Countered by: Optical Jammer.
 */
public class InfraredSensorBlock extends BaseSensorBlock {

    public InfraredSensorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CADBlockEntityTypes.INFRARED_SENSOR.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, st, be) -> { if (be instanceof InfraredSensorBlockEntity sensor) sensor.tick(); };
    }
}
