package com.lokins.cad.client;

import com.lokins.cad.client.renderer.LaserBeamRenderer;
import com.lokins.cad.client.renderer.ShieldRenderer;
import com.lokins.cad.registry.CADBlockEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.lokins.cad.CreateAttackDefense.MOD_ID;

/**
 * Registers custom block entity renderers on the client.
 */
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CADRendererRegistry {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CADBlockEntityTypes.LASER_WEAPON.get(), LaserBeamRenderer::new);
        event.registerBlockEntityRenderer(CADBlockEntityTypes.SHIELD_GENERATOR.get(), ShieldRenderer::new);
    }
}
