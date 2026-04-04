package com.lokins.cad.block.base;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Base class for all CAD blocks that consume Create rotational power (SU).
 * Implements IBE for BlockEntity association.
 * Subclasses must implement getRotationAxis(), getBlockEntityClass(), getBlockEntityType().
 */
public abstract class CADKineticBlock<T extends BlockEntity> extends KineticBlock implements IBE<T> {

    protected final float requiredSpeed;

    /**
     * @param properties    block properties
     * @param requiredSpeed minimum RPM needed for the device to function
     */
    public CADKineticBlock(Properties properties, float requiredSpeed) {
        super(properties);
        this.requiredSpeed = requiredSpeed;
    }

    public float getRequiredSpeed() {
        return requiredSpeed;
    }
}
