package org.oilmod.api.inventory;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.crafting.CraftingProcessorBuilder;
import org.oilmod.api.crafting.ICraftingProcessor;
import org.oilmod.api.data.IDataParent;
import org.oilmod.api.data.ItemStackData;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.crafting.ICraftingManager;
import org.oilmod.api.rep.crafting.IIngredientCategory;
import org.oilmod.api.rep.crafting.IResultCategory;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.stateable.complex.IComplexState;
import org.oilmod.api.stateable.complex.IInventoryState;
import org.oilmod.api.util.ITicker;
import org.oilmod.api.util.InventoryBuilder;
import org.oilmod.api.util.InventoryListBuilder;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.oilmod.util.LamdbaCastUtils.cast;

/**
 * Created by sirati97 on 15.01.2016.
 */
public abstract class InventoryFactory {
    private static InventoryFactory instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(InventoryFactory instance) {
        if (InventoryFactory.instance == null) {
            synchronized (MUTEX) {
                if (InventoryFactory.instance == null) {
                    InventoryFactory.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static InventoryFactory getInstance() {
        return instance;
    }
    
    public static  <T extends ModInventoryObjectBase<T>> Builder<T> builder(String nbtName) {
        return new Builder<>(nbtName);
    }

    @FunctionalInterface
    public interface DropPredicate {
        boolean test(InventoryRep rep, int index, int top, int left);
    }

    private static final DropPredicate DROP_ALL = (rep, index, top, left) -> true;
    private static final DropPredicate DROP_NONE = (rep, index, top, left) -> false;

    //tho only reason why this is generic is so that the create method can have output the right type without casting
    //just a lot of generics magic, allows compile time checking and makes code look nicer when using the builder
    public static class Builder<T extends ModInventoryObjectBase<T>> extends BuilderWRefHolder<T, Object>{

        public Builder(String nbtName) {
            super(nbtName);
        }
    }
    //this subclass is only there to keep track of extra generics, it cannot be directly instantiated. Needs to be public for method access
    public static class BuilderWRefHolder<T extends ModInventoryObjectBase<T>, RefHolder>{
        private final String nbtName;
        private int rows = -1;
        private int columns = -1;
        private ItemFilter filter;
        private boolean mainInventory;
        Function<IInventoryState, ModNMSIInventory<T>> factory;
        Function<InventoryData<T>, T> ctor;
        private String standardTitle;
        private ITicker ticker;
        private List<Function<InventoryRep, ICraftingProcessor>> craftingProcessors;
        private DropPredicate dropPredicate = DROP_NONE;
        private Supplier<RefHolder> refHolderFactory;

        private BuilderWRefHolder(String nbtName) {
            this.nbtName = nbtName;
        }


        public <NewRefHolder> BuilderWRefHolder<T, NewRefHolder> refHolder(Supplier<NewRefHolder> refFactory) {
            Validate.isTrue(this.refHolderFactory == null, "Cannot set refHolder twice!");
            BuilderWRefHolder<T, NewRefHolder> result = cast(this);
            result.refHolderFactory = refFactory;
            return result;
        }

        public BuilderWRefHolder<T, RefHolder> size(int rows, int columns) {
            this.columns = columns;
            this.rows = rows;
            return checkAccess();
        }

        public BuilderWRefHolder<T, RefHolder> size(int size) {
            this.columns = size;
            this.rows = 0;
            return checkAccess();
        }


        public BuilderWRefHolder<T, RefHolder> chestSize(int rows) {
            this.columns = 9;
            this.rows = rows;
            return checkAccess();
        }

        public BuilderWRefHolder<T, RefHolder> filter(ItemFilter filter) {
            this.filter = filter;
            return checkAccess();
        }

        public BuilderWRefHolder<T, RefHolder> mainInventory() {
            this.mainInventory = true;
            return checkAccess();
        }

        public BuilderWRefHolder<T, RefHolder> standardTitle(String standardTitle) {
            this.standardTitle = standardTitle;
            return checkAccess();
        }

        public BuilderWRefHolder<T, RefHolder> ticker(ITicker ticker) {
            this.ticker = ticker;
            return checkAccess();
        }

        public BuilderWRefHolder<T, RefHolder> dropAll() {
            this.dropPredicate = DROP_ALL;
            return this;
        }

        public BuilderWRefHolder<T, RefHolder> dropConditional(DropPredicate dropPredicate) {
            this.dropPredicate = dropPredicate;
            return this;
        }

        public BuilderWRefHolder<T, RefHolder> processor(Function<InventoryRep, ICraftingProcessor> processor) {
            if (craftingProcessors == null) craftingProcessors = new ObjectArrayList<>();
            craftingProcessors.add(processor);
            return checkAccess();
        }

        public <Type extends ICraftingProcessor> BuilderWRefHolder<T, RefHolder> processor(ICraftingManager manager, Function<CraftingProcessorBuilder<?, RefHolder>, CraftingProcessorBuilder<Type, RefHolder>> processor) {
            return processor(inventoryRep -> {
                CraftingProcessorBuilder<?, RefHolder> b = new CraftingProcessorBuilder<>(manager);
                if (refHolderFactory != null)b.refHolder(refHolderFactory);
                return processor.apply(b.mainInv(inventoryRep)).build();
            });
        }

        private BuilderWRefHolder<T, RefHolder> checkAccess() {
            if (factory != null)throw new IllegalStateException("calling type setter makes builder immutable. e.g.: basic(), furnace(), crafting()");
            return this;
        }
        
        public Builder<ModInventoryObject> basic() {
            if (rows == -1 || columns == -1)throw new IllegalArgumentException("size not set!");

            Builder<ModInventoryObject> result  = cast(this); //abusing type erasure
            result.factory = getInstance().getBasicInventoryFactory(rows, columns, standardTitle, filter, getCraftingProcessors(), dropPredicate);
            result.ctor = ModInventoryObject::new;
            return result;
        }


        public Builder<ModFurnaceInventoryObject> furnace() {
            return furnaceSpecial(ModFurnaceInventoryObject::new);
        }

        public Builder<ModFurnaceInventoryObject> furnaceSpecial(Function<InventoryData<ModFurnaceInventoryObject>, ModFurnaceInventoryObject> specialFactory) {
            if (ticker == null)throw new IllegalArgumentException("ticker not set!");

            Builder<ModFurnaceInventoryObject> result  = cast(this); //abusing type erasure
            result.factory = getInstance().getFurnaceInventoryFactory(standardTitle, ticker, filter, getCraftingProcessors(), dropPredicate);
            result.ctor = specialFactory;
            return result;
        }

        public Builder<ModPortableCraftingInventoryObject> crafting() {
            if (rows < 1 || columns < 1)throw new IllegalArgumentException("2d size not set!");

            Builder<ModPortableCraftingInventoryObject> result  = cast(this); //abusing type erasure
            result.factory = getInstance().getPortableCraftingInventoryFactory(rows, columns, standardTitle, filter, getCraftingProcessors(), dropPredicate);
            result.ctor = ModPortableCraftingInventoryObject::new;
            return result;
        }


        public T create(IInventoryState itemStack) {
            if (factory == null)throw new IllegalStateException("you need to call a type setter method. e.g.: basic(), furnace(), crafting()");

            InventoryData<T> iData = new InventoryData<>(nbtName, itemStack, () -> factory.apply(itemStack), false);
            T result = ctor.apply(iData);
            getInstance().checkInventoryHolder(itemStack, result, mainInventory);
            return result;
        }
        private final static ICraftingProcessor[] emptyArrayPro = new ICraftingProcessor[0];
        private Function<InventoryRep, ICraftingProcessor[]> getCraftingProcessors() {
            return craftingProcessors==null?(inv)->emptyArrayPro:(inv)->{
                ICraftingProcessor[] result = new ICraftingProcessor[craftingProcessors.size()];
                //boolean needsPreviewInv = false;
                for (int i = 0; i < result.length; i++) {
                    result[i] = craftingProcessors.get(i).apply(inv);
                    //if (result[i].needPreviewShadowInv())needsPreviewInv = true;
                }
                //if (needsPreviewInv)
                return result;
            };
        }
    }

    //ModPortableCraftingInventoryObject

    //###ItemStackData###
    public abstract ItemStackData createItemStackData(String name, IDataParent dataParent);

    //###Other stuff###
    protected <T extends ModInventoryObjectBase<T>> void checkInventoryHolder(IComplexState itemStack, T inventory, boolean mainItemstackInventory) {
        if (!(itemStack instanceof OilItemStack))return;
        if (mainItemstackInventory || ((OilItemStack) itemStack).getInventory() == null) {
            ((OilItemStack) itemStack).setMainInventory(inventory);
        }
    }

    //###Factories###
    protected abstract Function<IInventoryState, ModNMSIInventory<ModInventoryObject>> getBasicInventoryFactory(int rows, int columns, String inventoryTitle, ItemFilter filter, Function<InventoryRep, ICraftingProcessor[]> processorFactory, DropPredicate dropPredicate);
    protected abstract Function<IInventoryState, ModNMSIInventory<ModFurnaceInventoryObject>> getFurnaceInventoryFactory(String inventoryTitle, ITicker ticker, ItemFilter filter, Function<InventoryRep, ICraftingProcessor[]> processorFactory, DropPredicate dropPredicate);
    protected abstract Function<IInventoryState, ModNMSIInventory<ModPortableCraftingInventoryObject>> getPortableCraftingInventoryFactory(int width, int height, String inventoryTitle, ItemFilter filter, Function<InventoryRep, ICraftingProcessor[]> processorFactory, DropPredicate dropPredicate);

}
