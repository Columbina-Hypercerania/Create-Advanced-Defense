package com.lokins.cad.blockentity.satellite;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

/**
 * Data record for a launched satellite.
 * Stores type, frequency, encryption status, and operational state.
 */
public record SatelliteData(
        UUID satelliteId,
        SatelliteType type,
        int frequency,
        boolean hasEncryption,
        boolean operational,
        long launchTime
) {
    public enum SatelliteType {
        RECON,      // 3x3 chunk surveillance, 32 CU + 160 FE/t
        WEAPON_KINETIC,  // Orbital kinetic strike, 50,000 FE per strike
        WEAPON_LASER     // Orbital laser strike, 80,000 FE per strike
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("Id", satelliteId);
        tag.putInt("Type", type.ordinal());
        tag.putInt("Frequency", frequency);
        tag.putBoolean("Encrypted", hasEncryption);
        tag.putBoolean("Operational", operational);
        tag.putLong("LaunchTime", launchTime);
        return tag;
    }

    public static SatelliteData fromTag(CompoundTag tag) {
        SatelliteType[] types = SatelliteType.values();
        int typeOrdinal = tag.getInt("Type");
        return new SatelliteData(
                tag.getUUID("Id"),
                typeOrdinal >= 0 && typeOrdinal < types.length ? types[typeOrdinal] : SatelliteType.RECON,
                tag.getInt("Frequency"),
                tag.getBoolean("Encrypted"),
                tag.getBoolean("Operational"),
                tag.getLong("LaunchTime")
        );
    }
}
