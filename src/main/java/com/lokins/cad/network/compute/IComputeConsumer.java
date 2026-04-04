package com.lokins.cad.network.compute;

/**
 * A device that consumes compute units (CU) from the network.
 * Implementations: sensors, targeting modules, command center functions, etc.
 */
public interface IComputeConsumer extends IComputeNode {

    /**
     * @return CU required by this consumer for full operation
     */
    int getRequiredComputeUnits();

    /**
     * @return CU actually allocated to this consumer by the network
     * (may be less than required if network is overloaded)
     */
    int getAllocatedComputeUnits();

    /**
     * Called by the network manager to update the allocated CU.
     * Consumers should adjust their behavior based on allocation ratio.
     */
    void setAllocatedComputeUnits(int allocated);

    /**
     * @return ratio of allocated/required CU (0.0 to 1.0), used for degradation
     */
    default float getComputeRatio() {
        int required = getRequiredComputeUnits();
        if (required <= 0) return 1.0f;
        return Math.min(1.0f, (float) getAllocatedComputeUnits() / required);
    }
}
