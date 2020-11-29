package org.oilmod.api.UI.slot;

import org.oilmod.api.rep.inventory.InventoryRep;

public interface ISlotTypePreviewable extends ISlotType {
    InventoryRep getPreviewInventory();
}
