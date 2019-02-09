package org.oilmod.api.userinterface;

import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.userinterface.internal.NMSClickData;
import org.oilmod.api.userinterface.internal.UIHelper;

/**
 * Created by sirati97 on 18.06.2016 for OilMod-Api.
 */
public abstract class InteractableUIElementBase extends UIElement implements IInteractableUIElement {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItemstackAllowed(int index, ItemStackRep itemStack) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerAllowed(int index, EntityHumanRep player) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReadOnly(int index) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxStackSize(int index, ItemStackRep itemStack) {
        return getMaxStackSize(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxStackSize(int index) {
        return 64;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(EntityHumanRep player, int index, Click click, NMSClickData data) {
        UIHelper.getInstance().handleInventoryClick(this, player, index, click, data);
    }
}
