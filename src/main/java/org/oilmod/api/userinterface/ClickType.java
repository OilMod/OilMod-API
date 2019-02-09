package org.oilmod.api.userinterface;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public enum ClickType {
    /**
     * Right or Left click
     */
    PICKUP,
    /**
     * Shift click
     */
    QUICK_MOVE,
    /**
     * Number 1-9 pressed
     */
    SWAP,
    /**
     * Middle click
     */
    CLONE,
    /**
     * Q or STRG+Q
     */
    THROW,
    /**
     * idk, if you know tell me
     */
    QUICK_CRAFT,
    /**
     * EntityHumanRep double clicked. (Warning: Normal click is processed as well)
     */
    PICKUP_ALL;
}
