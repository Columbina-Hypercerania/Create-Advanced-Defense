package com.lokins.cad.blockentity.sensor;

import com.lokins.cad.capability.CADEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Phased Array Radar BE - Tier 3 continuous tracking.
 * 128 block range, real-time position + velocity tracking. 16 CU + 80 FE/t.
 */
public class PhasedArrayRadarBlockEntity extends BaseSensorBlockEntity {

    private final CADEnergyStorage energyStorage = new CADEnergyStorage(20000, 200, 80);

    public PhasedArrayRadarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override protected int getRange() { return 128; }
    @Override protected int getScanInterval() { return 5; } // near real-time

    @Override
    protected boolean canDetect(LivingEntity entity) {
        return entity.isAlive() && energyStorage.consumeTick();
    }

    @Override public int getRequiredComputeUnits() { return 16; }

    public CADEnergyStorage getEnergyStorage() { return energyStorage; }

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
