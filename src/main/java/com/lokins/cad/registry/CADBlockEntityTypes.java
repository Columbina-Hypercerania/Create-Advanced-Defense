package com.lokins.cad.registry;

import com.lokins.cad.CreateAttackDefense;
import com.lokins.cad.blockentity.compute.AdvancedComputeBlockEntity;
import com.lokins.cad.blockentity.compute.MechanicalComputeUnitBlockEntity;
import com.lokins.cad.blockentity.sensor.PhasedArrayRadarBlockEntity;
import com.lokins.cad.blockentity.defense.ElectricFenceBlockEntity;
import com.lokins.cad.blockentity.defense.MechanicalGateBlockEntity;
import com.lokins.cad.blockentity.defense.ShieldGeneratorBlockEntity;
import com.lokins.cad.blockentity.jammer.JammerBlockEntity;
import com.lokins.cad.blockentity.satellite.SatelliteLaunchPadBlockEntity;
import com.lokins.cad.blockentity.satellite.SatelliteLinkJammerBlockEntity;
import com.lokins.cad.blockentity.utility.RepairMachineBlockEntity;
import com.lokins.cad.blockentity.network.CommandCenterBlockEntity;
import com.lokins.cad.blockentity.network.SignalAdapterBlockEntity;
import com.lokins.cad.blockentity.network.SignalOutputAdapterBlockEntity;
import com.lokins.cad.blockentity.power.BatteryBlockEntity;
import com.lokins.cad.blockentity.power.DynamoBlockEntity;
import com.lokins.cad.blockentity.sensor.InfraredSensorBlockEntity;
import com.lokins.cad.blockentity.sensor.MechanicalWatchtowerBlockEntity;
import com.lokins.cad.blockentity.sensor.PressureSensorBlockEntity;
import com.lokins.cad.blockentity.sensor.PulseRadarBlockEntity;
import com.lokins.cad.blockentity.sensor.SonarArrayBlockEntity;
import com.lokins.cad.blockentity.sensor.VibrationMonitorBlockEntity;
import com.lokins.cad.blockentity.weapon.AutoRotatingBaseBlockEntity;
import com.lokins.cad.blockentity.weapon.AutoSentryBlockEntity;
import com.lokins.cad.blockentity.weapon.ElectromagneticWeaponBlockEntity;
import com.lokins.cad.blockentity.weapon.LaserWeaponBlockEntity;
import com.lokins.cad.blockentity.weapon.ManualCannonBaseBlockEntity;
import com.lokins.cad.blockentity.weapon.TurretBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.lokins.cad.CreateAttackDefense.REGISTRATE;

public class CADBlockEntityTypes {

    // Weapons
    public static final BlockEntityEntry<AutoSentryBlockEntity> AUTO_SENTRY = REGISTRATE
            .blockEntity("auto_sentry", AutoSentryBlockEntity::new)
            .validBlocks(CADBlocks.AUTO_SENTRY).register();

    public static final BlockEntityEntry<ManualCannonBaseBlockEntity> MANUAL_CANNON_BASE = REGISTRATE
            .blockEntity("manual_cannon_base", ManualCannonBaseBlockEntity::new)
            .validBlocks(CADBlocks.MANUAL_CANNON_BASE).register();

    public static final BlockEntityEntry<AutoRotatingBaseBlockEntity> AUTO_ROTATING_BASE = REGISTRATE
            .blockEntity("auto_rotating_base", AutoRotatingBaseBlockEntity::new)
            .validBlocks(CADBlocks.AUTO_ROTATING_BASE_SMALL, CADBlocks.AUTO_ROTATING_BASE_MEDIUM, CADBlocks.AUTO_ROTATING_BASE_LARGE)
            .register();

    public static final BlockEntityEntry<TurretBlockEntity> TURRET = REGISTRATE
            .blockEntity("turret", TurretBlockEntity::new)
            .validBlocks(CADBlocks.GATLING_GUN, CADBlocks.REVOLVER_TURRET, CADBlocks.STEAM_CANNON, CADBlocks.MLRS)
            .register();

    // Sensors
    public static final BlockEntityEntry<InfraredSensorBlockEntity> INFRARED_SENSOR = REGISTRATE
            .blockEntity("infrared_sensor", InfraredSensorBlockEntity::new)
            .validBlocks(CADBlocks.INFRARED_SENSOR).register();

    public static final BlockEntityEntry<VibrationMonitorBlockEntity> VIBRATION_MONITOR = REGISTRATE
            .blockEntity("vibration_monitor", VibrationMonitorBlockEntity::new)
            .validBlocks(CADBlocks.VIBRATION_MONITOR).register();

    public static final BlockEntityEntry<PressureSensorBlockEntity> PRESSURE_SENSOR = REGISTRATE
            .blockEntity("pressure_sensor", PressureSensorBlockEntity::new)
            .validBlocks(CADBlocks.PRESSURE_SENSOR).register();

    // Sensors: Tier 2
    public static final BlockEntityEntry<PulseRadarBlockEntity> PULSE_RADAR = REGISTRATE
            .blockEntity("pulse_radar", PulseRadarBlockEntity::new)
            .validBlocks(CADBlocks.PULSE_RADAR).register();

    public static final BlockEntityEntry<SonarArrayBlockEntity> SONAR_ARRAY = REGISTRATE
            .blockEntity("sonar_array", SonarArrayBlockEntity::new)
            .validBlocks(CADBlocks.SONAR_ARRAY).register();

    public static final BlockEntityEntry<MechanicalWatchtowerBlockEntity> MECHANICAL_WATCHTOWER = REGISTRATE
            .blockEntity("mechanical_watchtower", MechanicalWatchtowerBlockEntity::new)
            .validBlocks(CADBlocks.MECHANICAL_WATCHTOWER).register();

    // Sensors: Tier 3
    public static final BlockEntityEntry<PhasedArrayRadarBlockEntity> PHASED_ARRAY_RADAR = REGISTRATE
            .blockEntity("phased_array_radar", PhasedArrayRadarBlockEntity::new)
            .validBlocks(CADBlocks.PHASED_ARRAY_RADAR).register();

    // Compute
    public static final BlockEntityEntry<MechanicalComputeUnitBlockEntity> MECHANICAL_COMPUTE_UNIT = REGISTRATE
            .blockEntity("mechanical_compute_unit", MechanicalComputeUnitBlockEntity::new)
            .validBlocks(CADBlocks.MECHANICAL_COMPUTE_UNIT).register();

    public static final BlockEntityEntry<AdvancedComputeBlockEntity> ADVANCED_COMPUTE = REGISTRATE
            .blockEntity("advanced_compute", AdvancedComputeBlockEntity::new)
            .validBlocks(CADBlocks.VACUUM_TUBE_PROCESSOR, CADBlocks.HIGH_DENSITY_COMPUTE_CORE).register();

    // Advanced Weapons
    public static final BlockEntityEntry<ElectromagneticWeaponBlockEntity> ELECTROMAGNETIC_WEAPON = REGISTRATE
            .blockEntity("electromagnetic_weapon", ElectromagneticWeaponBlockEntity::new)
            .validBlocks(CADBlocks.ELECTROMAGNETIC_WEAPON).register();

    public static final BlockEntityEntry<LaserWeaponBlockEntity> LASER_WEAPON = REGISTRATE
            .blockEntity("laser_weapon", LaserWeaponBlockEntity::new)
            .validBlocks(CADBlocks.LASER_WEAPON).register();

    // Power
    public static final BlockEntityEntry<DynamoBlockEntity> DYNAMO = REGISTRATE
            .blockEntity("dynamo", DynamoBlockEntity::new)
            .validBlocks(CADBlocks.DYNAMO).register();

    public static final BlockEntityEntry<BatteryBlockEntity> BATTERY = REGISTRATE
            .blockEntity("battery", BatteryBlockEntity::new)
            .validBlocks(CADBlocks.BATTERY_SMALL, CADBlocks.BATTERY_MEDIUM, CADBlocks.BATTERY_LARGE).register();

    // Defense
    public static final BlockEntityEntry<ShieldGeneratorBlockEntity> SHIELD_GENERATOR = REGISTRATE
            .blockEntity("shield_generator", ShieldGeneratorBlockEntity::new)
            .validBlocks(CADBlocks.SHIELD_GENERATOR).register();

    public static final BlockEntityEntry<MechanicalGateBlockEntity> MECHANICAL_GATE = REGISTRATE
            .blockEntity("mechanical_gate", MechanicalGateBlockEntity::new)
            .validBlocks(CADBlocks.MECHANICAL_GATE).register();

    public static final BlockEntityEntry<ElectricFenceBlockEntity> ELECTRIC_FENCE = REGISTRATE
            .blockEntity("electric_fence", ElectricFenceBlockEntity::new)
            .validBlocks(CADBlocks.ELECTRIC_FENCE).register();

    // Network
    public static final BlockEntityEntry<SignalAdapterBlockEntity> SIGNAL_ADAPTER = REGISTRATE
            .blockEntity("signal_adapter", SignalAdapterBlockEntity::new)
            .validBlocks(CADBlocks.SIGNAL_ADAPTER).register();

    public static final BlockEntityEntry<SignalOutputAdapterBlockEntity> SIGNAL_OUTPUT_ADAPTER = REGISTRATE
            .blockEntity("signal_output_adapter", SignalOutputAdapterBlockEntity::new)
            .validBlocks(CADBlocks.SIGNAL_OUTPUT_ADAPTER).register();

    public static final BlockEntityEntry<CommandCenterBlockEntity> COMMAND_CENTER = REGISTRATE
            .blockEntity("command_center", CommandCenterBlockEntity::new)
            .validBlocks(CADBlocks.COMMAND_CENTER).register();

    // Jammers
    public static final BlockEntityEntry<JammerBlockEntity> JAMMER = REGISTRATE
            .blockEntity("jammer", JammerBlockEntity::new)
            .validBlocks(CADBlocks.ACOUSTIC_JAMMER, CADBlocks.OPTICAL_JAMMER, CADBlocks.ELECTRONIC_JAMMER)
            .register();

    // Utility
    public static final BlockEntityEntry<RepairMachineBlockEntity> REPAIR_MACHINE = REGISTRATE
            .blockEntity("repair_machine", RepairMachineBlockEntity::new)
            .validBlocks(CADBlocks.REPAIR_MACHINE).register();

    // Satellite
    public static final BlockEntityEntry<SatelliteLaunchPadBlockEntity> SATELLITE_LAUNCH_PAD = REGISTRATE
            .blockEntity("satellite_launch_pad", SatelliteLaunchPadBlockEntity::new)
            .validBlocks(CADBlocks.SATELLITE_LAUNCH_PAD).register();

    public static final BlockEntityEntry<SatelliteLinkJammerBlockEntity> SATELLITE_LINK_JAMMER = REGISTRATE
            .blockEntity("satellite_link_jammer", SatelliteLinkJammerBlockEntity::new)
            .validBlocks(CADBlocks.SATELLITE_LINK_JAMMER).register();

    public static void register() {
        CreateAttackDefense.LOGGER.debug("Registering CAD block entity types");
    }
}
