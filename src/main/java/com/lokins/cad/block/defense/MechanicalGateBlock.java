package com.lokins.cad.block.defense;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.defense.MechanicalGateBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mechanical Gate - fast rising/lowering gate.
 * 32-128 SU depending on speed. Crushing damage on close.
 */
public class MechanicalGateBlock extends CADKineticBlock<MechanicalGateBlockEntity> {

    public MechanicalGateBlock(Properties properties) {
        super(properties, 8f);
    }

    @Override public Direction.Axis getRotationAxis(BlockState state) { return Direction.Axis.Y; }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override public Class<MechanicalGateBlockEntity> getBlockEntityClass() { return MechanicalGateBlockEntity.class; }
    @Override public BlockEntityType<? extends MechanicalGateBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.MECHANICAL_GATE.get(); }
}
