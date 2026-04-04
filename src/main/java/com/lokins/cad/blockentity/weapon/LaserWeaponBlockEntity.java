package com.lokins.cad.blockentity.weapon;

import com.lokins.cad.capability.CADEnergyStorage;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Laser Weapon BE - sustained beam damage.
 * No ammo, but lens has durability (~1000 shots). 300 FE/t firing.
 * Effective against heavy armor and shields. Highest heat generation.
 */
public class LaserWeaponBlockEntity extends SmartBlockEntity {

    private final CADEnergyStorage energyStorage = new CADEnergyStorage(100000, 1000, 10); // 10 FE/t standby
    private int lensDurability = 1000;
    private boolean firing = false;
    private int heatLevel = 0;
    private static final int MAX_HEAT = 200;
    private static final int OVERHEAT_COOLDOWN = 100;
    private int cooldownTicks = 0;

    public LaserWeaponBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        energyStorage.consumeTick(); // standby consumption

        // Cooling
        if (cooldownTicks > 0) { cooldownTicks--; firing = false; return; }
        if (heatLevel > 0 && !firing) heatLevel = Math.max(0, heatLevel - 2);

        if (lensDurability <= 0) { firing = false; return; } // Lens broken

        LivingEntity target = findTarget();
        if (target != null && energyStorage.consumeBurst(290)) { // 300 total = 10 standby + 290 extra
            firing = true;
            heatLevel += 3;

            // Beam damage - continuous
            target.hurt(level.damageSources().magic(), 8.0f); // high damage, magic type bypasses armor
            lensDurability--;

            // Overheat check
            if (heatLevel >= MAX_HEAT) {
                cooldownTicks = OVERHEAT_COOLDOWN;
                heatLevel = MAX_HEAT;
            }
        } else {
            firing = false;
        }
    }

    @Nullable
    private LivingEntity findTarget() {
        if (level == null) return null;
        AABB area = new AABB(getBlockPos()).inflate(32);
        List<Monster> targets = level.getEntitiesOfClass(Monster.class, area, e -> e.isAlive());
        if (targets.isEmpty()) return null;
        Vec3 pos = Vec3.atCenterOf(getBlockPos());
        return targets.stream().min((a, b) -> Double.compare(a.distanceToSqr(pos), b.distanceToSqr(pos))).orElse(null);
    }

    public boolean isFiring() { return firing; }
    public int getLensDurability() { return lensDurability; }
    public int getHeatLevel() { return heatLevel; }
    public boolean isOverheated() { return cooldownTicks > 0; }
    public CADEnergyStorage getEnergyStorage() { return energyStorage; }

    public void replaceLens() { lensDurability = 1000; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Energy")) energyStorage.setEnergy(tag.getInt("Energy"));
        lensDurability = tag.getInt("LensDurability");
        heatLevel = tag.getInt("HeatLevel");
        cooldownTicks = tag.getInt("CooldownTicks");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Energy", energyStorage.getEnergyStored());
        tag.putInt("LensDurability", lensDurability);
        tag.putInt("HeatLevel", heatLevel);
        tag.putInt("CooldownTicks", cooldownTicks);
    }
}
