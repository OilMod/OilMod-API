package de.sirati97.oilmod.api.userinterface;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class SimpleUIElementBase extends UIElement {
    private ItemStack displayed;

    /**
     * @inheritDoc
     */
    @Override
    public final ItemStack getDisplayed(int index) {
        return getDisplayed();
    }

    /**
     *
     * @return stored itemstack
     */
    public ItemStack getDisplayed() {
        return displayed;
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void setDisplayed(int index, ItemStack itemStack) {
        setDisplayed(itemStack);
    }

    /**
     * sets stored itemstack
     */
    public void setDisplayed(ItemStack itemStack) {
        this.displayed = itemStack;
    }
}
