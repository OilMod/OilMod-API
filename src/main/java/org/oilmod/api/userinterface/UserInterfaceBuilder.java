package org.oilmod.api.userinterface;

import org.oilmod.api.userinterface.internal.UIHelper;
import org.oilmod.api.userinterface.internal.UserInterface;
import org.oilmod.api.userinterface.internal.UserInterfaceFactory;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UserInterfaceBuilder<Argument> {

    protected void displayNewUI(Player player, Argument argument) {
        UserInterface ui = buildDisplay(player, argument, UIHelper.getInstance().getInterfaceFactory());
        displayUI(player, ui);
    }

    public void displayUI(Player player, UserInterface ui) {
        if (ui.getBuilder() != this) {
            throw new IllegalArgumentException("Ui was not created by this builder");
        }
        ui.display(player);
    }

    protected abstract UserInterface buildDisplay(Player player, Argument argument, UserInterfaceFactory factory);


}
