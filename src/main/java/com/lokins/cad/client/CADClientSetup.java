package com.lokins.cad.client;

import com.lokins.cad.gui.CommandCenterMenu;
import com.lokins.cad.gui.CommandCenterScreen;
import com.lokins.cad.registry.CADMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.lokins.cad.CreateAttackDefense.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CADClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(CADMenuTypes.COMMAND_CENTER.get(), CommandCenterScreen::new);
    }
}
