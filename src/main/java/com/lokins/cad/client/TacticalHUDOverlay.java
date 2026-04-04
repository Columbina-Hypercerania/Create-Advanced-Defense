package com.lokins.cad.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import static com.lokins.cad.CreateAttackDefense.MOD_ID;

/**
 * Tactical HUD overlay when player carries a portable terminal.
 * Displays: nearby device status, target markers, alert level.
 */
@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class TacticalHUDOverlay {

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // TODO: Check if player has portable terminal in inventory
        // TODO: Query nearby CAD network for status data
        // TODO: Render HUD elements:
        //   - Top-left: alert level indicator
        //   - Top-right: CU/FE status bars
        //   - Compass-style target markers on screen edges
        //   - Device status icons near crosshair when looking at CAD devices
    }
}
