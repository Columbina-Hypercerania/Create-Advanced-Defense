package com.lokins.cad.blockentity.defense;

import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Shield Generator BE - maintains a force field.
 * 256 SU + 100 FE/t base. Shield HP absorbs projectile damage.
 */
public class ShieldGeneratorBlockEntity extends CADKineticBlockEntity {

    private float shieldHP = 100f;
    private float maxShieldHP = 100f;
    private int shieldRadius = 8;

    public ShieldGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        initEnergyStorage(100000, 1000, 100); // 100 FE/t idle
    }

    @Override protected float getRequiredSpeed() { return 16f; }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        // Regenerate shield HP slowly when active
        if (shieldHP < maxShieldHP) {
            shieldHP = Math.min(maxShieldHP, shieldHP + 0.1f);
        }

        // TODO: actual shield field rendering and projectile blocking
    }

    @Override
    protected void onDeactivate() {
        // Shield drops when power lost
        shieldHP = 0;
    }

    public void damageShield(float amount) {
        shieldHP = Math.max(0, shieldHP - amount);
        // Increased FE consumption under fire is handled by the energy system
    }

    public float getShieldHP() { return shieldHP; }
    public float getMaxShieldHP() { return maxShieldHP; }
    public int getShieldRadius() { return shieldRadius; }
    public boolean isShieldUp() { return isActive() && shieldHP > 0; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        shieldHP = tag.getFloat("ShieldHP");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putFloat("ShieldHP", shieldHP);
    }
}
