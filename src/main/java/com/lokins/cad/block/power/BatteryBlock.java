package com.lokins.cad.block.power;

import com.lokins.cad.blockentity.power.BatteryBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Battery block - stores FE. Three tiers: Small, Medium, Large.
 * Supports parallel connection (multiple batteries stack throughput).
 */
public class BatteryBlock extends Block implements IBE<BatteryBlockEntity> {

    public enum Tier {
        SMALL(50_000, 200),
        MEDIUM(500_000, 1_000),
        LARGE(5_000_000, 5_000);

        public final int capacity;
        public final int maxTransfer;

        Tier(int capacity, int maxTransfer) {
            this.capacity = capacity;
            this.maxTransfer = maxTransfer;
        }
    }

    private final Tier tier;

    public BatteryBlock(Properties properties, Tier tier) {
        super(properties);
        this.tier = tier;
    }

    public Tier getTier() { return tier; }

    @Override
    public Class<BatteryBlockEntity> getBlockEntityClass() {
        return BatteryBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BatteryBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.BATTERY.get();
    }
}
