package com.lokins.cad.network;

import com.lokins.cad.network.compute.IComputeConsumer;
import com.lokins.cad.network.compute.IComputeProvider;
import com.lokins.cad.network.detection.DetectedTarget;
import net.minecraft.core.BlockPos;

import java.util.*;

/**
 * Represents a single CAD tactical network instance.
 * Manages connected devices, compute allocation, and target data.
 */
public class CADNetwork {

    private final UUID networkId = UUID.randomUUID();
    private final Set<BlockPos> connectedDevices = new HashSet<>();
    private final List<IComputeProvider> computeProviders = new ArrayList<>();
    private final List<IComputeConsumer> computeConsumers = new ArrayList<>();
    private final List<DetectedTarget> activeTargets = new ArrayList<>();

    private int totalCU = 0;
    private int usedCU = 0;
    private int alertLevel = 0; // 0=green, 1=yellow, 2=red

    public void addDevice(BlockPos pos) {
        connectedDevices.add(pos);
    }

    public void removeDevice(BlockPos pos) {
        connectedDevices.remove(pos);
    }

    public void registerProvider(IComputeProvider provider) {
        if (!computeProviders.contains(provider)) computeProviders.add(provider);
    }

    public void unregisterProvider(IComputeProvider provider) {
        computeProviders.remove(provider);
    }

    public void registerConsumer(IComputeConsumer consumer) {
        if (!computeConsumers.contains(consumer)) computeConsumers.add(consumer);
    }

    public void unregisterConsumer(IComputeConsumer consumer) {
        computeConsumers.remove(consumer);
    }

    /**
     * Recalculate compute allocation across all consumers.
     * Called periodically by the command center or network tick.
     */
    public void recalculateCompute() {
        totalCU = 0;
        for (IComputeProvider provider : computeProviders) {
            if (provider.isProviderActive()) {
                totalCU += provider.getAvailableComputeUnits();
            }
        }

        int totalDemand = 0;
        for (IComputeConsumer consumer : computeConsumers) {
            totalDemand += consumer.getRequiredComputeUnits();
        }

        usedCU = 0;
        if (totalDemand <= totalCU) {
            // Enough CU for everyone
            for (IComputeConsumer consumer : computeConsumers) {
                consumer.setAllocatedComputeUnits(consumer.getRequiredComputeUnits());
                usedCU += consumer.getRequiredComputeUnits();
            }
        } else {
            // Proportional allocation
            float ratio = totalCU > 0 ? (float) totalCU / totalDemand : 0;
            for (IComputeConsumer consumer : computeConsumers) {
                int allocated = (int) (consumer.getRequiredComputeUnits() * ratio);
                consumer.setAllocatedComputeUnits(allocated);
                usedCU += allocated;
            }
        }
    }

    public void updateTargets(List<DetectedTarget> newTargets) {
        activeTargets.clear();
        activeTargets.addAll(newTargets);
    }

    // Getters
    public UUID getNetworkId() { return networkId; }
    public int getTotalCU() { return totalCU; }
    public int getUsedCU() { return usedCU; }
    public float getComputeRatio() { return totalCU > 0 ? (float) usedCU / totalCU : 0; }
    public int getAlertLevel() { return alertLevel; }
    public void setAlertLevel(int level) { this.alertLevel = Math.max(0, Math.min(2, level)); }
    public List<DetectedTarget> getActiveTargets() { return Collections.unmodifiableList(activeTargets); }
    public Set<BlockPos> getConnectedDevices() { return Collections.unmodifiableSet(connectedDevices); }
    public int getDeviceCount() { return connectedDevices.size(); }
}
