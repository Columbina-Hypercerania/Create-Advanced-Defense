package com.lokins.cad.block.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Data Cable (block type) - connects CAD devices into a network.
 * Auto-connects to adjacent data cables and CAD devices.
 */
public class DataCableBlock extends Block {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public DataCableBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(NORTH, false).setValue(SOUTH, false)
                .setValue(EAST, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, getConnectionState(state, level, pos));
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, getConnectionState(state, level, pos));
        }
    }

    private BlockState getConnectionState(BlockState state, Level level, BlockPos pos) {
        return state
                .setValue(NORTH, canConnect(level, pos, Direction.NORTH))
                .setValue(SOUTH, canConnect(level, pos, Direction.SOUTH))
                .setValue(EAST, canConnect(level, pos, Direction.EAST))
                .setValue(WEST, canConnect(level, pos, Direction.WEST))
                .setValue(UP, canConnect(level, pos, Direction.UP))
                .setValue(DOWN, canConnect(level, pos, Direction.DOWN));
    }

    private boolean canConnect(BlockGetter level, BlockPos pos, Direction dir) {
        BlockState neighbor = level.getBlockState(pos.relative(dir));
        return neighbor.getBlock() instanceof DataCableBlock
                || neighbor.getBlock() instanceof DataRouterBlock;
        // TODO: also connect to sensors, weapons, command center, etc.
    }
}
