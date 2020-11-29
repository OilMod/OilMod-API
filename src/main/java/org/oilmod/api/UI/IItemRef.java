package org.oilmod.api.UI;

import org.oilmod.api.rep.inventory.InventoryRep;

import java.util.List;

/**
 * Instances of this object are reused, so storage of them creates undefined behaviour
 * Setting the value of this is done through the UI-MPI
 */
public interface IItemRef {
    boolean isNative();
    boolean isRedirectPreview();
    InventoryRep getRelatedInventory();
    int getSlotId();
    int getLocalRow();
    int getGlobalRow();
    int getLocalColumn();
    int getGlobalColumn();
    List<IItemElement> getTrace();
    void deferTo(IItemElement element, int row, int column);
    void setPreview(boolean isPreview);
}
