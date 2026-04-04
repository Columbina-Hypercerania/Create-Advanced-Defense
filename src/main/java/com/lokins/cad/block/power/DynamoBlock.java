package com.lokins.cad.block.power;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.power.DynamoBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Dynamo / Power Generator - converts Create SU to FE.
 * 1 SU = 0.25 FE/t. Core of the electrical system.
 */
public class DynamoBlock extends CADKineticBlock<DynamoBlockEntity> {

    public DynamoBlock(Properties properties) {
        super(properties, 0f); // No minimum speed, generates proportionally
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
    public Class<DynamoBlockEntity> getBlockEntityClass() {
        return DynamoBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends DynamoBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.DYNAMO.get();
    }
}
