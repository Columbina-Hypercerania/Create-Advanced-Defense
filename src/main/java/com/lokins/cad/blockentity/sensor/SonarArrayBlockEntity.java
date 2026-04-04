package com.lokins.cad.blockentity.sensor;

import com.lokins.cad.capability.CADEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

/**
 * Sonar Array BE - Tier 2 active detection.
 * 48 block range on land, 96 blocks underwater. 4 CU + 20 FE/t.
 */
public class SonarArrayBlockEntity extends BaseSensorBlockEntity {

    private final CADEnergyStorage energyStorage = new CADEnergyStorage(5000, 100, 20);

    public SonarArrayBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected int getRange() {
        // Double range if submerged in water
        if (level != null) {
            FluidState fluid = level.getFluidState(getBlockPos());
            if (fluid.is(Fluids.WATER) || fluid.is(Fluids.FLOWING_WATER)) {
                return 96;
            }
        }
        return 48;
    }

    @Override protected int getScanInterval() { return 30; }

    @Override
    protected boolean canDetect(LivingEntity entity) {
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
