package com.lokins.cad.blockentity.sensor;

import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Infrared Sensor BE - detects living entities (heat signatures) within 16 blocks.
 */
public class InfraredSensorBlockEntity extends BaseSensorBlockEntity {

    public InfraredSensorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override protected int getRange() { return 16; }
    @Override protected int getScanInterval() { return 20; } // every second

    @Override
    protected boolean canDetect(LivingEntity entity) {
        return entity.isAlive() && !entity.isInvisible();
    }
}
