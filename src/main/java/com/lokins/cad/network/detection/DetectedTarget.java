package com.lokins.cad.network.detection;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

/**
 * Represents a target detected by a sensor.
 * Immutable data record passed through the CAD network.
 */
public record DetectedTarget(
        UUID entityId,
        Vec3 position,
        String entityType,
        boolean isHostile,
        int tickDetected,
        BlockPos sensorPos
) {
    /**
     * Whether this detection is still fresh (within timeout ticks).
     */
    public boolean isFresh(int currentTick, int timeoutTicks) {
        return currentTick - tickDetected < timeoutTicks;
    }
}
