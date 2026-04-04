package com.lokins.cad.network.compute;

/**
 * A device that provides compute units (CU) to the network.
 * Implementations: Mechanical Compute Unit (4 CU), Vacuum Tube Processor (16 CU),
 * High-Density Compute Core (64 CU).
 */
public interface IComputeProvider extends IComputeNode {

    /**
     * @return maximum CU this provider can supply when fully operational
     */
    int getMaxComputeUnits();

    /**
     * @return CU currently available (may be reduced by overheating, damage, etc.)
     */
    int getAvailableComputeUnits();

    /**
     * @return true if this provider is currently operational (has power, not overheated)
     */
    boolean isProviderActive();
}
