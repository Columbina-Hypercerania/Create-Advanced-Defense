package com.lokins.cad.blockentity.weapon;

import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import com.lokins.cad.entity.BulletProjectileEntity;
import com.lokins.cad.item.BulletItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Electromagnetic Weapon BE - high-velocity projectile weapon.
 * 128 SU + 200 FE/t firing / 20 FE/t standby. Fast fire rate, high penetration.
 */
public class ElectromagneticWeaponBlockEntity extends CADKineticBlockEntity {

    private final ItemStackHandler ammoSlot = new ItemStackHandler(1) {
        @Override public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof BulletItem;
        }
    };
    private int fireCooldown = 0;
    private boolean firing = false;

    public ElectromagneticWeaponBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        initEnergyStorage(50000, 500, 20); // 20 FE/t standby
    }

    @Override protected float getRequiredSpeed() { return 32f; }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        if (fireCooldown > 0) { fireCooldown--; return; }

        LivingEntity target = findTarget();
        if (target != null && consumeAmmo() && energyStorage != null && energyStorage.consumeBurst(180)) {
            // Extra 180 FE per shot on top of standby
            firing = true;
            Vec3 spawnPos = Vec3.atCenterOf(getBlockPos()).add(0, 0.5, 0);
            Vec3 dir = target.getEyePosition().subtract(spawnPos).normalize();

            BulletProjectileEntity projectile = new BulletProjectileEntity(level, spawnPos, dir.scale(5.0f));
            projectile.setDamage(12.0f);
            level.addFreshEntity(projectile);

            fireCooldown = 8; // Very fast fire rate
        } else {
            firing = false;
        }
    }

    @Nullable
    private LivingEntity findTarget() {
        if (level == null) return null;
        AABB area = new AABB(getBlockPos()).inflate(48);
        List<Monster> targets = level.getEntitiesOfClass(Monster.class, area, e -> e.isAlive());
        if (targets.isEmpty()) return null;
        Vec3 pos = Vec3.atCenterOf(getBlockPos());
        return targets.stream().min((a, b) -> Double.compare(a.distanceToSqr(pos), b.distanceToSqr(pos))).orElse(null);
    }

    private boolean consumeAmmo() {
        ItemStack ammo = ammoSlot.getStackInSlot(0);
        if (ammo.isEmpty()) return false;
        ammo.shrink(1);
        return true;
    }

    public ItemStackHandler getAmmoSlot() { return ammoSlot; }
    public boolean isFiring() { return firing; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Ammo")) ammoSlot.deserializeNBT(registries, tag.getCompound("Ammo"));
        fireCooldown = tag.getInt("FireCooldown");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.put("Ammo", ammoSlot.serializeNBT(registries));
        tag.putInt("FireCooldown", fireCooldown);
    }
}
