package com.lokins.cad.blockentity.network;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Signal Adapter BE - converts redstone signals to CAD network events.
 * Tracks signal state for edge detection (rising/falling/threshold/pulse).
 */
public class SignalAdapterBlockEntity extends SmartBlockEntity {

    private int lastSignal = 0;
    private int currentSignal = 0;

    public SignalAdapterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    public void onRedstoneSignalChanged(int newSignal) {
        lastSignal = currentSignal;
        currentSignal = newSignal;

        if (lastSignal == 0 && currentSignal > 0) {
            onRisingEdge(currentSignal);
        } else if (lastSignal > 0 && currentSignal == 0) {
            onFallingEdge();
        }

        notifyUpdate();
    }

    private void onRisingEdge(int strength) {
        // TODO: trigger configured CAD network actions (raise alert, activate weapons, etc.)
    }

    private void onFallingEdge() {
        // TODO: trigger configured CAD network actions (lower alert, cease fire, etc.)
    }

    public int getCurrentSignal() { return currentSignal; }
    public int getLastSignal() { return lastSignal; }
    public boolean isRisingEdge() { return lastSignal == 0 && currentSignal > 0; }
    public boolean isFallingEdge() { return lastSignal > 0 && currentSignal == 0; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        currentSignal = tag.getInt("Signal");
        lastSignal = tag.getInt("LastSignal");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Signal", currentSignal);
        tag.putInt("LastSignal", lastSignal);
    }
}
