package com.lokins.cad.blockentity.defense;

import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mechanical Gate BE - controls gate open/close with speed-proportional crushing damage.
 */
public class MechanicalGateBlockEntity extends CADKineticBlockEntity {

    private boolean gateOpen = false;
    private float gateProgress = 0; // 0=closed, 1=open

    public MechanicalGateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override protected float getRequiredSpeed() { return 8f; }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        float speed = Math.abs(getSpeed());
        float moveSpeed = speed / 256f * 0.1f; // gate movement per tick

        if (gateOpen && gateProgress < 1.0f) {
            gateProgress = Math.min(1.0f, gateProgress + moveSpeed);
        } else if (!gateOpen && gateProgress > 0) {
            gateProgress = Math.max(0, gateProgress - moveSpeed);
            // TODO: crush damage to entities in gate path, proportional to speed
        }
    }

    public void toggleGate() {
        gateOpen = !gateOpen;
        notifyUpdate();
    }

    public boolean isGateOpen() { return gateOpen; }
    public float getGateProgress() { return gateProgress; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        gateOpen = tag.getBoolean("GateOpen");
        gateProgress = tag.getFloat("GateProgress");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean("GateOpen", gateOpen);
        tag.putFloat("GateProgress", gateProgress);
    }
}
