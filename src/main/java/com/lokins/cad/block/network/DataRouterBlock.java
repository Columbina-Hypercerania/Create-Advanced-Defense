package com.lokins.cad.block.network;

import net.minecraft.world.level.block.Block;

/**
 * Data Router - hub node that merges/distributes data link connections.
 * Supports channel configuration and priority (TODO: GUI in future).
 */
public class DataRouterBlock extends Block {

    public DataRouterBlock(Properties properties) {
        super(properties);
    }
}
