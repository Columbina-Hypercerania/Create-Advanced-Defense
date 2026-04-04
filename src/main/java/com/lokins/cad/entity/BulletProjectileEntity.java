package com.lokins.cad.entity;

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

public class BulletProjectileEntity extends AbstractHurtingProjectile {

    private static final EntityDataAccessor<Float> DAMAGE =
            SynchedEntityData.defineId(BulletProjectileEntity.class, EntityDataSerializers.FLOAT);

    private static final float DEFAULT_DAMAGE = 4.0f;
    private int maxLifeTicks = 100; // 5 seconds

    public BulletProjectileEntity(EntityType<? extends BulletProjectileEntity> type, Level level) {
        super(type, level);
    }

    public BulletProjectileEntity(Level level, Vec3 pos, Vec3 velocity) {
        super(CADEntityTypes.BULLET_PROJECTILE.get(), level);
        this.setPos(pos);
        this.setDeltaMovement(velocity);
        this.entityData.set(DAMAGE, DEFAULT_DAMAGE);
    }

    public void setDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DAMAGE, DEFAULT_DAMAGE);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > maxLifeTicks) {
            this.discard();
            return;
        }
        // Trail particles on client
        if (level().isClientSide()) {
            level().addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (level().isClientSide()) return;

        Entity target = result.getEntity();
        if (target instanceof LivingEntity living) {
            living.hurt(damageSources().thrown(this, getOwner()), getDamage());
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide()) {
            // Impact particle
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CRIT,
                        result.getLocation().x, result.getLocation().y, result.getLocation().z,
                        3, 0.1, 0.1, 0.1, 0.02);
            }
            this.discard();
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", getDamage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Damage")) {
            setDamage(tag.getFloat("Damage"));
        }
    }
}
