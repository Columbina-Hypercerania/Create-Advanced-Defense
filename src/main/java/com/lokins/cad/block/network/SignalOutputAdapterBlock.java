package com.lokins.cad.block.network;

import com.lokins.cad.blockentity.network.SignalOutputAdapterBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Signal Output Adapter - converts CAD network events to redstone signals.
 * Outputs signal strength based on configured event mapping.
 */
public class SignalOutputAdapterBlock extends Block implements IBE<SignalOutputAdapterBlockEntity> {

    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public SignalOutputAdapterBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(POWER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
        return state.getValue(POWER);
    }

    @Override
    public Class<SignalOutputAdapterBlockEntity> getBlockEntityClass() {
        return SignalOutputAdapterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SignalOutputAdapterBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.SIGNAL_OUTPUT_ADAPTER.get();
    }
}
