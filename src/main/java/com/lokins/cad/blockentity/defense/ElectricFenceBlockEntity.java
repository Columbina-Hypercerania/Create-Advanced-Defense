package com.lokins.cad.blockentity.defense;

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
 * Electric Fence BE - consumes 15 FE/t to stay powered.
 */
public class ElectricFenceBlockEntity extends SmartBlockEntity {

    private final CADEnergyStorage energyStorage = new CADEnergyStorage(1000, 50, 15);
    private boolean powered = false;

    public ElectricFenceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;
        powered = energyStorage.consumeTick();
    }

    public boolean isPowered() { return powered; }
    public CADEnergyStorage getEnergyStorage() { return energyStorage; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Energy")) energyStorage.setEnergy(tag.getInt("Energy"));
        powered = tag.getBoolean("Powered");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putBoolean("Powered", powered);
    }
}
