package com.lokins.cad;

import com.lokins.cad.registry.CADBlockEntityTypes;
import com.lokins.cad.registry.CADBlocks;
import com.lokins.cad.registry.CADCreativeTabs;
import com.lokins.cad.registry.CADEntityTypes;
import com.lokins.cad.registry.CADItems;
import com.lokins.cad.registry.CADMenuTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateAttackDefense.MOD_ID)
public class CreateAttackDefense {
    public static final String MOD_ID = "cad";
    public static final String MOD_NAME = "Create: Attack & Defense";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public CreateAttackDefense(IEventBus modEventBus) {
        LOGGER.info("Initializing {}", MOD_NAME);

        REGISTRATE.registerEventListeners(modEventBus);

        CADCreativeTabs.register(modEventBus);
        REGISTRATE.setCreativeTab(CADCreativeTabs.MAIN);
        CADBlocks.register();
        CADItems.register();
        CADBlockEntityTypes.register();
        CADEntityTypes.register();
        CADMenuTypes.register(modEventBus);
    }
}
