package de.sirati97.oilmod.api.userinterface;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class SimpleUIElementBase extends UIElement {
    private ItemStack displayed;

    @Override
    public final ItemStack getDisplayed(int index) {
        return getDisplayed();
    }
    public ItemStack getDisplayed() {
        return displayed;
    }

    @Override
    public final void setDisplayed(int index, ItemStack itemStack) {
        setDisplayed(itemStack);
    }

    public void setDisplayed(ItemStack itemStack) {
        this.displayed = itemStack;
    }
}
