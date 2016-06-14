package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface InterfaceFactory {
    Interface createChestInterace(Player player, UserInterfaceBuilder builder, String title, int rows);
    Interface createChestInterace(Player player, UserInterfaceBuilder builder, String title, int rows, ItemStack background);
}
