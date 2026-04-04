package com.lokins.cad.registry;

import com.lokins.cad.CreateAttackDefense;
import com.lokins.cad.entity.BulletProjectileEntity;
import com.lokins.cad.entity.CannonProjectileEntity;
import net.minecraft.world.entity.MobCategory;
import com.tterrag.registrate.util.entry.EntityEntry;

import static com.lokins.cad.CreateAttackDefense.REGISTRATE;

public class CADEntityTypes {

    public static final EntityEntry<BulletProjectileEntity> BULLET_PROJECTILE = REGISTRATE
            .<BulletProjectileEntity>entity("bullet_projectile", BulletProjectileEntity::new, MobCategory.MISC)
            .properties(b -> b.sized(0.25f, 0.25f)
                    .clientTrackingRange(8)
                    .updateInterval(2)
                    .fireImmune())
            .register();

    public static final EntityEntry<CannonProjectileEntity> CANNON_PROJECTILE = REGISTRATE
            .<CannonProjectileEntity>entity("cannon_projectile", CannonProjectileEntity::new, MobCategory.MISC)
            .properties(b -> b.sized(0.4f, 0.4f)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .fireImmune())
            .register();

    public static void register() {
        CreateAttackDefense.LOGGER.debug("Registering CAD entity types");
    }
}
