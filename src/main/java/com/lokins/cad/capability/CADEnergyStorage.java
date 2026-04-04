package com.lokins.cad.capability;

import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * FE energy storage for CAD devices.
 * Wraps NeoForge's EnergyStorage with consumption tracking.
 */
public class CADEnergyStorage extends EnergyStorage {

    private final int consumptionPerTick;

    /**
     * @param capacity          max FE stored
     * @param maxTransfer       max FE input/output per tick
     * @param consumptionPerTick FE consumed per tick when active (from design doc)
     */
    public CADEnergyStorage(int capacity, int maxTransfer, int consumptionPerTick) {
        super(capacity, maxTransfer, maxTransfer);
        this.consumptionPerTick = consumptionPerTick;
    }

    /**
     * Try to consume FE for one tick of operation.
     * @return true if enough energy was consumed
     */
    public boolean consumeTick() {
        if (consumptionPerTick <= 0) return true;
        if (energy >= consumptionPerTick) {
            energy -= consumptionPerTick;
            return true;
        }
        return false;
    }

    /**
     * Try to consume a specific amount of FE (for burst costs like sky weapons).
     * @return true if enough energy was consumed
     */
    public boolean consumeBurst(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }

    public int getConsumptionPerTick() {
        return consumptionPerTick;
    }

    public boolean hasEnoughEnergy() {
        return energy >= consumptionPerTick;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(energy, capacity));
    }
}
