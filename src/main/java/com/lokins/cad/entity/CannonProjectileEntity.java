package com.lokins.cad.entity;

import com.lokins.cad.item.ShellItem;
import com.lokins.cad.registry.CADEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Cannon projectile entity. Behavior varies by shell type.
 */
public class CannonProjectileEntity extends AbstractHurtingProjectile {

    private static final EntityDataAccessor<Float> DAMAGE =
            SynchedEntityData.defineId(CannonProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> SHELL_TYPE_ORDINAL =
            SynchedEntityData.defineId(CannonProjectileEntity.class, EntityDataSerializers.INT);

    public CannonProjectileEntity(EntityType<? extends CannonProjectileEntity> type, Level level) {
        super(type, level);
    }

    public CannonProjectileEntity(Level level, Vec3 pos, Vec3 velocity,
                                   ShellItem.ShellType shellType, float damage) {
        super(CADEntityTypes.CANNON_PROJECTILE.get(), level);
        this.setPos(pos);
        this.setDeltaMovement(velocity);
        this.entityData.set(DAMAGE, damage);
        this.entityData.set(SHELL_TYPE_ORDINAL, shellType.ordinal());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DAMAGE, 6.0f);
        builder.define(SHELL_TYPE_ORDINAL, 0);
    }

    private ShellItem.ShellType getShellType() {
        int ordinal = entityData.get(SHELL_TYPE_ORDINAL);
        ShellItem.ShellType[] types = ShellItem.ShellType.values();
        return ordinal >= 0 && ordinal < types.length ? types[ordinal] : ShellItem.ShellType.SOLID;
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > 200) { discard(); return; }
        if (level().isClientSide()) {
            level().addParticle(ParticleTypes.LARGE_SMOKE, getX(), getY(), getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (level().isClientSide()) return;

        Entity target = result.getEntity();
        ShellItem.ShellType type = getShellType();
        float damage = entityData.get(DAMAGE);

        if (target instanceof LivingEntity living) {
            living.hurt(damageSources().thrown(this, getOwner()), damage);
            if (type.setsFire) living.igniteForSeconds(5);
        }

        if (type.explosionRadius > 0 && level() instanceof ServerLevel serverLevel) {
            level().explode(this, getX(), getY(), getZ(),
                    type.explosionRadius, type.setsFire,
                    Level.ExplosionInteraction.MOB);
        }
        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (level().isClientSide()) return;

        ShellItem.ShellType type = getShellType();
        if (type.explosionRadius > 0) {
            level().explode(this, getX(), getY(), getZ(),
                    type.explosionRadius, type.setsFire,
                    Level.ExplosionInteraction.MOB);
        }
        discard();
    }

    @Override
    protected boolean shouldBurn() { return false; }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", entityData.get(DAMAGE));
        tag.putInt("ShellType", entityData.get(SHELL_TYPE_ORDINAL));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Damage")) entityData.set(DAMAGE, tag.getFloat("Damage"));
        if (tag.contains("ShellType")) entityData.set(SHELL_TYPE_ORDINAL, tag.getInt("ShellType"));
    }
}
