package com.lokins.cad.block.satellite;

import com.lokins.cad.blockentity.satellite.SatelliteLaunchPadBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Satellite Launch Pad - multi-block structure placeholder (single block for now).
 * Manufactures and launches satellite payloads into orbit.
 */
public class SatelliteLaunchPadBlock extends Block implements IBE<SatelliteLaunchPadBlockEntity> {

    public SatelliteLaunchPadBlock(Properties properties) {
        super(properties);
    }

    @Override public Class<SatelliteLaunchPadBlockEntity> getBlockEntityClass() { return SatelliteLaunchPadBlockEntity.class; }
    @Override public BlockEntityType<? extends SatelliteLaunchPadBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.SATELLITE_LAUNCH_PAD.get(); }
}
