package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.UIPanel;
import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface UserInterfaceFactory {
    UserInterface createChestInterface(Player player, UserInterfaceBuilder builder, String title, int rows);
    UserInterface createChestInterface(Player player, UserInterfaceBuilder builder, String title, int rows, ItemStack background);
    UIPanel createPlayerPanel(UserInterface ui, Player player, boolean hotbarPosSwitched, boolean armor, boolean offhand, boolean filterItems);
    UIPanel createPlayerPanel(UserInterface ui, boolean hotbarPosSwitched, boolean armor, boolean offhand, boolean filterItems);
}
