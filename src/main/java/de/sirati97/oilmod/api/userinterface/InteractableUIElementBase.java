package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSClickData;
import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 18.06.2016 for OilMod-Api.
 */
public abstract class InteractableUIElementBase extends UIElement implements IInteractableUIElement {
    @Override
    public boolean isItemstackAllowed(int index, ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean isPlayerAllowed(int index, Player player) {
        return true;
    }

    @Override
    public boolean isReadOnly(int index) {
        return false;
    }

    @Override
    public int getMaxStackSize(int index, ItemStack itemStack) {
        return getMaxStackSize(index);
    }

    @Override
    public int getMaxStackSize(int index) {
        return 64;
    }

    @Override
    public void onClick(Player player, int index, Click click, NMSClickData data) {
        UIHelperBase.getInstance().handleInventoryClick(this, player, index, click, data);
    }
}
