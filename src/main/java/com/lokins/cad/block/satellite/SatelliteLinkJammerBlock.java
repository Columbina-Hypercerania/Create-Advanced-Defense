package com.lokins.cad.block.satellite;

import com.lokins.cad.blockentity.satellite.SatelliteLinkJammerBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Satellite Link Jammer - multi-block structure placeholder.
 * Blocks satellite communication in 3x3 chunk area. 150 FE/t + 16 CU.
 * Must be tuned to target satellite frequency (requires frequency analysis first).
 */
public class SatelliteLinkJammerBlock extends Block implements IBE<SatelliteLinkJammerBlockEntity> {

    public SatelliteLinkJammerBlock(Properties properties) {
        super(properties);
    }

    @Override public Class<SatelliteLinkJammerBlockEntity> getBlockEntityClass() { return SatelliteLinkJammerBlockEntity.class; }
    @Override public BlockEntityType<? extends SatelliteLinkJammerBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.SATELLITE_LINK_JAMMER.get(); }
}
