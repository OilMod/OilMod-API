package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSClickData;
import de.sirati97.oilmod.api.userinterface.internal.NMSUIElement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 16.06.2016 for OilMod-Api.
 */
public interface IUIElement {
    ItemStack getDisplayed(int index);
    void setDisplayed(int index, ItemStack itemStack);
    void onClick(Player player, int index, Click click, NMSClickData data);
    void setNmsWrapper(NMSUIElement nmsWrapper);
    NMSUIElement getNmsWrapper();
}
