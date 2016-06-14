package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface InterfaceFactory {
    Interface createChestInterace(Player player, UserInterfaceBuilder builder, int rows);
}
