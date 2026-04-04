package com.lokins.cad.registry;

import com.lokins.cad.CreateAttackDefense;
import com.lokins.cad.gui.CommandCenterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CADMenuTypes {

    private static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CreateAttackDefense.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<CommandCenterMenu>> COMMAND_CENTER =
            MENUS.register("command_center",
                    () -> IMenuTypeExtension.create(CommandCenterMenu::new));

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}
