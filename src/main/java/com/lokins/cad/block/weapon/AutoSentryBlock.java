package com.lokins.cad.block.weapon;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.weapon.AutoSentryBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

/**
 * Auto Sentry - the most basic automated defense turret.
 * 32 SU, min 16 RPM, fires bullets at hostile entities in range.
 * Pure mechanical - no FE required.
 */
public class AutoSentryBlock extends CADKineticBlock<AutoSentryBlockEntity> {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final float REQUIRED_SPEED = 16f;

    public AutoSentryBlock(Properties properties) {
        super(properties, REQUIRED_SPEED);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(net.minecraft.world.level.LevelReader world,
                                    BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<AutoSentryBlockEntity> getBlockEntityClass() {
        return AutoSentryBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AutoSentryBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.AUTO_SENTRY.get();
    }
}
