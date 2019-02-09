package org.oilmod.api.userinterface;


import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 16.06.2016 for OilMod-Api.
 */
public interface IInteractableUIElement extends IUIElement {
    /**
     *
     * @param index the <b>local</b> index of the element
     * @param itemStack the itemstack in question
     * @return
     */
    boolean isItemstackAllowed(int index, ItemStackRep itemStack);

    /**
     *
     * @param index the <b>local</b> index of the element
     * @param player the player in question
     * @return
     */
    boolean isPlayerAllowed(int index, EntityHumanRep player);

    /**
     *
     * @param index the <b>local</b> index of the element
     * @return whether the slot is read-only. that means that you can only take an itemstack but not set one. Currently has no effect at all
     */
    boolean isReadOnly(int index);

    /**
     *
     * @param index the <b>local</b> index of the element
     * @return the maximal stack size for the itemstack
     */
    int getMaxStackSize(int index, ItemStackRep itemStack);

    /**
     *
     * @param index the <b>local</b> index of the element
     * @return the general maximal stack size
     */
    int getMaxStackSize(int index);
    /**
     * {@inheritDoc}
     */
}
