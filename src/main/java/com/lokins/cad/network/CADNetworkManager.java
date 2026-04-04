package com.lokins.cad.network;

import net.minecraft.server.level.ServerLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Global manager for all CAD networks in a world.
 * Each connected group of data link cables forms one network.
 */
public class CADNetworkManager {

    private static final Map<ServerLevel, CADNetworkManager> instances = new HashMap<>();

    private final Map<UUID, CADNetwork> networks = new HashMap<>();

    public static CADNetworkManager get(ServerLevel level) {
        return instances.computeIfAbsent(level, k -> new CADNetworkManager());
    }

    public static void clearAll() {
        instances.clear();
    }

    public CADNetwork createNetwork() {
        CADNetwork network = new CADNetwork();
        networks.put(network.getNetworkId(), network);
        return network;
    }

    public CADNetwork getNetwork(UUID id) {
        return networks.get(id);
    }

    public void removeNetwork(UUID id) {
        networks.remove(id);
    }

    public void tickAll() {
        for (CADNetwork network : networks.values()) {
            network.recalculateCompute();
        }
    }
}
