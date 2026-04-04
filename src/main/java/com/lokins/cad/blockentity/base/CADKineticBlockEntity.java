package com.lokins.cad.blockentity.base;

import com.lokins.cad.capability.CADEnergyStorage;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Base BlockEntity for all CAD kinetic devices.
 * Provides SU consumption checking and optional FE energy management.
 */
public abstract class CADKineticBlockEntity extends KineticBlockEntity {

    @Nullable
    protected CADEnergyStorage energyStorage;
    private boolean active;

    public CADKineticBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * Called by subclasses to initialize FE storage.
     * Pass null if the device doesn't use FE (pure mechanical).
     */
    protected void initEnergyStorage(int capacity, int maxTransfer, int consumptionPerTick) {
        this.energyStorage = new CADEnergyStorage(capacity, maxTransfer, consumptionPerTick);
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        boolean hasSU = hasEnoughSU();
        boolean hasFE = energyStorage == null || energyStorage.consumeTick();
        boolean wasActive = active;
        active = hasSU && hasFE;

        if (active) {
            onActiveTick();
        } else if (wasActive) {
            onDeactivate();
        }
    }

    /**
     * Check if the kinetic network provides enough speed for this device.
     */
    protected boolean hasEnoughSU() {
        return Math.abs(getSpeed()) >= getRequiredSpeed();
    }

    /**
     * Minimum RPM required for this device to function.
     */
    protected abstract float getRequiredSpeed();

    /**
     * Called every tick when the device has enough SU and FE.
     */
    protected abstract void onActiveTick();

    /**
     * Called once when the device transitions from active to inactive.
     */
    protected void onDeactivate() {
    }

    public boolean isActive() {
        return active;
    }

    @Nullable
    public CADEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if (energyStorage != null && compound.contains("Energy")) {
            energyStorage.setEnergy(compound.getInt("Energy"));
        }
        active = compound.getBoolean("Active");
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        if (energyStorage != null) {
            compound.putInt("Energy", energyStorage.getEnergyStored());
        }
        compound.putBoolean("Active", active);
    }
}
