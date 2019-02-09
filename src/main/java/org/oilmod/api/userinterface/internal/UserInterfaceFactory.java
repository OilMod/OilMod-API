package org.oilmod.api.userinterface.internal;

import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.userinterface.UIPanel;
import org.oilmod.api.userinterface.UserInterfaceBuilder;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface UserInterfaceFactory {
    UserInterface createChestInterface(EntityHumanRep player, UserInterfaceBuilder builder, String title, int rows);
    UserInterface createChestInterface(EntityHumanRep player, UserInterfaceBuilder builder, String title, int rows, ItemStackRep background);
    UIPanel createPlayerPanel(UserInterface ui, EntityHumanRep player, boolean hotbarPosSwitched, boolean armor, boolean offhand, boolean filterItems);
    UIPanel createPlayerPanel(UserInterface ui, boolean hotbarPosSwitched, boolean armor, boolean offhand, boolean filterItems);
}
