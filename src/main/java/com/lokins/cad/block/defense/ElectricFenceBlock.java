package com.lokins.cad.block.defense;

import com.lokins.cad.blockentity.defense.ElectricFenceBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Electric Fence - electrified wire that damages and slows entities on contact.
 * 15 FE/t per section. Can be climbed but causes continuous damage.
 */
public class ElectricFenceBlock extends Block implements IBE<ElectricFenceBlockEntity> {

    public ElectricFenceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide() && entity instanceof LivingEntity living) {
            // Check if fence is powered
            if (level.getBlockEntity(pos) instanceof ElectricFenceBlockEntity fence && fence.isPowered()) {
                living.hurt(level.damageSources().lightningBolt(), 2.0f);
                // Slow effect
                living.setDeltaMovement(living.getDeltaMovement().multiply(0.3, 1.0, 0.3));
            }
        }
    }

    @Override public Class<ElectricFenceBlockEntity> getBlockEntityClass() { return ElectricFenceBlockEntity.class; }
    @Override public BlockEntityType<? extends ElectricFenceBlockEntity> getBlockEntityType() { return CADBlockEntityTypes.ELECTRIC_FENCE.get(); }
}
