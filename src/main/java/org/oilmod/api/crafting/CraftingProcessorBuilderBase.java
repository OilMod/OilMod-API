package org.oilmod.api.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.oilmod.api.rep.crafting.ICraftingManager;
import org.oilmod.api.rep.crafting.IIngredientCategory;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.util.InventoryBuilder;

import java.util.function.Function;

import static org.oilmod.util.LamdbaCastUtils.cast;

public abstract class CraftingProcessorBuilderBase<T extends CraftingProcessorBuilderBase<T, ?>, Type extends ICraftingProcessor> {
    private final Object2ObjectMap<IResultCategory, InventoryRep> results = new Object2ObjectOpenHashMap<>();
    private final Object2ObjectMap<IIngredientCategory, InventoryRep> ingre = new Object2ObjectOpenHashMap<>();
    private ICraftingManager craftingManager;
    private static final Function<CraftingProcessorBuilderBase<?, ResultSlotCraftingProcessor>, ResultSlotCraftingProcessor> ResultSlotCtor = b -> new ResultSlotCraftingProcessor(b.ingre, b.results, b.craftingManager);
    Function<CraftingProcessorBuilderBase<?, Type>, Type> ctor;

    protected CraftingProcessorBuilderBase(ICraftingManager craftingManager) {
        this.craftingManager = craftingManager;
    }


    public InventoryBuilder<T> ingre(InventoryRep invArg, IIngredientCategory category) {
        return new InventoryBuilder<T>(inv
                -> ingre.compute(category, (category1, oldInv) -> {
            if (oldInv == null) {
                return inv;
            } else {
                throw new IllegalStateException(String.format("There already an inventory associated with the category %s", category1));
            }
        }), invArg, cast(this));
    }

    public InventoryBuilder<T> result(InventoryRep invArg, IResultCategory category) {
        return new InventoryBuilder<T>(inv
                -> results.compute(category, (category1, oldInv) -> {
            if (oldInv == null) {
                return inv;
            } else {
                throw new IllegalStateException(String.format("There already an inventory associated with the category %s", category1));
            }
        }), invArg, cast(this));
    }

    protected <NewT extends CraftingProcessorBuilderBase<NewT, ResultSlotCraftingProcessor>> NewT resultSlot() {
        NewT newT = cast(this);
        newT.ctor = ResultSlotCtor;
        return newT;
    }

    protected Type build() {
        return ctor.apply(this);
    }


    //.processors((inv)-> new ResultSlotCraftingProcessor(Collections.singletonMap(TestIngredientCategory, inv.createView2d(0, 4, 4)), Collections.singletonMap(TestResultCategory, inv.createView(16, 2)), TestCraftingManager))

}
