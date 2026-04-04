package com.lokins.cad.blockentity.weapon;

import com.lokins.cad.block.weapon.AutoSentryBlock;
import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import com.lokins.cad.entity.BulletProjectileEntity;
import com.lokins.cad.item.BulletItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
 * Auto Sentry BlockEntity.
 * - Scans for hostile mobs in facing direction within range
 * - Fires bullets from ammo inventory
 * - Fire rate scales with RPM (higher RPM = faster shooting)
 * - 32 SU consumption, no FE
 */
public class AutoSentryBlockEntity extends CADKineticBlockEntity {

    public static final float REQUIRED_SPEED = 16f;
    public static final float BASE_DAMAGE = 4.0f;
    public static final int RANGE = 24;
    public static final int BASE_FIRE_COOLDOWN = 40; // ticks at minimum RPM
    public static final int MIN_FIRE_COOLDOWN = 10;  // ticks at max effective RPM

    private final ItemStackHandler ammoInventory = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof BulletItem;
        }
    };

    private int fireCooldown = 0;

    public AutoSentryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        // Pure mechanical, no FE storage needed
    }

    @Override
    protected float getRequiredSpeed() {
        return REQUIRED_SPEED;
    }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        if (fireCooldown > 0) {
            fireCooldown--;
            return;
        }

        LivingEntity target = findTarget();
        if (target != null && consumeAmmo()) {
            fireAt(target);
            fireCooldown = calculateFireCooldown();
        }
    }

    /**
     * Find the nearest hostile mob in range, prioritizing the facing direction.
     */
    @Nullable
    private LivingEntity findTarget() {
        if (level == null) return null;

        BlockPos pos = getBlockPos();
        AABB scanArea = new AABB(pos).inflate(RANGE);

        List<Monster> targets = level.getEntitiesOfClass(Monster.class, scanArea,
                entity -> entity.isAlive() && !entity.isInvisible());

        if (targets.isEmpty()) return null;

        // Return nearest
        Vec3 sentryPos = Vec3.atCenterOf(pos);
        return targets.stream()
                .min((a, b) -> Double.compare(
                        a.position().distanceToSqr(sentryPos),
                        b.position().distanceToSqr(sentryPos)))
                .orElse(null);
    }

    /**
     * Try to consume one bullet from the ammo slot.
     */
    private boolean consumeAmmo() {
        ItemStack ammo = ammoInventory.getStackInSlot(0);
        if (ammo.isEmpty()) return false;
        ammo.shrink(1);
        return true;
    }

    /**
     * Fire a bullet at the target.
     */
    private void fireAt(LivingEntity target) {
        if (level == null) return;

        BlockPos pos = getBlockPos();
        Direction facing = getBlockState().getValue(AutoSentryBlock.FACING);
        Vec3 spawnPos = Vec3.atCenterOf(pos).add(
                facing.getStepX() * 0.7,
                0.3,
                facing.getStepZ() * 0.7
        );

        Vec3 targetPos = target.getEyePosition();
        Vec3 direction = targetPos.subtract(spawnPos).normalize();
        float speed = 2.0f;
        Vec3 velocity = direction.scale(speed);

        // Calculate damage based on ammo type
        float damage = BASE_DAMAGE;
        ItemStack ammoStack = ammoInventory.getStackInSlot(0);
        if (ammoStack.getItem() instanceof BulletItem bullet) {
            damage *= bullet.getDamageModifier();
        }

        BulletProjectileEntity projectile = new BulletProjectileEntity(level, spawnPos, velocity);
        projectile.setDamage(damage);
        level.addFreshEntity(projectile);
    }

    /**
     * Fire cooldown decreases with higher RPM (faster shooting).
     * At 16 RPM (min): 40 ticks (2 seconds)
     * At 256 RPM: 10 ticks (0.5 seconds)
     */
    private int calculateFireCooldown() {
        float speed = Math.abs(getSpeed());
        float ratio = Math.min(1.0f, (speed - REQUIRED_SPEED) / (256f - REQUIRED_SPEED));
        return (int) (BASE_FIRE_COOLDOWN - ratio * (BASE_FIRE_COOLDOWN - MIN_FIRE_COOLDOWN));
    }

    public ItemStackHandler getAmmoInventory() {
        return ammoInventory;
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        if (compound.contains("Ammo")) {
            ammoInventory.deserializeNBT(registries, compound.getCompound("Ammo"));
        }
        fireCooldown = compound.getInt("FireCooldown");
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.put("Ammo", ammoInventory.serializeNBT(registries));
        compound.putInt("FireCooldown", fireCooldown);
    }
}
