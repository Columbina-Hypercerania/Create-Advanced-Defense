package com.lokins.cad.blockentity.power;

import com.lokins.cad.capability.CADEnergyStorage;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * Dynamo BE - converts SU to FE at 1 SU = 0.25 FE/t.
 * Outputs FE to adjacent blocks.
 */
public class DynamoBlockEntity extends KineticBlockEntity {

    public static final float SU_TO_FE_RATIO = 0.25f;
    private final CADEnergyStorage energyStorage = new CADEnergyStorage(10000, 1000, 0);

    public DynamoBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        // Generate FE based on SU consumed by the network through this block
        float su = Math.abs(getSpeed()) * 4; // approximate SU from speed
        int feGenerated = (int) (su * SU_TO_FE_RATIO);
        if (feGenerated > 0) {
            energyStorage.receiveEnergy(feGenerated, false);
        }

        // Push FE to adjacent blocks
        distributeEnergy();
    }

    private void distributeEnergy() {
        if (level == null) return;
        int stored = energyStorage.getEnergyStored();
        if (stored <= 0) return;

        for (Direction dir : Direction.values()) {
            if (stored <= 0) break;
            BlockPos adjacent = getBlockPos().relative(dir);
            IEnergyStorage target = level.getCapability(Capabilities.EnergyStorage.BLOCK, adjacent, dir.getOpposite());
            if (target != null && target.canReceive()) {
                int pushed = target.receiveEnergy(Math.min(stored, 500), false);
                if (pushed > 0) {
                    energyStorage.extractEnergy(pushed, false);
                    stored -= pushed;
                }
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
