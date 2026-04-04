package com.lokins.cad.registry;

import com.lokins.cad.CreateAttackDefense;
import com.lokins.cad.item.BarrelItem;
import com.lokins.cad.item.BulletItem;
import com.lokins.cad.item.ChargePackItem;
import com.lokins.cad.item.CounterModuleItem;
import com.lokins.cad.item.EngineeringDisarmerItem;
import com.lokins.cad.item.FrequencyAnalyzerItem;
import com.lokins.cad.item.LightweightBootsItem;
import com.lokins.cad.item.ShellItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import static com.lokins.cad.CreateAttackDefense.REGISTRATE;

public class CADItems {

    // ---- Turret Bullets ----

    public static final ItemEntry<BulletItem> STONE_BULLET = REGISTRATE
            .item("stone_bullet", p -> new BulletItem(p, 0.5f, 0.0f))
            .register();

    public static final ItemEntry<BulletItem> IRON_BULLET = REGISTRATE
            .item("iron_bullet", p -> new BulletItem(p, 1.0f, 0.0f))
            .register();

    public static final ItemEntry<BulletItem> COPPER_BULLET = REGISTRATE
            .item("copper_bullet", p -> new BulletItem(p, 1.2f, 0.0f))
            .register();

    public static final ItemEntry<BulletItem> BRASS_BULLET = REGISTRATE
            .item("brass_bullet", p -> new BulletItem(p, 1.3f, 0.15f))
            .register();

    // ---- Cannon Charge Packs ----

    public static final ItemEntry<ChargePackItem> LIGHT_CHARGE_PACK = REGISTRATE
            .item("light_charge_pack", p -> new ChargePackItem(p, ChargePackItem.Grade.LIGHT))
            .register();

    public static final ItemEntry<ChargePackItem> STANDARD_CHARGE_PACK = REGISTRATE
            .item("standard_charge_pack", p -> new ChargePackItem(p, ChargePackItem.Grade.STANDARD))
            .register();

    public static final ItemEntry<ChargePackItem> HEAVY_CHARGE_PACK = REGISTRATE
            .item("heavy_charge_pack", p -> new ChargePackItem(p, ChargePackItem.Grade.HEAVY))
            .register();

    // ---- Cannon Shells ----

    public static final ItemEntry<ShellItem> SOLID_SHELL = REGISTRATE
            .item("solid_shell", p -> new ShellItem(p, ShellItem.ShellType.SOLID))
            .register();

    public static final ItemEntry<ShellItem> HE_SHELL = REGISTRATE
            .item("he_shell", p -> new ShellItem(p, ShellItem.ShellType.HIGH_EXPLOSIVE))
            .register();

    public static final ItemEntry<ShellItem> INCENDIARY_SHELL = REGISTRATE
            .item("incendiary_shell", p -> new ShellItem(p, ShellItem.ShellType.INCENDIARY))
            .register();

    public static final ItemEntry<ShellItem> AP_SHELL = REGISTRATE
            .item("ap_shell", p -> new ShellItem(p, ShellItem.ShellType.ARMOR_PIERCING))
            .register();

    public static final ItemEntry<ShellItem> BUCKSHOT_SHELL = REGISTRATE
            .item("buckshot_shell", p -> new ShellItem(p, ShellItem.ShellType.BUCKSHOT))
            .register();

    // ---- Cannon Barrels (3 calibers × 3 materials = 9) ----

    public static final ItemEntry<BarrelItem> SMALL_CAST_IRON_BARREL = REGISTRATE
            .item("small_cast_iron_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.SMALL, BarrelItem.Material.CAST_IRON)).register();
    public static final ItemEntry<BarrelItem> SMALL_BRASS_BARREL = REGISTRATE
            .item("small_brass_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.SMALL, BarrelItem.Material.BRASS)).register();
    public static final ItemEntry<BarrelItem> SMALL_STEEL_BARREL = REGISTRATE
            .item("small_steel_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.SMALL, BarrelItem.Material.STEEL)).register();

    public static final ItemEntry<BarrelItem> MEDIUM_CAST_IRON_BARREL = REGISTRATE
            .item("medium_cast_iron_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.MEDIUM, BarrelItem.Material.CAST_IRON)).register();
    public static final ItemEntry<BarrelItem> MEDIUM_BRASS_BARREL = REGISTRATE
            .item("medium_brass_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.MEDIUM, BarrelItem.Material.BRASS)).register();
    public static final ItemEntry<BarrelItem> MEDIUM_STEEL_BARREL = REGISTRATE
            .item("medium_steel_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.MEDIUM, BarrelItem.Material.STEEL)).register();

    public static final ItemEntry<BarrelItem> LARGE_CAST_IRON_BARREL = REGISTRATE
            .item("large_cast_iron_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.LARGE, BarrelItem.Material.CAST_IRON)).register();
    public static final ItemEntry<BarrelItem> LARGE_BRASS_BARREL = REGISTRATE
            .item("large_brass_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.LARGE, BarrelItem.Material.BRASS)).register();
    public static final ItemEntry<BarrelItem> LARGE_STEEL_BARREL = REGISTRATE
            .item("large_steel_barrel", p -> new BarrelItem(p, BarrelItem.Caliber.LARGE, BarrelItem.Material.STEEL)).register();

    // ---- Special Ammo ----

    public static final ItemEntry<BulletItem> ELECTROMAGNETIC_ROUND = REGISTRATE
            .item("electromagnetic_round", p -> new BulletItem(p, 2.0f, 0.3f))
            .register();

    // ---- Counter-Jamming Modules ----

    public static final ItemEntry<CounterModuleItem> ADAPTIVE_FILTER_MODULE = REGISTRATE
            .item("adaptive_filter_module", p -> new CounterModuleItem(p, CounterModuleItem.CounterType.ADAPTIVE_FILTER))
            .register();

    public static final ItemEntry<CounterModuleItem> MULTI_SPECTRUM_MODULE = REGISTRATE
            .item("multi_spectrum_module", p -> new CounterModuleItem(p, CounterModuleItem.CounterType.MULTI_SPECTRUM))
            .register();

    public static final ItemEntry<CounterModuleItem> FREQUENCY_HOPPING_MODULE = REGISTRATE
            .item("frequency_hopping_module", p -> new CounterModuleItem(p, CounterModuleItem.CounterType.FREQUENCY_HOPPING))
            .register();

    // ---- Consumable Components ----

    public static final ItemEntry<net.minecraft.world.item.Item> FOCUSING_LENS = REGISTRATE
            .item("focusing_lens", net.minecraft.world.item.Item::new)
            .properties(p -> p.stacksTo(16))
            .register();

    // ---- Tools & Equipment ----

    public static final ItemEntry<FrequencyAnalyzerItem> FREQUENCY_ANALYZER = REGISTRATE
            .item("frequency_analyzer", FrequencyAnalyzerItem::new)
            .register();

    public static final ItemEntry<LightweightBootsItem> LIGHTWEIGHT_BOOTS = REGISTRATE
            .item("lightweight_boots", LightweightBootsItem::new)
            .register();

    public static final ItemEntry<EngineeringDisarmerItem> ENGINEERING_DISARMER = REGISTRATE
            .item("engineering_disarmer", EngineeringDisarmerItem::new)
            .register();

    public static void register() {
        CreateAttackDefense.LOGGER.debug("Registering CAD items");
    }
}
