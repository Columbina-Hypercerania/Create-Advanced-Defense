package com.lokins.cad;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateAdvancedDefense.MOD_ID)
public class CreateAdvancedDefense {
    public static final String MOD_ID = "create_advanced_defense";
    public static final String MOD_NAME = "Create Advanced Defense";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public CreateAdvancedDefense(IEventBus modEventBus) {
        LOGGER.info("Initializing Create Advanced Defense");
        
        // Register mod content here
        // Example: ModBlocks.register(modEventBus);
        // Example: ModItems.register(modEventBus);
    }
}
