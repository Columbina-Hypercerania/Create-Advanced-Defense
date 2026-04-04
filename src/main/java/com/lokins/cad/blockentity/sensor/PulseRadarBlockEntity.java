package com.lokins.cad.blockentity.sensor;

import com.lokins.cad.capability.CADEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Pulse Radar BE - Tier 2 active detection.
 * 64 block range, 4 CU, 20 FE/t. Scan interval scales with power supply.
 */
public class PulseRadarBlockEntity extends BaseSensorBlockEntity {

    private final CADEnergyStorage energyStorage = new CADEnergyStorage(5000, 100, 20);

    public PulseRadarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override protected int getRange() { return 64; }

    @Override protected int getScanInterval() {
        // More FE stored = faster scan (min 10 ticks, max 60 ticks)
        float fill = (float) energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored();
        return (int) (60 - fill * 50);
    }

    @Override
    protected boolean canDetect(LivingEntity entity) {
        // Radar detects all living entities, even invisible ones
        return entity.isAlive() && energyStorage.consumeTick();
    }

    @Override public int getRequiredComputeUnits() { return 4; }

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
