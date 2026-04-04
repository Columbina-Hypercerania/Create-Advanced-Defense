package com.lokins.cad.blockentity.utility;

import com.lokins.cad.blockentity.base.CADKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Repair Machine BE - takes damaged devices, consumes materials + FE + time to repair.
 * 32 SU + 50 FE/t during repair process.
 */
public class RepairMachineBlockEntity extends CADKineticBlockEntity {

    private final ItemStackHandler inputSlot = new ItemStackHandler(1);   // damaged device
    private final ItemStackHandler materialSlot = new ItemStackHandler(1); // repair materials
    private final ItemStackHandler outputSlot = new ItemStackHandler(1);   // repaired device

    private int repairProgress = 0;
    private int repairTime = 0; // total ticks needed
    private boolean repairing = false;

    public RepairMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        initEnergyStorage(10000, 200, 50); // 50 FE/t during repair
    }

    @Override protected float getRequiredSpeed() { return 8f; }

    @Override
    protected void onActiveTick() {
        if (level == null || level.isClientSide()) return;

        if (!repairing) {
            // Check if we can start a repair
            ItemStack input = inputSlot.getStackInSlot(0);
            ItemStack material = materialSlot.getStackInSlot(0);
            if (!input.isEmpty() && input.isDamaged() && !material.isEmpty() && outputSlot.getStackInSlot(0).isEmpty()) {
                repairing = true;
                repairTime = calculateRepairTime(input);
                repairProgress = 0;
            }
        } else {
            repairProgress++;
            if (repairProgress >= repairTime) {
                completeRepair();
            }
        }
    }

    private int calculateRepairTime(ItemStack item) {
        // Simple: 10 seconds base + 1 second per missing durability point (capped at 60s)
        int damage = item.getDamageValue();
        return Math.min(1200, 200 + damage * 20);
    }

    private void completeRepair() {
        ItemStack input = inputSlot.extractItem(0, 1, false);
        materialSlot.extractItem(0, 1, false);

        if (!input.isEmpty()) {
            input.setDamageValue(0); // Full repair
            outputSlot.insertItem(0, input, false);
        }

        repairing = false;
        repairProgress = 0;
        repairTime = 0;
        notifyUpdate();
    }

    public ItemStackHandler getInputSlot() { return inputSlot; }
    public ItemStackHandler getMaterialSlot() { return materialSlot; }
    public ItemStackHandler getOutputSlot() { return outputSlot; }
    public boolean isRepairing() { return repairing; }
    public float getRepairRatio() { return repairTime > 0 ? (float) repairProgress / repairTime : 0; }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("Input")) inputSlot.deserializeNBT(registries, tag.getCompound("Input"));
        if (tag.contains("Material")) materialSlot.deserializeNBT(registries, tag.getCompound("Material"));
        if (tag.contains("Output")) outputSlot.deserializeNBT(registries, tag.getCompound("Output"));
        repairProgress = tag.getInt("RepairProgress");
        repairTime = tag.getInt("RepairTime");
        repairing = tag.getBoolean("Repairing");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.put("Input", inputSlot.serializeNBT(registries));
        tag.put("Material", materialSlot.serializeNBT(registries));
        tag.put("Output", outputSlot.serializeNBT(registries));
        tag.putInt("RepairProgress", repairProgress);
        tag.putInt("RepairTime", repairTime);
        tag.putBoolean("Repairing", repairing);
    }
}
