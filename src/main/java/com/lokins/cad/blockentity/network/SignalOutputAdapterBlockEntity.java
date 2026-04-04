package com.lokins.cad.blockentity.network;

import com.lokins.cad.block.network.SignalOutputAdapterBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Signal Output Adapter BE - outputs redstone based on CAD network events.
 * Configurable event-to-signal mapping (entity count, alert level, ammo status, etc.)
 */
public class SignalOutputAdapterBlockEntity extends SmartBlockEntity {

    private int outputPower = 0;

    public SignalOutputAdapterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    /**
     * Called by the CAD network when a monitored event updates.
     */
    public void setOutputPower(int power) {
        int clamped = Math.max(0, Math.min(15, power));
        if (this.outputPower != clamped) {
            this.outputPower = clamped;
            if (level != null && !level.isClientSide()) {
                BlockState state = getBlockState();
                level.setBlockAndUpdate(getBlockPos(),
                        state.setValue(SignalOutputAdapterBlock.POWER, clamped));
                level.updateNeighborsAt(getBlockPos(), state.getBlock());
            }
            notifyUpdate();
        }
    }

    public int getOutputPower() { return outputPower; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        outputPower = tag.getInt("OutputPower");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("OutputPower", outputPower);
    }
}
