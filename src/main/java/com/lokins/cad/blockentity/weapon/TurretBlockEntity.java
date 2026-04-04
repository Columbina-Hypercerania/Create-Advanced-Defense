package com.lokins.cad.blockentity.weapon;

import com.lokins.cad.block.weapon.TurretBlock;
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
 * Generic turret BE for all P10 weapon types.
 * Behavior is configured by the TurretBlock's TurretType.
 */
public class TurretBlockEntity extends CADKineticBlockEntity {

    private final ItemStackHandler ammoInventory = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof BulletItem;
        }
    };
    private int fireCooldown = 0;
    private int burstCount = 0; // for revolver burst mode

    public TurretBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected float getRequiredSpeed() {
        if (getBlockState().getBlock() instanceof TurretBlock turret) {
            return turret.getTurretType().minSpeed;
        }
        return 16f;
    }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;
        if (!(getBlockState().getBlock() instanceof TurretBlock turret)) return;

        TurretBlock.TurretType type = turret.getTurretType();

        // FE for MLRS
        if (type == TurretBlock.TurretType.MLRS) {
            if (energyStorage == null) initEnergyStorage(10000, 200, 10);
        }

        if (fireCooldown > 0) { fireCooldown--; return; }

        LivingEntity target = findTarget(type.range);
        if (target != null && consumeAmmo()) {
            fireAt(target, type);

            // Revolver burst: fire 6 rapid shots
            if (type == TurretBlock.TurretType.REVOLVER_TURRET) {
                burstCount++;
                fireCooldown = burstCount < 6 ? 4 : type.baseCooldown;
                if (burstCount >= 6) burstCount = 0;
            } else {
                fireCooldown = calculateCooldown(type);
            }
        }
    }

    @Nullable
    private LivingEntity findTarget(int range) {
        if (level == null) return null;
        AABB area = new AABB(getBlockPos()).inflate(range);
        List<Monster> targets = level.getEntitiesOfClass(Monster.class, area,
                e -> e.isAlive() && !e.isInvisible());
        if (targets.isEmpty()) return null;
        Vec3 pos = Vec3.atCenterOf(getBlockPos());
        return targets.stream()
                .min((a, b) -> Double.compare(a.distanceToSqr(pos), b.distanceToSqr(pos)))
                .orElse(null);
    }

    private boolean consumeAmmo() {
        ItemStack ammo = ammoInventory.getStackInSlot(0);
        if (ammo.isEmpty()) return false;
        ammo.shrink(1);
        return true;
    }

    private void fireAt(LivingEntity target, TurretBlock.TurretType type) {
        if (level == null) return;
        Vec3 spawnPos = Vec3.atCenterOf(getBlockPos()).add(0, 0.5, 0);
        Vec3 dir = target.getEyePosition().subtract(spawnPos).normalize();

        float damage = type.baseDamage;
        ItemStack ammo = ammoInventory.getStackInSlot(0);
        if (ammo.getItem() instanceof BulletItem bullet) {
            damage *= bullet.getDamageModifier();
        }

        float speed = type == TurretBlock.TurretType.STEAM_CANNON ? 3.5f : 2.0f;
        BulletProjectileEntity projectile = new BulletProjectileEntity(level, spawnPos, dir.scale(speed));
        projectile.setDamage(damage);
        level.addFreshEntity(projectile);
    }

    private int calculateCooldown(TurretBlock.TurretType type) {
        float speed = Math.abs(getSpeed());
        float ratio = Math.min(1.0f, (speed - type.minSpeed) / (256f - type.minSpeed));
        int minCooldown = Math.max(2, type.baseCooldown / 4);
        return (int) (type.baseCooldown - ratio * (type.baseCooldown - minCooldown));
    }

    public ItemStackHandler getAmmoInventory() { return ammoInventory; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Ammo")) ammoInventory.deserializeNBT(registries, tag.getCompound("Ammo"));
        fireCooldown = tag.getInt("FireCooldown");
        burstCount = tag.getInt("BurstCount");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.put("Ammo", ammoInventory.serializeNBT(registries));
        tag.putInt("FireCooldown", fireCooldown);
        tag.putInt("BurstCount", burstCount);
    }
}
