package com.lokins.cad;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateAttackDefense.MOD_ID)
public class CreateAttackDefense {
    public static final String MOD_ID = "cad";
    public static final String MOD_NAME = "Create Attack & Defense";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public CreateAttackDefense(IEventBus modEventBus) {
        LOGGER.info("Initializing Create Attack & Defense");
        
        // Register mod content here
        // Example: ModBlocks.register(modEventBus);
        // Example: ModItems.register(modEventBus);
    }
}
