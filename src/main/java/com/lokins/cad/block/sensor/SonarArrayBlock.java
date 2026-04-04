package com.lokins.cad.block.sensor;

import com.lokins.cad.blockentity.sensor.SonarArrayBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Sonar Array - Tier 2 active detection.
 * Range: 48 blocks (96 underwater). 4 CU + 20 FE/t.
 * Countered by: Acoustic Jammer.
 */
public class SonarArrayBlock extends BaseSensorBlock {

    public SonarArrayBlock(Properties properties) {
        super(properties);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CADBlockEntityTypes.SONAR_ARRAY.create(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return (lvl, pos, st, be) -> { if (be instanceof SonarArrayBlockEntity s) s.tick(); };
    }
}
