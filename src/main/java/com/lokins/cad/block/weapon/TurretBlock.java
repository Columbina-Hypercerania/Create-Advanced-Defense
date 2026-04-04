package com.lokins.cad.block.weapon;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.weapon.TurretBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Configurable turret block for P10 weapons.
 * Gatling Gun, Revolver Turret, Steam Cannon, MLRS all share this block type.
 */
public class TurretBlock extends CADKineticBlock<TurretBlockEntity> {

    public enum TurretType {
        GATLING_GUN(128, 32, 6.0f, 24, 5, "gatling_gun"),
        REVOLVER_TURRET(64, 16, 5.0f, 20, 8, "revolver_turret"),
        STEAM_CANNON(256, 32, 12.0f, 48, 60, "steam_cannon"),
        MLRS(64, 16, 8.0f, 32, 40, "mlrs");

        public final int suConsumption;
        public final float minSpeed;
        public final float baseDamage;
        public final int range;
        public final int baseCooldown; // ticks
        public final String id;

        TurretType(int su, float minSpeed, float baseDamage, int range, int baseCooldown, String id) {
            this.suConsumption = su;
            this.minSpeed = minSpeed;
            this.baseDamage = baseDamage;
            this.range = range;
            this.baseCooldown = baseCooldown;
            this.id = id;
        }
    }

    private final TurretType turretType;

    public TurretBlock(Properties properties, TurretType turretType) {
        super(properties, turretType.minSpeed);
        this.turretType = turretType;
    }

    public TurretType getTurretType() { return turretType; }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<TurretBlockEntity> getBlockEntityClass() {
        return TurretBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TurretBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.TURRET.get();
    }
}
