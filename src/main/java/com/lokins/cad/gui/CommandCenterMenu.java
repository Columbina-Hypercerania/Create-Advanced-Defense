package com.lokins.cad.gui;

import com.lokins.cad.blockentity.network.CommandCenterBlockEntity;
import com.lokins.cad.registry.CADMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Command Center menu - syncs network data to the client.
 */
public class CommandCenterMenu extends AbstractContainerMenu {

    private final CommandCenterBlockEntity blockEntity;
    private final ContainerData data;

    // Server constructor
    public CommandCenterMenu(int containerId, Inventory playerInv, CommandCenterBlockEntity be) {
        super(CADMenuTypes.COMMAND_CENTER.get(), containerId);
        this.blockEntity = be;
        this.data = new SimpleContainerData(4); // alertLevel, totalCU, usedCU, totalFE
        addDataSlots(data);
    }

    // Client constructor
    public CommandCenterMenu(int containerId, Inventory playerInv, FriendlyByteBuf buf) {
        super(CADMenuTypes.COMMAND_CENTER.get(), containerId);
        BlockEntity be = playerInv.player.level().getBlockEntity(buf.readBlockPos());
        this.blockEntity = be instanceof CommandCenterBlockEntity cc ? cc : null;
        this.data = new SimpleContainerData(4);
        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity != null &&
                player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5,
                        blockEntity.getBlockPos().getY() + 0.5,
                        blockEntity.getBlockPos().getZ() + 0.5) <= 64.0;
    }

    public int getAlertLevel() { return data.get(0); }
    public int getTotalCU() { return data.get(1); }
    public int getUsedCU() { return data.get(2); }
    public int getTotalFE() { return data.get(3); }

    public CommandCenterBlockEntity getBlockEntity() { return blockEntity; }
}
