package org.oilmod.api.userinterface;

import org.oilmod.api.userinterface.internal.NMSClickData;
import org.oilmod.api.userinterface.internal.UIHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 18.06.2016 for OilMod-Api.
 */
public abstract class InteractableUIElementBase extends UIElement implements IInteractableUIElement {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItemstackAllowed(int index, ItemStack itemStack) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerAllowed(int index, Player player) {
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
    public int getMaxStackSize(int index, ItemStack itemStack) {
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
    public void onClick(Player player, int index, Click click, NMSClickData data) {
        UIHelper.getInstance().handleInventoryClick(this, player, index, click, data);
    }
}
