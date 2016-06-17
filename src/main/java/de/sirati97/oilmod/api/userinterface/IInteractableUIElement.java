package de.sirati97.oilmod.api.userinterface;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 16.06.2016 for OilMod-Api.
 */
public interface IInteractableUIElement extends IUIElement {
    boolean isItemstackAllowed(int index, ItemStack itemStack);
    boolean isReadOnly(int index);
}
