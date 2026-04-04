package com.lokins.cad.block.jammer;

import com.lokins.cad.blockentity.jammer.JammerBlockEntity;
import com.lokins.cad.registry.CADBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Jammer block - disrupts specific sensor types within range.
 * Three variants: Acoustic, Optical, Electronic.
 */
public class JammerBlock extends Block implements IBE<JammerBlockEntity> {

    public enum JammerType {
        ACOUSTIC("acoustic", 50, 30),    // counters: vibration monitor, sonar
        OPTICAL("optical", 50, 20),      // counters: infrared sensor, mechanical watchtower
        ELECTRONIC("electronic", 50, 60); // counters: pulse radar, phased array radar

        public final String id;
        public final int range;
        public final int fePerTick;

        JammerType(String id, int range, int fePerTick) {
            this.id = id;
            this.range = range;
            this.fePerTick = fePerTick;
        }
    }

    private final JammerType jammerType;

    public JammerBlock(Properties properties, JammerType jammerType) {
        super(properties);
        this.jammerType = jammerType;
    }

    public JammerType getJammerType() { return jammerType; }

    @Override
    public Class<JammerBlockEntity> getBlockEntityClass() {
        return JammerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends JammerBlockEntity> getBlockEntityType() {
        return CADBlockEntityTypes.JAMMER.get();
    }
}
