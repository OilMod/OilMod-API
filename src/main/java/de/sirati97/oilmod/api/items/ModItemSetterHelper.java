package de.sirati97.oilmod.api.items;

/**
 * Internal - should not be called by user code
 */
public class ModItemSetterHelper {
    protected ModItemSetterHelper(){}

    /**
     * Internal - should not be called by user code
     */
    protected void set(Object nmsItem, OilItemBase apiItem) {
        apiItem.setNmsItem(nmsItem);
    }
}
