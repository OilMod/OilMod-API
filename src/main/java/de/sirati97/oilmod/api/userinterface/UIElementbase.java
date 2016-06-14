package de.sirati97.oilmod.api.userinterface;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIElementBase extends UIElement {
    private ItemStack displayed;

    @Override
    public ItemStack getDisplayed() {
        return displayed;
    }

    @Override
    public void setDisplayed(ItemStack itemStack) {
        this.displayed = itemStack;
    }
}
