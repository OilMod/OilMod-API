package org.oilmod.api.items;

/**
 * Internal - should not be called by user code
 */
public class ModItemSetterHelper {
    protected ModItemSetterHelper(){}

    /**
     * Internal - should not be called by user code
     */
    protected void set(Object nmsItem, OilItem apiItem) {
        apiItem.setNmsItem(nmsItem);
    }
}
