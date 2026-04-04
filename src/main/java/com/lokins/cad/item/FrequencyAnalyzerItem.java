package com.lokins.cad.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

/**
 * Frequency Analyzer - hand-held tool to detect satellite communication frequencies.
 * Takes ~60 seconds (300s with encryption) to analyze. Consumes 8 CU + 40 FE/t during analysis.
 * Result: satellite frequency ID that can be input to satellite link jammer.
 */
public class FrequencyAnalyzerItem extends Item {

    public FrequencyAnalyzerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    // TODO: Right-click use action to begin frequency scanning
    // TODO: Progress bar / scanning animation
    // TODO: Store scanned frequency in NBT
}
