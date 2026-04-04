package com.lokins.cad.block.network;

import com.lokins.cad.blockentity.network.SignalAdapterBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Signal Adapter - bridges redstone signals into the CAD network.
 * Detects adjacent redstone signal changes and converts them to CAD events.
 */
public class SignalAdapterBlock extends Block implements IBE<SignalAdapterBlockEntity> {

    public SignalAdapterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            withBlockEntityDo(level, pos, be -> {
                int signal = level.getBestNeighborSignal(pos);
                be.onRedstoneSignalChanged(signal);
            });
        }
    }

    @Override
    public Class<SignalAdapterBlockEntity> getBlockEntityClass() {
        return SignalAdapterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SignalAdapterBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.SIGNAL_ADAPTER.get();
    }
}
