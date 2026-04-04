package com.lokins.cad.block.sensor;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.sensor.MechanicalWatchtowerBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mechanical Watchtower - Tier 2 active detection.
 * Range: 32 blocks (requires line of sight). Gear-driven optical scanner. 32 SU.
 * Countered by: Optical Jammer.
 */
public class MechanicalWatchtowerBlock extends CADKineticBlock<MechanicalWatchtowerBlockEntity> {

    public MechanicalWatchtowerBlock(Properties properties) {
        super(properties, 8f);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<MechanicalWatchtowerBlockEntity> getBlockEntityClass() {
        return MechanicalWatchtowerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MechanicalWatchtowerBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.MECHANICAL_WATCHTOWER.get();
    }
}
