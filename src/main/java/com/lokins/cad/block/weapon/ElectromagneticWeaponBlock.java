package com.lokins.cad.block.weapon;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.weapon.ElectromagneticWeaponBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Electromagnetic Kinetic Weapon - railgun-style weapon.
 * 128 SU + 200 FE/t firing / 20 FE/t standby. Anti-light-armor.
 */
public class ElectromagneticWeaponBlock extends CADKineticBlock<ElectromagneticWeaponBlockEntity> {

    public ElectromagneticWeaponBlock(Properties properties) {
        super(properties, 32f);
    }

    @Override public Direction.Axis getRotationAxis(BlockState state) { return Direction.Axis.Y; }
    @Override public boolean hasShaftTowards(LevelReader w, BlockPos p, BlockState s, Direction f) { return f == Direction.DOWN; }
    @Override public Class<ElectromagneticWeaponBlockEntity> getBlockEntityClass() { return ElectromagneticWeaponBlockEntity.class; }
    @Override public BlockEntityType<? extends ElectromagneticWeaponBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.ELECTROMAGNETIC_WEAPON.get(); }
}
