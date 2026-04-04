package com.lokins.cad.blockentity.satellite;

import com.lokins.cad.capability.CADEnergyStorage;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Satellite Launch Pad BE - manages satellite construction and launch.
 * Tracks launched satellites and their orbital status.
 */
public class SatelliteLaunchPadBlockEntity extends SmartBlockEntity {

    private final CADEnergyStorage energyStorage = new CADEnergyStorage(500000, 5000, 0);
    private final List<SatelliteData> launchedSatellites = new ArrayList<>();

    public SatelliteLaunchPadBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    /**
     * Launch a satellite. Requires massive FE burst.
     */
    public boolean launchSatellite(SatelliteData.SatelliteType type, int frequency, boolean hasEncryption) {
        int launchCost = 100000; // 100K FE to launch
        if (!energyStorage.consumeBurst(launchCost)) return false;

        SatelliteData sat = new SatelliteData(
                UUID.randomUUID(), type, frequency, hasEncryption,
                true, System.currentTimeMillis()
        );
        launchedSatellites.add(sat);
        notifyUpdate();
        return true;
    }

    public List<SatelliteData> getLaunchedSatellites() { return launchedSatellites; }
    public CADEnergyStorage getEnergyStorage() { return energyStorage; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Energy")) energyStorage.setEnergy(tag.getInt("Energy"));
        launchedSatellites.clear();
        if (tag.contains("Satellites")) {
            CompoundTag sats = tag.getCompound("Satellites");
            int count = sats.getInt("Count");
            for (int i = 0; i < count; i++) {
                CompoundTag satTag = sats.getCompound("Sat" + i);
                launchedSatellites.add(SatelliteData.fromTag(satTag));
            }
        }
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        CompoundTag sats = new CompoundTag();
        sats.putInt("Count", launchedSatellites.size());
        for (int i = 0; i < launchedSatellites.size(); i++) {
            sats.put("Sat" + i, launchedSatellites.get(i).toTag());
        }
        tag.put("Satellites", sats);
    }
}
