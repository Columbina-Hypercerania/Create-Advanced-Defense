package com.lokins.cad.blockentity.jammer;

import com.lokins.cad.block.jammer.JammerBlock;
import com.lokins.cad.blockentity.sensor.BaseSensorBlockEntity;
import com.lokins.cad.blockentity.sensor.InfraredSensorBlockEntity;
import com.lokins.cad.blockentity.sensor.VibrationMonitorBlockEntity;
import com.lokins.cad.capability.CADEnergyStorage;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Jammer BE - disrupts sensors within range based on jammer type.
 * Consumes FE while active.
 */
public class JammerBlockEntity extends SmartBlockEntity {

    private CADEnergyStorage energyStorage;
    private boolean jamming = false;

    public JammerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        // Lazy init energy based on block type
        if (energyStorage == null && getBlockState().getBlock() instanceof JammerBlock jammer) {
            energyStorage = new CADEnergyStorage(10000, 200, jammer.getJammerType().fePerTick);
        }
        if (energyStorage == null) return;

        boolean wasJamming = jamming;
        jamming = energyStorage.consumeTick();

        if (jamming && level.getGameTime() % 20 == 0) {
            applyJamming();
        }
    }

    private void applyJamming() {
        if (!(getBlockState().getBlock() instanceof JammerBlock jammer)) return;
        JammerBlock.JammerType type = jammer.getJammerType();
        int range = type.range;

        // Scan for sensors in range and check if they're the right type to jam
        BlockPos.betweenClosedStream(
                getBlockPos().offset(-range, -range, -range),
                getBlockPos().offset(range, range, range)
        ).forEach(pos -> {
            if (pos.distSqr(getBlockPos()) > (long) range * range) return;
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof BaseSensorBlockEntity sensor && shouldJam(type, sensor)) {
                // TODO: apply jamming debuff to the sensor
                // For now, sensors detect this through the jamming system
            }
        });
    }

    private boolean shouldJam(JammerBlock.JammerType type, BaseSensorBlockEntity sensor) {
        return switch (type) {
            case ACOUSTIC -> sensor instanceof VibrationMonitorBlockEntity;
            // TODO: sonar
            case OPTICAL -> sensor instanceof InfraredSensorBlockEntity;
            // TODO: mechanical watchtower
            case ELECTRONIC -> false; // TODO: pulse radar, phased array
        };
    }

    public boolean isJamming() { return jamming; }
    public CADEnergyStorage getEnergyStorage() { return energyStorage; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        jamming = tag.getBoolean("Jamming");
        if (energyStorage != null && tag.contains("Energy")) {
            energyStorage.setEnergy(tag.getInt("Energy"));
        }
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean("Jamming", jamming);
        if (energyStorage != null) tag.putInt("Energy", energyStorage.getEnergyStored());
    }
}
