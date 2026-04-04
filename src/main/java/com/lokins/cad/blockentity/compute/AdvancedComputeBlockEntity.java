package com.lokins.cad.blockentity.compute;

import com.lokins.cad.block.compute.AdvancedComputeBlock;
import com.lokins.cad.capability.CADEnergyStorage;
import com.lokins.cad.network.compute.IComputeProvider;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Advanced Compute BE - FE-powered compute provider.
 * Tier determined by block.
 */
public class AdvancedComputeBlockEntity extends SmartBlockEntity implements IComputeProvider {

    private CADEnergyStorage energyStorage;
    private boolean active = false;
    private int maxCU = 16;

    public AdvancedComputeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        // Lazy init from block tier
        if (energyStorage == null && getBlockState().getBlock() instanceof AdvancedComputeBlock block) {
            AdvancedComputeBlock.Tier tier = block.getTier();
            maxCU = tier.computeUnits;
            energyStorage = new CADEnergyStorage(tier.fePerTick * 100, tier.fePerTick * 2, tier.fePerTick);
        }
        if (energyStorage == null) return;

        active = energyStorage.consumeTick();
    }

    public CADEnergyStorage getEnergyStorage() { return energyStorage; }

    // IComputeProvider
    @Override public BlockPos getNodePos() { return getBlockPos(); }
    @Override public boolean isConnectedToNetwork() { return true; }
    @Override public int getMaxComputeUnits() { return maxCU; }
    @Override public int getAvailableComputeUnits() { return active ? maxCU : 0; }
    @Override public boolean isProviderActive() { return active; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (energyStorage != null && tag.contains("Energy")) energyStorage.setEnergy(tag.getInt("Energy"));
        active = tag.getBoolean("Active");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        if (energyStorage != null) tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putBoolean("Active", active);
    }
}
