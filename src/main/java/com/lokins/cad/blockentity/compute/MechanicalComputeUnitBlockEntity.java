package com.lokins.cad.blockentity.compute;

import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import com.lokins.cad.network.compute.IComputeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mechanical Compute Unit BE - provides 4 CU when spinning.
 * Pure mechanical, no FE. 32 SU consumption.
 */
public class MechanicalComputeUnitBlockEntity extends CADKineticBlockEntity implements IComputeProvider {

    public static final int MAX_CU = 4;

    public MechanicalComputeUnitBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        // No FE needed
    }

    @Override
    protected float getRequiredSpeed() { return 8f; }

    @Override
    protected void onActiveTick() {
        // Compute unit just needs to be spinning - no special tick logic
    }

    // IComputeProvider
    @Override public BlockPos getNodePos() { return getBlockPos(); }
    @Override public boolean isConnectedToNetwork() { return true; }
    @Override public int getMaxComputeUnits() { return MAX_CU; }
    @Override public int getAvailableComputeUnits() { return isActive() ? MAX_CU : 0; }
    @Override public boolean isProviderActive() { return isActive(); }
}
