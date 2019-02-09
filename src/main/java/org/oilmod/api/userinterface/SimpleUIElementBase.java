package org.oilmod.api.userinterface;


import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class SimpleUIElementBase extends UIElement {
    private ItemStackRep displayed;

    /**
     * {@inheritDoc}
     */
    @Override
    public final ItemStackRep getDisplayed(int index) {
        return getDisplayed();
    }

    /**
     *
     * @return stored itemstack
     */
    public ItemStackRep getDisplayed() {
        return displayed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setDisplayed(int index, ItemStackRep itemStack) {
        setDisplayed(itemStack);
    }

    /**
     * sets stored itemstack
     */
    public void setDisplayed(ItemStackRep itemStack) {
        this.displayed = itemStack;
    }
}
