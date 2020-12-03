package org.oilmod.api.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import org.jetbrains.annotations.NotNull;
import org.oilmod.api.rep.crafting.ICraftingManager;
import org.oilmod.api.rep.crafting.IIngredientCategory;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.inventory.InventoryRep;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class CraftingSlotCraftingProcessor extends ResultSlotCraftingProcessor {
    public CraftingSlotCraftingProcessor(@NotNull Map<IIngredientCategory, InventoryRep> supplierMap, @Nullable Object2ObjectMap<IIngredientCategory, List<InventoryRep>> reserveMap, Map<IResultCategory, InventoryRep> resultMap, @Nullable Object2ObjectMap<IResultCategory, List<InventoryRep>> overflowMap, ICraftingManager manager) {
        super(supplierMap, reserveMap, resultMap, overflowMap, manager);
    }
}
