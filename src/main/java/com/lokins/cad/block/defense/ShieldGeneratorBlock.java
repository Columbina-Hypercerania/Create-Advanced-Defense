package com.lokins.cad.block.defense;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.defense.ShieldGeneratorBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Shield Generator - produces a force field blocking projectiles.
 * 256 SU + 100 FE/t idle / up to 500 FE/t under fire.
 */
public class ShieldGeneratorBlock extends CADKineticBlock<ShieldGeneratorBlockEntity> {

    public ShieldGeneratorBlock(Properties properties) {
        super(properties, 16f);
    }

    @Override public Direction.Axis getRotationAxis(BlockState state) { return Direction.Axis.Y; }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override public Class<ShieldGeneratorBlockEntity> getBlockEntityClass() { return ShieldGeneratorBlockEntity.class; }
    @Override public BlockEntityType<? extends ShieldGeneratorBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.SHIELD_GENERATOR.get(); }
}
