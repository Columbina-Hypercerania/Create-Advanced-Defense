package com.lokins.cad.registry;

import com.lokins.cad.CreateAttackDefense;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CADCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateAttackDefense.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + CreateAttackDefense.MOD_ID + ".main"))
                    .icon(() -> new ItemStack(Items.IRON_BLOCK)) // placeholder icon
                    .build()
    );

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}
