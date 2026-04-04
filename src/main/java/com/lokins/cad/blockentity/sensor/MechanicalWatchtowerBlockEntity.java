package com.lokins.cad.blockentity.sensor;

import com.lokins.cad.block.sensor.BaseSensorBlock;
import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import com.lokins.cad.network.compute.IComputeConsumer;
import com.lokins.cad.network.detection.DetectedTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * Mechanical Watchtower BE - Tier 2 optical scanner.
 * 32 block range, requires line of sight. 32 SU, gear-driven.
 * Can distinguish entity types with filters.
 */
public class MechanicalWatchtowerBlockEntity extends CADKineticBlockEntity implements IComputeConsumer {

    private static final int RANGE = 32;
    private final List<DetectedTarget> detectedTargets = new ArrayList<>();
    private int allocatedCU = 0;
    private int scanCooldown = 0;

    public MechanicalWatchtowerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected float getRequiredSpeed() { return 8f; }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        if (scanCooldown > 0) { scanCooldown--; return; }
        scanCooldown = 20;

        detectedTargets.clear();
        AABB area = new AABB(getBlockPos()).inflate(RANGE);
        Vec3 sensorPos = Vec3.atCenterOf(getBlockPos()).add(0, 0.5, 0);

        int tickCount = level.getServer() != null ? level.getServer().getTickCount() : 0;

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && hasLineOfSight(sensorPos, e));

        for (LivingEntity entity : entities) {
            detectedTargets.add(new DetectedTarget(
                    entity.getUUID(), entity.position(),
                    entity.getType().toShortString(),
                    entity instanceof net.minecraft.world.entity.monster.Monster,
                    tickCount, getBlockPos()
            ));
        }

        // Update redstone
        int power = Math.min(15, detectedTargets.size());
        BlockState state = getBlockState();
        if (state.hasProperty(BaseSensorBlock.POWER) &&
                state.getValue(BaseSensorBlock.POWER) != power) {
            level.setBlockAndUpdate(getBlockPos(), state.setValue(BaseSensorBlock.POWER, power));
        }
    }

    private boolean hasLineOfSight(Vec3 from, LivingEntity target) {
        if (level == null) return false;
        Vec3 to = target.getEyePosition();
        BlockHitResult result = level.clip(new ClipContext(
                from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, target));
        return result.getType() == HitResult.Type.MISS
                || result.getBlockPos().equals(target.blockPosition());
    }

    public List<DetectedTarget> getDetectedTargets() { return detectedTargets; }

    // IComputeConsumer
    @Override public BlockPos getNodePos() { return getBlockPos(); }
    @Override public boolean isConnectedToNetwork() { return true; }
    @Override public int getRequiredComputeUnits() { return 4; }
    @Override public int getAllocatedComputeUnits() { return allocatedCU; }
    @Override public void setAllocatedComputeUnits(int allocated) { this.allocatedCU = allocated; }
}
