package org.oilmod.api.crafting;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.rep.crafting.ICraftingManager;
import org.oilmod.api.rep.crafting.IIngredientCategory;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.util.InventoryBuilder;
import org.oilmod.api.util.InventoryListBuilder;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.oilmod.util.LamdbaCastUtils.cast;

public class CraftingProcessorBuilder<Processor extends ICraftingProcessor, RefHolder> {
    private final Object2ObjectMap<IResultCategory, InventoryRep> results = new Object2ObjectOpenHashMap<>();
    private final Object2ObjectMap<IIngredientCategory, InventoryRep> ingre = new Object2ObjectOpenHashMap<>();
    private Object2ObjectMap<IResultCategory, List<InventoryRep>> overflow;
    private Object2ObjectMap<IIngredientCategory, List<InventoryRep>> reserve;
    private ICraftingManager craftingManager;
    private static final Function<CraftingProcessorBuilder<ResultSlotCraftingProcessor, ?>, ResultSlotCraftingProcessor> ResultSlotCtor = b -> new ResultSlotCraftingProcessor(b.ingre, b.reserve, b.results, b.overflow, b.craftingManager);
    Function<CraftingProcessorBuilder<Processor, RefHolder>, Processor> ctor;
    private InventoryRep mainInv;
    private RefHolder refHolder;

    public CraftingProcessorBuilder<Processor, RefHolder> mainInv(InventoryRep mainInv) {
        Validate.isTrue(this.mainInv == null, "Cannot set mainInv twice!");
        this.mainInv = mainInv;
        return this;
    }

    public <NewRefHolder> CraftingProcessorBuilder<Processor, NewRefHolder> refHolder(Supplier<NewRefHolder> refFactory) {
        Validate.isTrue(this.refHolder == null, "Cannot set refHolder twice!");
        CraftingProcessorBuilder<Processor, NewRefHolder> result = cast(this);
        result.refHolder = refFactory.get();
        return result;
    }


    public RefHolder ref() {
        return refHolder;
    }

    public CraftingProcessorBuilder(ICraftingManager craftingManager) {
        this.craftingManager = craftingManager;
    }


    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> ingre(IIngredientCategory category) {
        return ingre(mainInv, category);
    }

    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> ingre(Function<RefHolder, InventoryRep> getter, IIngredientCategory category) {
        return ingre(getter.apply(ref()), category);
    }

    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> ingre(InventoryRep invArg, IIngredientCategory category) {
        return new InventoryBuilder<>(inv -> ingre.merge(category, inv, (vN, vO) -> {throw alreadyAdded(category);}), invArg, cast(this));
    }

    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> result(IResultCategory category) {
        return result(mainInv, category);
    }

    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> result(Function<RefHolder, InventoryRep> getter, IResultCategory category) {
        return result(getter.apply(ref()), category);
    }

    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> result(InventoryRep invArg, IResultCategory category) {
        return new InventoryBuilder<>(inv -> results.merge(category, inv, (vN, vO) -> {throw alreadyAdded(category);}), invArg, cast(this));
    }


    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> overflow(IResultCategory category) {
        return overflow(mainInv, category);
    }

    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> overflow(Function<RefHolder, InventoryRep> getter, IResultCategory category) {
        return overflow(getter.apply(ref()), category);
    }

    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> overflow(InventoryRep invArg, IResultCategory category) {
        if (overflow == null) overflow = new Object2ObjectOpenHashMap<>();
        return new InventoryListBuilder<>(invList -> overflow.merge(category, invList, (vN, vO) -> {throw alreadyAdded(category);}), invArg, cast(this));
    }

    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> reserve(IIngredientCategory category) {
        return reserve(mainInv, category);
    }

    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> reserve(Function<RefHolder, InventoryRep> getter, IIngredientCategory category) {
        return reserve(getter.apply(ref()), category);
    }

    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> reserve(InventoryRep invArg, IIngredientCategory category) {
        if (reserve == null) reserve = new Object2ObjectOpenHashMap<>();
        return new InventoryListBuilder<>(invList -> reserve.merge(category, invList, (vN, vO) -> {throw alreadyAdded(category);}), invArg, cast(this));
    }



    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> buildInv(BiConsumer<RefHolder, InventoryRep> setter) {
        return buildInv(mainInv, setter);
    }

    public InventoryBuilder<CraftingProcessorBuilder<Processor, RefHolder>> buildInv(InventoryRep invArg, BiConsumer<RefHolder, InventoryRep> setter) {
        return new InventoryBuilder<>(invOut -> setter.accept(ref(), invOut), invArg, cast(this));
    }

    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> buildInvList(BiConsumer<RefHolder, List<InventoryRep>> setter) {
        return buildInvList(mainInv, setter);
    }

    public InventoryListBuilder<CraftingProcessorBuilder<Processor, RefHolder>> buildInvList(InventoryRep invArg, BiConsumer<RefHolder, List<InventoryRep>> setter) {
        return new InventoryListBuilder<>(invOut -> setter.accept(ref(), invOut), invArg, cast(this));
    }

    private <K> IllegalStateException alreadyAdded(K key) {
        return new IllegalStateException(String.format("There already an inventory associated with the category %s", key));
    }

    public CraftingProcessorBuilder<ResultSlotCraftingProcessor, RefHolder> resultSlot() {
        CraftingProcessorBuilder<ResultSlotCraftingProcessor, RefHolder> newT = cast(this);
        newT.ctor = cast(ResultSlotCtor);
        return newT;
    }

    public Processor build() {
        return ctor.apply(this);
    }


    //.processors((inv)-> new ResultSlotCraftingProcessor(Collections.singletonMap(TestIngredientCategory, inv.createView2d(0, 4, 4)), Collections.singletonMap(TestResultCategory, inv.createView(16, 2)), TestCraftingManager))

}
