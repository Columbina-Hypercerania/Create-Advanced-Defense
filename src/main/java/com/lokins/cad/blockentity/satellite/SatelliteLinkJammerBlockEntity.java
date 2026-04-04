package com.lokins.cad.blockentity.satellite;

import com.lokins.cad.capability.CADEnergyStorage;
import com.lokins.cad.network.compute.IComputeConsumer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Satellite Link Jammer BE - blocks satellite comms on tuned frequency.
 * 150 FE/t + 16 CU. Must know target frequency to jam.
 */
public class SatelliteLinkJammerBlockEntity extends SmartBlockEntity implements IComputeConsumer {

    private final CADEnergyStorage energyStorage = new CADEnergyStorage(50000, 500, 150);
    private int tunedFrequency = -1; // -1 = not tuned
    private boolean jamming = false;
    private int allocatedCU = 0;

    public SatelliteLinkJammerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        jamming = tunedFrequency >= 0 && energyStorage.consumeTick() && allocatedCU >= 16;
    }

    public void tuneToFrequency(int frequency) {
        this.tunedFrequency = frequency;
        notifyUpdate();
    }

    public boolean isJamming() { return jamming; }
    public int getTunedFrequency() { return tunedFrequency; }
    public CADEnergyStorage getEnergyStorage() { return energyStorage; }

    // IComputeConsumer
    @Override public BlockPos getNodePos() { return getBlockPos(); }
    @Override public boolean isConnectedToNetwork() { return true; }
    @Override public int getRequiredComputeUnits() { return 16; }
    @Override public int getAllocatedComputeUnits() { return allocatedCU; }
    @Override public void setAllocatedComputeUnits(int allocated) { this.allocatedCU = allocated; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Energy")) energyStorage.setEnergy(tag.getInt("Energy"));
        tunedFrequency = tag.getInt("TunedFrequency");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putInt("TunedFrequency", tunedFrequency);
    }
}
