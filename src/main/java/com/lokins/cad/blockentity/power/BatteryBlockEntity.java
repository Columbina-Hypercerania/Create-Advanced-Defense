package com.lokins.cad.blockentity.power;

import com.lokins.cad.block.power.BatteryBlock;
import com.lokins.cad.capability.CADEnergyStorage;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Battery BE - stores FE. Tier determined by the block.
 */
public class BatteryBlockEntity extends SmartBlockEntity {

    private CADEnergyStorage energyStorage;

    public BatteryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        // Default to small, will be overridden when block is known
        initStorage(BatteryBlock.Tier.SMALL);
    }

    private void initStorage(BatteryBlock.Tier tier) {
        int currentEnergy = energyStorage != null ? energyStorage.getEnergyStored() : 0;
        energyStorage = new CADEnergyStorage(tier.capacity, tier.maxTransfer, 0);
        energyStorage.setEnergy(Math.min(currentEnergy, tier.capacity));
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        // Ensure correct tier
        if (getBlockState().getBlock() instanceof BatteryBlock battery) {
            if (energyStorage.getMaxEnergyStored() != battery.getTier().capacity) {
                initStorage(battery.getTier());
            }
        }
    }

    public CADEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Energy")) energyStorage.setEnergy(tag.getInt("Energy"));
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Energy", energyStorage.getEnergyStored());
    }
}
