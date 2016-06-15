package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSClickData;
import de.sirati97.oilmod.api.userinterface.internal.NMSUIElement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIElement {
    private NMSUIElement nmsWrapper;

    public abstract ItemStack getDisplayed(int index);
    public abstract void setDisplayed(int index, ItemStack itemStack);
    public abstract void onClick(Player player, int index, Click click, NMSClickData data);

    public void setNmsWrapper(NMSUIElement nmsWrapper) {
        this.nmsWrapper = nmsWrapper;
    }

    public NMSUIElement getNmsWrapper() {
        return nmsWrapper;
    }
}
