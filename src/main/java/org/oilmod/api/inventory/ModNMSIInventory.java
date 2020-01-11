package org.oilmod.api.inventory;

import org.oilmod.api.config.CompoundSerializable;
import org.oilmod.api.crafting.ICraftingProcessor;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.userinterface.IInteractableUIElement;
import org.oilmod.api.util.Tickable;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by sirati97 on 12.02.2016.
 */
public interface ModNMSIInventory<T extends ModInventoryObjectBase> extends CompoundSerializable, Cloneable, Tickable {
    InventoryRep getInventoryRep();
    Object getNMSInventory();
    Object getOilModInventory();
    void setOilApiMirror(T arg);
    IInteractableUIElement createUIElement();
    Collection<ICraftingProcessor> getProcessors();
    void setTitle(String title);
}
