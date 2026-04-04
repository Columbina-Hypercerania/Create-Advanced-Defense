package com.lokins.cad.blockentity.network;

import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import com.lokins.cad.network.CADNetwork;
import com.lokins.cad.network.CADNetworkManager;
import com.lokins.cad.network.detection.DetectedTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Command Center BE - the CAD network brain.
 * Manages network, aggregates sensor data, assigns targets, controls alert level.
 * 64 SU + 30 FE/t.
 */
public class CommandCenterBlockEntity extends CADKineticBlockEntity {

    @Nullable
    private UUID networkId;
    private int alertLevel = 0; // 0=green, 1=yellow, 2=red
    private int totalFEConsumption = 0;

    public CommandCenterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        initEnergyStorage(50000, 500, 30); // 30 FE/t base consumption
    }

    @Override
    protected float getRequiredSpeed() { return 8f; }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        // Ensure network exists
        if (level instanceof ServerLevel serverLevel) {
            CADNetworkManager manager = CADNetworkManager.get(serverLevel);
            CADNetwork network = getOrCreateNetwork(manager);
            if (network != null) {
                network.recalculateCompute();
                totalFEConsumption = calculateTotalFEConsumption(network);
            }
        }
    }

    @Nullable
    private CADNetwork getOrCreateNetwork(CADNetworkManager manager) {
        if (networkId != null) {
            CADNetwork network = manager.getNetwork(networkId);
            if (network != null) return network;
        }
        CADNetwork network = manager.createNetwork();
        networkId = network.getNetworkId();
        network.addDevice(getBlockPos());
        return network;
    }

    private int calculateTotalFEConsumption(CADNetwork network) {
        // Base 30 FE/t + all connected device consumption
        // TODO: aggregate from all connected devices
        return 30;
    }

    public int getAlertLevel() { return alertLevel; }

    public void setAlertLevel(int level) {
        this.alertLevel = Math.max(0, Math.min(2, level));
        notifyUpdate();
    }

    public int getTotalFEConsumption() { return totalFEConsumption; }

    @Nullable
    public CADNetwork getNetwork() {
        if (level instanceof ServerLevel serverLevel && networkId != null) {
            return CADNetworkManager.get(serverLevel).getNetwork(networkId);
        }
        return null;
    }

    public List<DetectedTarget> getAllTargets() {
        CADNetwork network = getNetwork();
        return network != null ? network.getActiveTargets() : Collections.emptyList();
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        alertLevel = tag.getInt("AlertLevel");
        if (tag.hasUUID("NetworkId")) networkId = tag.getUUID("NetworkId");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("AlertLevel", alertLevel);
        if (networkId != null) tag.putUUID("NetworkId", networkId);
    }
}
