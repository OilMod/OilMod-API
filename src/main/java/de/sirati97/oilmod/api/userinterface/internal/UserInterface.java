package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.UIPanel;
import de.sirati97.oilmod.api.userinterface.UserInterfaceBuilder;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface UserInterface {
    Player getPlayer();
    void showPanel(UIPanel panel);
    void showPanel(UIPanel panelPlayer, UIPanel panelChest);
    UIPanel getPanel();
    UserInterfaceBuilder<?> getBuilder();
    void display(Player player);
    UIPanel createPlayerPanel(Player player, boolean hotbarPosSwitched, boolean armor, boolean offhand);
    UIPanel createPlayerPanel(boolean hotbarPosSwitched, boolean armor, boolean offhand);
    void setDroppingAllowed(boolean value);
    boolean isDroppingAllowed();
}
