package com.lokins.cad.block.compute;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.compute.MechanicalComputeUnitBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mechanical Compute Unit - entry-level compute provider.
 * 4 CU, 32 SU, 0 FE. Gear-driven.
 */
public class MechanicalComputeUnitBlock extends CADKineticBlock<MechanicalComputeUnitBlockEntity> {

    public MechanicalComputeUnitBlock(Properties properties) {
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
    public Class<MechanicalComputeUnitBlockEntity> getBlockEntityClass() {
        return MechanicalComputeUnitBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MechanicalComputeUnitBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.MECHANICAL_COMPUTE_UNIT.get();
    }
}
