package org.oilmod.api.test.providers.ui;

import org.oilmod.api.UI.IItemInteractionHandler;
import org.oilmod.api.UI.IItemRef;
import org.oilmod.api.UI.UI;
import org.oilmod.api.UI.UIMPI;
import org.oilmod.api.UI.slot.ISlotType;
import org.oilmod.api.crafting.ICraftingProcessor;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.entity.EntityPlayerRep;
import org.oilmod.api.rep.inventory.InventoryRep;

public class UIHelper extends UIMPI.Helper<UIHelper> {
    @Override
    protected void openUI(EntityPlayerRep player, UI ui) {

    }

    @Override
    protected void handleNative(IItemRef handle, InventoryRep inv, ISlotType type) {

    }

    @Override
    protected void handleCustom(IItemRef handle, InventoryRep inv, ISlotType type) {

    }

    @Override
    protected void handleCustom(IItemRef handle, InventoryRep inv, IItemInteractionHandler handler) {

    }

    @Override
    protected int getSizeSlots() {
        return 0;
    }

    @Override
    protected int getSizeBorder() {
        return 0;
    }

    @Override
    protected int getSizeText() {
        return 0;
    }

    @Override
    protected int getSizeItemRender() {
        return 0;
    }

    @Override
    protected ISlotType getNormalSlotType() {
        return null;
    }

    @Override
    protected ISlotType getTakeOnlySlotType() {
        return null;
    }

    @Override
    protected ISlotType getProcessingSlotType(ICraftingProcessor processor, IResultCategory[] categories) {
        return null;
    }
}
