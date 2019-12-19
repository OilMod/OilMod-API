package org.oilmod.api.crafting;

import org.oilmod.api.rep.crafting.ICraftingManager;
import org.oilmod.api.rep.crafting.IIngredientCategory;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.inventory.InventoryRep;

import java.util.Map;

public class CraftingSlotCraftingProcessor extends ResultSlotCraftingProcessor {
    public CraftingSlotCraftingProcessor(Map<IIngredientCategory, InventoryRep> supplierMap, Map<IResultCategory, InventoryRep> resultMap, ICraftingManager manager) {
        super(supplierMap, resultMap, manager);
    }
}
