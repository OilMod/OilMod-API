package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.Interface;
import de.sirati97.oilmod.api.userinterface.internal.InterfaceFactory;
import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UserInterfaceBuilder<Argument extends UIArgument> {

    public void display(Player player, Argument argument) {
        Interface ui = buildDisplay(player, argument, UIHelperBase.getInstance().getInterfaceFactory());
        display(player, ui);
    }

    public void display(Player player, Interface ui) {
        if (ui.getBuilder() != this) {
            throw new IllegalArgumentException("Ui was not created by this builder");
        }
        ui.display(player);
    }

    protected abstract Interface buildDisplay(Player player, Argument argument, InterfaceFactory factory);


}
