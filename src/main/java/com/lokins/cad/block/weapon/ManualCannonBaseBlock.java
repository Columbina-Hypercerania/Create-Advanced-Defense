package com.lokins.cad.block.weapon;

import com.lokins.cad.blockentity.weapon.ManualCannonBaseBlockEntity;
import com.lokins.cad.item.BarrelItem;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Manual cannon base - player-operated fire cannon.
 * No SU/FE required. Player installs barrel, loads ammo, aims, and fires manually.
 */
public class ManualCannonBaseBlock extends Block implements IBE<ManualCannonBaseBlockEntity> {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ManualCannonBaseBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                               BlockPos pos, Player player, InteractionHand hand,
                                               BlockHitResult hit) {
        if (level.isClientSide()) return ItemInteractionResult.SUCCESS;

        return onBlockEntityUseItemOn(level, pos, be -> {
            // Install barrel
            if (stack.getItem() instanceof BarrelItem && !be.hasBarrel()) {
                be.installBarrel(stack.copy());
                stack.shrink(1);
                return ItemInteractionResult.SUCCESS;
            }
            // Load ammo (charge pack or shell)
            if (be.hasBarrel() && be.tryLoadAmmo(stack)) {
                stack.shrink(1);
                return ItemInteractionResult.SUCCESS;
            }
            // Fire (empty hand sneak)
            if (stack.isEmpty() && player.isShiftKeyDown() && be.canFire()) {
                be.fire(player);
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        });
    }

    @Override
    public Class<ManualCannonBaseBlockEntity> getBlockEntityClass() {
        return ManualCannonBaseBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ManualCannonBaseBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.MANUAL_CANNON_BASE.get();
    }
}
