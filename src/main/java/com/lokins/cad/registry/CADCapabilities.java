package com.lokins.cad.registry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import static com.lokins.cad.CreateAttackDefense.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CADCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Auto Sentry: item handler for ammo insertion
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                CADBlockEntityTypes.AUTO_SENTRY.get(),
                (be, side) -> be.getAmmoInventory());

        // Auto Rotating Base: item handler for ammo slots
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                CADBlockEntityTypes.AUTO_ROTATING_BASE.get(),
                (be, side) -> be.getAmmoSlots());

        // Dynamo: energy output
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.DYNAMO.get(),
                (be, side) -> be.getEnergyStorage());

        // Batteries: energy storage
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.BATTERY.get(),
                (be, side) -> be.getEnergyStorage());

        // Pulse Radar: energy input
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.PULSE_RADAR.get(),
                (be, side) -> be.getEnergyStorage());

        // Sonar Array: energy input
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.SONAR_ARRAY.get(),
                (be, side) -> be.getEnergyStorage());

        // Phased Array Radar: energy input
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.PHASED_ARRAY_RADAR.get(),
                (be, side) -> be.getEnergyStorage());

        // Advanced Compute: energy input
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.ADVANCED_COMPUTE.get(),
                (be, side) -> be.getEnergyStorage());

        // Turrets: item handler for ammo
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                CADBlockEntityTypes.TURRET.get(),
                (be, side) -> be.getAmmoInventory());

        // Jammer: energy input
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.JAMMER.get(),
                (be, side) -> be.getEnergyStorage());

        // Shield Generator: energy input
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.SHIELD_GENERATOR.get(),
                (be, side) -> be.getEnergyStorage());

        // Electric Fence: energy input
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.ELECTRIC_FENCE.get(),
                (be, side) -> be.getEnergyStorage());

        // Electromagnetic Weapon
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.ELECTROMAGNETIC_WEAPON.get(),
                (be, side) -> be.getEnergyStorage());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                CADBlockEntityTypes.ELECTROMAGNETIC_WEAPON.get(),
                (be, side) -> be.getAmmoSlot());

        // Laser Weapon
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                CADBlockEntityTypes.LASER_WEAPON.get(),
                (be, side) -> be.getEnergyStorage());
    }
}
