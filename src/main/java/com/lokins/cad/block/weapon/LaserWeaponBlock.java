package com.lokins.cad.block.weapon;

import com.lokins.cad.blockentity.weapon.LaserWeaponBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Laser Energy Weapon - sustained beam weapon.
 * 0 SU, 300 FE/t firing / 10 FE/t standby. Anti-heavy-armor, breaks shields.
 * Lens has durability (~1000 shots). Generates most heat of any weapon.
 */
public class LaserWeaponBlock extends Block implements IBE<LaserWeaponBlockEntity> {

    public LaserWeaponBlock(Properties properties) {
        super(properties);
    }

    @Override public Class<LaserWeaponBlockEntity> getBlockEntityClass() { return LaserWeaponBlockEntity.class; }
    @Override public BlockEntityType<? extends LaserWeaponBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.LASER_WEAPON.get(); }
}
