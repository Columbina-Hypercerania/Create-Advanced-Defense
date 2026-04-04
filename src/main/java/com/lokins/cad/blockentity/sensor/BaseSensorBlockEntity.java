package com.lokins.cad.blockentity.sensor;

import com.lokins.cad.block.sensor.BaseSensorBlock;
import com.lokins.cad.network.compute.IComputeConsumer;
import com.lokins.cad.network.detection.DetectedTarget;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

/**
 * Base BlockEntity for all sensors.
 * Scans for entities, outputs redstone signal, and feeds data to CAD network.
 */
public abstract class BaseSensorBlockEntity extends SmartBlockEntity implements IComputeConsumer {

    protected final List<DetectedTarget> detectedTargets = new ArrayList<>();
    private int allocatedCU = 0;
    private int scanCooldown = 0;

    public BaseSensorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        if (scanCooldown > 0) { scanCooldown--; return; }
        scanCooldown = getScanInterval();

        detectedTargets.clear();
        AABB scanArea = new AABB(getBlockPos()).inflate(getRange());

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, scanArea,
                this::canDetect);

        int tickCount = level.getServer() != null ? level.getServer().getTickCount() : 0;
        for (LivingEntity entity : entities) {
            detectedTargets.add(new DetectedTarget(
                    entity.getUUID(),
                    entity.position(),
                    entity.getType().toShortString(),
                    isHostile(entity),
                    tickCount,
                    getBlockPos()
            ));
        }

        // Update redstone output
        int power = Math.min(15, detectedTargets.size());
        BlockState state = getBlockState();
        if (state.getValue(BaseSensorBlock.POWER) != power) {
            level.setBlockAndUpdate(getBlockPos(), state.setValue(BaseSensorBlock.POWER, power));
        }
    }

    /** Detection range in blocks */
    protected abstract int getRange();

    /** Ticks between scans */
    protected abstract int getScanInterval();

    /** Whether this sensor can detect the given entity */
    protected abstract boolean canDetect(LivingEntity entity);

    /** Whether the entity should be classified as hostile */
    protected boolean isHostile(LivingEntity entity) {
        return entity instanceof net.minecraft.world.entity.monster.Monster;
    }

    public List<DetectedTarget> getDetectedTargets() {
        return detectedTargets;
    }

    // IComputeConsumer
    @Override public BlockPos getNodePos() { return getBlockPos(); }
    @Override public boolean isConnectedToNetwork() { return true; } // TODO: actual network check
    @Override public int getRequiredComputeUnits() { return 1; }
    @Override public int getAllocatedComputeUnits() { return allocatedCU; }
    @Override public void setAllocatedComputeUnits(int allocated) { this.allocatedCU = allocated; }
}
