package com.lokins.cad.network.compute;

import net.minecraft.core.BlockPos;

/**
 * Base interface for any device that participates in the CU compute network.
 */
public interface IComputeNode {

    /**
     * @return the position of this node in the world
     */
    BlockPos getNodePos();

    /**
     * @return true if this node is currently connected to a CAD network
     */
    boolean isConnectedToNetwork();
}
