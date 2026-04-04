package com.lokins.cad.block.compute;

import com.lokins.cad.blockentity.compute.AdvancedComputeBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Advanced compute blocks powered by FE (no SU).
 * Vacuum Tube Processor (16 CU, 40 FE/t) and High-Density Compute Core (64 CU, 200 FE/t).
 */
public class AdvancedComputeBlock extends Block implements IBE<AdvancedComputeBlockEntity> {

    public enum Tier {
        VACUUM_TUBE(16, 40),
        HIGH_DENSITY(64, 200);

        public final int computeUnits;
        public final int fePerTick;

        Tier(int cu, int fe) { this.computeUnits = cu; this.fePerTick = fe; }
    }

    private final Tier tier;

    public AdvancedComputeBlock(Properties properties, Tier tier) {
        super(properties);
        this.tier = tier;
    }

    public Tier getTier() { return tier; }

    @Override public Class<AdvancedComputeBlockEntity> getBlockEntityClass() { return AdvancedComputeBlockEntity.class; }
    @Override public BlockEntityType<? extends AdvancedComputeBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.ADVANCED_COMPUTE.get(); }
}
