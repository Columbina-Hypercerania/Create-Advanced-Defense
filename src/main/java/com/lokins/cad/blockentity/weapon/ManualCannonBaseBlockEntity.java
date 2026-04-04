package com.lokins.cad.blockentity.weapon;

import com.lokins.cad.block.weapon.ManualCannonBaseBlock;
import com.lokins.cad.entity.CannonProjectileEntity;
import com.lokins.cad.item.BarrelItem;
import com.lokins.cad.item.ChargePackItem;
import com.lokins.cad.item.ShellItem;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Manual Cannon Base BlockEntity.
 * Holds barrel, charge pack, and shell. Player fires manually.
 */
public class ManualCannonBaseBlockEntity extends SmartBlockEntity {

    private ItemStack barrel = ItemStack.EMPTY;
    private ItemStack chargePack = ItemStack.EMPTY;
    private ItemStack shell = ItemStack.EMPTY;

    public ManualCannonBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    public boolean hasBarrel() {
        return !barrel.isEmpty() && barrel.getItem() instanceof BarrelItem;
    }

    public void installBarrel(ItemStack barrelStack) {
        this.barrel = barrelStack.copyWithCount(1);
        notifyUpdate();
    }

    public boolean tryLoadAmmo(ItemStack stack) {
        if (stack.getItem() instanceof ChargePackItem && chargePack.isEmpty()) {
            chargePack = stack.copyWithCount(1);
            notifyUpdate();
            return true;
        }
        if (stack.getItem() instanceof ShellItem && shell.isEmpty()) {
            shell = stack.copyWithCount(1);
            notifyUpdate();
            return true;
        }
        return false;
    }

    public boolean canFire() {
        return hasBarrel() && !chargePack.isEmpty() && !shell.isEmpty();
    }

    public void fire(Player player) {
        if (level == null || !canFire()) return;

        BarrelItem barrelItem = (BarrelItem) barrel.getItem();
        ChargePackItem chargeItem = (ChargePackItem) chargePack.getItem();
        ShellItem shellItem = (ShellItem) shell.getItem();

        Direction facing = getBlockState().getValue(ManualCannonBaseBlock.FACING);
        Vec3 spawnPos = Vec3.atCenterOf(getBlockPos()).add(
                facing.getStepX() * 1.2, 0.5, facing.getStepZ() * 1.2);

        Vec3 lookDir = player.getLookAngle().normalize();
        float speed = 2.5f * chargeItem.getGrade().rangeModifier;

        float damage = shellItem.getShellType().directDamage
                * barrelItem.getCaliber().damageMultiplier
                * chargeItem.getGrade().damageModifier;

        CannonProjectileEntity projectile = new CannonProjectileEntity(level, spawnPos,
                lookDir.scale(speed), shellItem.getShellType(), damage);
        projectile.setOwner(player);
        level.addFreshEntity(projectile);

        // Consume ammo
        chargePack = ItemStack.EMPTY;
        shell = ItemStack.EMPTY;

        // Barrel wear
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            float wearMultiplier = chargeItem.getGrade().wearModifier;
            barrel.hurtAndBreak((int) Math.ceil(wearMultiplier), serverLevel, player, item -> {
                barrel = ItemStack.EMPTY;
            });
        }

        notifyUpdate();
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        barrel = tag.contains("Barrel") ? ItemStack.parseOptional(registries, tag.getCompound("Barrel")) : ItemStack.EMPTY;
        chargePack = tag.contains("ChargePack") ? ItemStack.parseOptional(registries, tag.getCompound("ChargePack")) : ItemStack.EMPTY;
        shell = tag.contains("Shell") ? ItemStack.parseOptional(registries, tag.getCompound("Shell")) : ItemStack.EMPTY;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        if (!barrel.isEmpty()) tag.put("Barrel", barrel.save(registries));
        if (!chargePack.isEmpty()) tag.put("ChargePack", chargePack.save(registries));
        if (!shell.isEmpty()) tag.put("Shell", shell.save(registries));
    }
}
