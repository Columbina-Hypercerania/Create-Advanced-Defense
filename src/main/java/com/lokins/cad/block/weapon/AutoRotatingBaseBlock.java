package com.lokins.cad.block.weapon;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.weapon.AutoRotatingBaseBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Auto-rotating cannon base. Provides auto-aim when paired with a targeting module.
 * Available in small/medium/large sizes for different caliber barrels.
 */
public class AutoRotatingBaseBlock extends CADKineticBlock<AutoRotatingBaseBlockEntity> {

    public enum Size {
        SMALL(32, 5),
        MEDIUM(64, 10),
        LARGE(128, 15);

        public final int suConsumption;
        public final int fePerTick;

        Size(int su, int fe) {
            this.suConsumption = su;
            this.fePerTick = fe;
        }
    }

    private final Size size;

    public AutoRotatingBaseBlock(Properties properties, Size size) {
        super(properties, 16f);
        this.size = size;
    }

    public Size getSize() { return size; }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<AutoRotatingBaseBlockEntity> getBlockEntityClass() {
        return AutoRotatingBaseBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AutoRotatingBaseBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.AUTO_ROTATING_BASE.get();
    }
}
