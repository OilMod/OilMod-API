package org.oilmod.api.crafting;

import org.oilmod.api.rep.crafting.ICraftingManager;
import org.oilmod.api.rep.inventory.InventoryRep;

public class CraftingProcessorBuilder<Type extends ICraftingProcessor> extends CraftingProcessorBuilderBase<CraftingProcessorBuilder<Type>, Type> {


    public CraftingProcessorBuilder(ICraftingManager craftingManager, InventoryRep invFor) {
        super(craftingManager);
    }

    @Override
    public CraftingProcessorBuilder<ResultSlotCraftingProcessor> resultSlot() {
        return super.resultSlot();
    }

    @Override
    public Type build() {
        return super.build();
    }
}
