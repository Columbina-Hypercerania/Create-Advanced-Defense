package com.lokins.cad.registry;

import com.lokins.cad.CreateAttackDefense;
import com.lokins.cad.block.compute.AdvancedComputeBlock;
import com.lokins.cad.block.compute.MechanicalComputeUnitBlock;
import com.lokins.cad.block.defense.ElectricFenceBlock;
import com.lokins.cad.block.defense.MechanicalGateBlock;
import com.lokins.cad.block.defense.ShieldGeneratorBlock;
import com.lokins.cad.block.jammer.JammerBlock;
import com.lokins.cad.block.utility.HeatSinkBlock;
import com.lokins.cad.block.utility.RepairMachineBlock;
import com.lokins.cad.block.utility.WaterCoolingPipeBlock;
import com.lokins.cad.block.network.*;
import com.lokins.cad.block.satellite.SatelliteLaunchPadBlock;
import com.lokins.cad.block.satellite.SatelliteLinkJammerBlock;
import com.lokins.cad.block.power.BatteryBlock;
import com.lokins.cad.block.power.DynamoBlock;
import com.lokins.cad.block.sensor.InfraredSensorBlock;
import com.lokins.cad.block.sensor.MechanicalWatchtowerBlock;
import com.lokins.cad.block.sensor.PhasedArrayRadarBlock;
import com.lokins.cad.block.sensor.PressureSensorBlock;
import com.lokins.cad.block.sensor.PulseRadarBlock;
import com.lokins.cad.block.sensor.SonarArrayBlock;
import com.lokins.cad.block.sensor.VibrationMonitorBlock;
import com.lokins.cad.block.weapon.AutoRotatingBaseBlock;
import com.lokins.cad.block.weapon.AutoSentryBlock;
import com.lokins.cad.block.weapon.ElectromagneticWeaponBlock;
import com.lokins.cad.block.weapon.LaserWeaponBlock;
import com.lokins.cad.block.weapon.ManualCannonBaseBlock;
import com.lokins.cad.block.weapon.TurretBlock;
import com.tterrag.registrate.util.entry.BlockEntry;

import static com.lokins.cad.CreateAttackDefense.REGISTRATE;

public class CADBlocks {

    // ---- Weapons: Auto Turrets ----
    public static final BlockEntry<AutoSentryBlock> AUTO_SENTRY = REGISTRATE
            .block("auto_sentry", AutoSentryBlock::new)
            .properties(p -> p.strength(3.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<TurretBlock> GATLING_GUN = REGISTRATE
            .block("gatling_gun", p -> new TurretBlock(p, TurretBlock.TurretType.GATLING_GUN))
            .properties(p -> p.strength(4.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<TurretBlock> REVOLVER_TURRET = REGISTRATE
            .block("revolver_turret", p -> new TurretBlock(p, TurretBlock.TurretType.REVOLVER_TURRET))
            .properties(p -> p.strength(3.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<TurretBlock> STEAM_CANNON = REGISTRATE
            .block("steam_cannon", p -> new TurretBlock(p, TurretBlock.TurretType.STEAM_CANNON))
            .properties(p -> p.strength(4.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<TurretBlock> MLRS = REGISTRATE
            .block("mlrs", p -> new TurretBlock(p, TurretBlock.TurretType.MLRS))
            .properties(p -> p.strength(4.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Weapons: Cannon System ----
    public static final BlockEntry<ManualCannonBaseBlock> MANUAL_CANNON_BASE = REGISTRATE
            .block("manual_cannon_base", ManualCannonBaseBlock::new)
            .properties(p -> p.strength(3.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<AutoRotatingBaseBlock> AUTO_ROTATING_BASE_SMALL = REGISTRATE
            .block("auto_rotating_base_small", p -> new AutoRotatingBaseBlock(p, AutoRotatingBaseBlock.Size.SMALL))
            .properties(p -> p.strength(3.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<AutoRotatingBaseBlock> AUTO_ROTATING_BASE_MEDIUM = REGISTRATE
            .block("auto_rotating_base_medium", p -> new AutoRotatingBaseBlock(p, AutoRotatingBaseBlock.Size.MEDIUM))
            .properties(p -> p.strength(4.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<AutoRotatingBaseBlock> AUTO_ROTATING_BASE_LARGE = REGISTRATE
            .block("auto_rotating_base_large", p -> new AutoRotatingBaseBlock(p, AutoRotatingBaseBlock.Size.LARGE))
            .properties(p -> p.strength(4.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Sensors ----
    public static final BlockEntry<InfraredSensorBlock> INFRARED_SENSOR = REGISTRATE
            .block("infrared_sensor", InfraredSensorBlock::new)
            .properties(p -> p.strength(2.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<VibrationMonitorBlock> VIBRATION_MONITOR = REGISTRATE
            .block("vibration_monitor", VibrationMonitorBlock::new)
            .properties(p -> p.strength(2.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<PressureSensorBlock> PRESSURE_SENSOR = REGISTRATE
            .block("pressure_sensor", PressureSensorBlock::new)
            .properties(p -> p.strength(1.5f))
            .simpleItem().register();

    // ---- Sensors: Tier 2 ----
    public static final BlockEntry<PulseRadarBlock> PULSE_RADAR = REGISTRATE
            .block("pulse_radar", PulseRadarBlock::new)
            .properties(p -> p.strength(3.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<SonarArrayBlock> SONAR_ARRAY = REGISTRATE
            .block("sonar_array", SonarArrayBlock::new)
            .properties(p -> p.strength(3.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<MechanicalWatchtowerBlock> MECHANICAL_WATCHTOWER = REGISTRATE
            .block("mechanical_watchtower", MechanicalWatchtowerBlock::new)
            .properties(p -> p.strength(3.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Sensors: Tier 3 ----
    public static final BlockEntry<PhasedArrayRadarBlock> PHASED_ARRAY_RADAR = REGISTRATE
            .block("phased_array_radar", PhasedArrayRadarBlock::new)
            .properties(p -> p.strength(4.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Compute ----
    public static final BlockEntry<MechanicalComputeUnitBlock> MECHANICAL_COMPUTE_UNIT = REGISTRATE
            .block("mechanical_compute_unit", MechanicalComputeUnitBlock::new)
            .properties(p -> p.strength(3.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<AdvancedComputeBlock> VACUUM_TUBE_PROCESSOR = REGISTRATE
            .block("vacuum_tube_processor", p -> new AdvancedComputeBlock(p, AdvancedComputeBlock.Tier.VACUUM_TUBE))
            .properties(p -> p.strength(3.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<AdvancedComputeBlock> HIGH_DENSITY_COMPUTE_CORE = REGISTRATE
            .block("high_density_compute_core", p -> new AdvancedComputeBlock(p, AdvancedComputeBlock.Tier.HIGH_DENSITY))
            .properties(p -> p.strength(4.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Power ----
    public static final BlockEntry<DynamoBlock> DYNAMO = REGISTRATE
            .block("dynamo", DynamoBlock::new)
            .properties(p -> p.strength(3.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<BatteryBlock> BATTERY_SMALL = REGISTRATE
            .block("battery_small", p -> new BatteryBlock(p, BatteryBlock.Tier.SMALL))
            .properties(p -> p.strength(2.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<BatteryBlock> BATTERY_MEDIUM = REGISTRATE
            .block("battery_medium", p -> new BatteryBlock(p, BatteryBlock.Tier.MEDIUM))
            .properties(p -> p.strength(3.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<BatteryBlock> BATTERY_LARGE = REGISTRATE
            .block("battery_large", p -> new BatteryBlock(p, BatteryBlock.Tier.LARGE))
            .properties(p -> p.strength(3.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Advanced Weapons ----
    public static final BlockEntry<ElectromagneticWeaponBlock> ELECTROMAGNETIC_WEAPON = REGISTRATE
            .block("electromagnetic_weapon", ElectromagneticWeaponBlock::new)
            .properties(p -> p.strength(5.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<LaserWeaponBlock> LASER_WEAPON = REGISTRATE
            .block("laser_weapon", LaserWeaponBlock::new)
            .properties(p -> p.strength(5.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Defense ----
    public static final BlockEntry<ShieldGeneratorBlock> SHIELD_GENERATOR = REGISTRATE
            .block("shield_generator", ShieldGeneratorBlock::new)
            .properties(p -> p.strength(5.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<MechanicalGateBlock> MECHANICAL_GATE = REGISTRATE
            .block("mechanical_gate", MechanicalGateBlock::new)
            .properties(p -> p.strength(6.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<ElectricFenceBlock> ELECTRIC_FENCE = REGISTRATE
            .block("electric_fence", ElectricFenceBlock::new)
            .properties(p -> p.strength(2.0f).noOcclusion())
            .simpleItem().register();

    // ---- Network ----
    public static final BlockEntry<DataCableBlock> DATA_CABLE = REGISTRATE
            .block("data_cable", DataCableBlock::new)
            .properties(p -> p.strength(1.0f))
            .simpleItem().register();

    public static final BlockEntry<DataRouterBlock> DATA_ROUTER = REGISTRATE
            .block("data_router", DataRouterBlock::new)
            .properties(p -> p.strength(2.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<SignalAdapterBlock> SIGNAL_ADAPTER = REGISTRATE
            .block("signal_adapter", SignalAdapterBlock::new)
            .properties(p -> p.strength(2.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<SignalOutputAdapterBlock> SIGNAL_OUTPUT_ADAPTER = REGISTRATE
            .block("signal_output_adapter", SignalOutputAdapterBlock::new)
            .properties(p -> p.strength(2.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<CommandCenterBlock> COMMAND_CENTER = REGISTRATE
            .block("command_center", CommandCenterBlock::new)
            .properties(p -> p.strength(5.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Jammers ----
    public static final BlockEntry<JammerBlock> ACOUSTIC_JAMMER = REGISTRATE
            .block("acoustic_jammer", p -> new JammerBlock(p, JammerBlock.JammerType.ACOUSTIC))
            .properties(p -> p.strength(2.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<JammerBlock> OPTICAL_JAMMER = REGISTRATE
            .block("optical_jammer", p -> new JammerBlock(p, JammerBlock.JammerType.OPTICAL))
            .properties(p -> p.strength(2.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<JammerBlock> ELECTRONIC_JAMMER = REGISTRATE
            .block("electronic_jammer", p -> new JammerBlock(p, JammerBlock.JammerType.ELECTRONIC))
            .properties(p -> p.strength(2.5f).requiresCorrectToolForDrops())
            .simpleItem().register();

    // ---- Utility ----
    public static final BlockEntry<RepairMachineBlock> REPAIR_MACHINE = REGISTRATE
            .block("repair_machine", RepairMachineBlock::new)
            .properties(p -> p.strength(3.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<HeatSinkBlock> HEAT_SINK = REGISTRATE
            .block("heat_sink", p -> new HeatSinkBlock(p, 10))
            .properties(p -> p.strength(2.0f))
            .simpleItem().register();

    public static final BlockEntry<WaterCoolingPipeBlock> WATER_COOLING_PIPE = REGISTRATE
            .block("water_cooling_pipe", WaterCoolingPipeBlock::new)
            .properties(p -> p.strength(2.0f))
            .simpleItem().register();

    // ---- Satellite ----
    public static final BlockEntry<SatelliteLaunchPadBlock> SATELLITE_LAUNCH_PAD = REGISTRATE
            .block("satellite_launch_pad", SatelliteLaunchPadBlock::new)
            .properties(p -> p.strength(6.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static final BlockEntry<SatelliteLinkJammerBlock> SATELLITE_LINK_JAMMER = REGISTRATE
            .block("satellite_link_jammer", SatelliteLinkJammerBlock::new)
            .properties(p -> p.strength(5.0f).requiresCorrectToolForDrops())
            .simpleItem().register();

    public static void register() {
        CreateAttackDefense.LOGGER.debug("Registering CAD blocks");
    }
}
