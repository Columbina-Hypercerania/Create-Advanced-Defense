package com.lokins.cad.block.network;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.network.CommandCenterBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Command Center - the brain of the CAD network.
 * Multi-block structure (3x3x2) in the future, single block placeholder for now.
 * 64 SU + 30 FE/t. Provides situation display, target assignment, alert management.
 */
public class CommandCenterBlock extends CADKineticBlock<CommandCenterBlockEntity> {

    public CommandCenterBlock(Properties properties) {
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
    public Class<CommandCenterBlockEntity> getBlockEntityClass() {
        return CommandCenterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CommandCenterBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.COMMAND_CENTER.get();
    }
}
