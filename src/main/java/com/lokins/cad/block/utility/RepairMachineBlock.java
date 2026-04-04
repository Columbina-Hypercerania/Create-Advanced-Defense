package com.lokins.cad.block.utility;

import com.lokins.cad.block.base.CADKineticBlock;
import com.lokins.cad.blockentity.utility.RepairMachineBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Repair Machine - repairs damaged CAD devices.
 * 32 SU + 50 FE/t during repair. Consumes materials + time.
 */
public class RepairMachineBlock extends CADKineticBlock<RepairMachineBlockEntity> {

    public RepairMachineBlock(Properties properties) {
        super(properties, 8f);
    }

    @Override public Direction.Axis getRotationAxis(BlockState state) { return Direction.Axis.Y; }
    @Override public boolean hasShaftTowards(LevelReader w, BlockPos p, BlockState s, Direction f) { return f == Direction.DOWN; }
    @Override public Class<RepairMachineBlockEntity> getBlockEntityClass() { return RepairMachineBlockEntity.class; }
    @Override public BlockEntityType<? extends RepairMachineBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.REPAIR_MACHINE.get(); }
}
