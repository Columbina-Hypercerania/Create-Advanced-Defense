package com.lokins.cad.blockentity.weapon;

import com.lokins.cad.block.weapon.AutoRotatingBaseBlock;
import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import com.lokins.cad.entity.CannonProjectileEntity;
import com.lokins.cad.item.BarrelItem;
import com.lokins.cad.item.ChargePackItem;
import com.lokins.cad.item.ShellItem;
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
 * Auto-rotating cannon base with barrel + ammo slots.
 * Requires SU + FE. Auto-targets and fires when barrel + ammo are loaded.
 */
public class AutoRotatingBaseBlockEntity extends CADKineticBlockEntity {

    private ItemStack barrel = ItemStack.EMPTY;
    private final ItemStackHandler ammoSlots = new ItemStackHandler(2); // 0=charge, 1=shell
    private int fireCooldown = 0;
    private static final int FIRE_INTERVAL = 60; // 3 seconds

    public AutoRotatingBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        // Init FE storage lazily based on block size
        if (energyStorage == null && getBlockState().getBlock() instanceof AutoRotatingBaseBlock block) {
            initEnergyStorage(10000, 200, block.getSize().fePerTick);
        }
    }

    @Override
    protected float getRequiredSpeed() {
        return 16f;
    }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        if (fireCooldown > 0) { fireCooldown--; return; }

        if (!hasBarrel() || !hasAmmo()) return;

        LivingEntity target = findTarget();
        if (target != null) {
            fireAt(target);
            fireCooldown = FIRE_INTERVAL;
        }
    }

    public boolean hasBarrel() {
        return !barrel.isEmpty() && barrel.getItem() instanceof BarrelItem;
    }

    private boolean hasAmmo() {
        return ammoSlots.getStackInSlot(0).getItem() instanceof ChargePackItem
                && ammoSlots.getStackInSlot(1).getItem() instanceof ShellItem;
    }

    @Nullable
    private LivingEntity findTarget() {
        if (level == null) return null;
        int range = 48;
        AABB area = new AABB(getBlockPos()).inflate(range);
        List<Monster> targets = level.getEntitiesOfClass(Monster.class, area,
                e -> e.isAlive() && !e.isInvisible());
        if (targets.isEmpty()) return null;
        Vec3 pos = Vec3.atCenterOf(getBlockPos());
        return targets.stream()
                .min((a, b) -> Double.compare(a.distanceToSqr(pos), b.distanceToSqr(pos)))
                .orElse(null);
    }

    private void fireAt(LivingEntity target) {
        if (level == null) return;

        BarrelItem barrelItem = (BarrelItem) barrel.getItem();
        ChargePackItem charge = (ChargePackItem) ammoSlots.getStackInSlot(0).getItem();
        ShellItem shellItem = (ShellItem) ammoSlots.getStackInSlot(1).getItem();

        Vec3 spawnPos = Vec3.atCenterOf(getBlockPos()).add(0, 1.0, 0);
        Vec3 dir = target.getEyePosition().subtract(spawnPos).normalize();
        float speed = 2.5f * charge.getGrade().rangeModifier;
        float damage = shellItem.getShellType().directDamage
                * barrelItem.getCaliber().damageMultiplier
                * charge.getGrade().damageModifier;

        CannonProjectileEntity projectile = new CannonProjectileEntity(
                level, spawnPos, dir.scale(speed), shellItem.getShellType(), damage);
        level.addFreshEntity(projectile);

        // Consume ammo
        ammoSlots.extractItem(0, 1, false);
        ammoSlots.extractItem(1, 1, false);

        notifyUpdate();
    }

    public void installBarrel(ItemStack barrelStack) {
        this.barrel = barrelStack.copyWithCount(1);
        notifyUpdate();
    }

    public ItemStackHandler getAmmoSlots() { return ammoSlots; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        barrel = tag.contains("Barrel") ? ItemStack.parseOptional(registries, tag.getCompound("Barrel")) : ItemStack.EMPTY;
        if (tag.contains("AmmoSlots")) ammoSlots.deserializeNBT(registries, tag.getCompound("AmmoSlots"));
        fireCooldown = tag.getInt("FireCooldown");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        if (!barrel.isEmpty()) tag.put("Barrel", barrel.save(registries));
        tag.put("AmmoSlots", ammoSlots.serializeNBT(registries));
        tag.putInt("FireCooldown", fireCooldown);
    }
}
