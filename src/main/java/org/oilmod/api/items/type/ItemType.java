package org.oilmod.api.items.type;

import gnu.trove.set.hash.THashSet;
import org.bukkit.block.BlockState;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

/**
 *
 * @param <TType> ItemType interfaces needed for this ItemType
 * @param <TItem> completely the same as TType, this is needed due to https://stackoverflow.com/questions/197190/why-cant-i-use-a-type-argument-in-a-type-parameter-with-multiple-bounds. Thanks Java :(
 */
public class ItemType<TType extends IItemGeneric, TItem extends OilItem<?> & IItemGeneric> implements IItemGeneric {
    //Static members
    public static final ItemType<?, ?> GENERIC;
    public static final ItemType<ITool, ? extends ITool> TOOL;
    public static final ItemType<IToolBlockBreaking, ? extends IToolBlockBreaking> TOOL_BLOCK_BREAKING;
    public static final ItemType<IPickaxe, ? extends IPickaxe> PICKAXE;
    public static final ItemType<IAxe, ? extends IAxe> AXE;
    public static final ItemType<IShovel, ? extends IShovel> SHOVEL;
    public static final ItemType<IShears, ? extends IShears> SHEARS;
    public static final ItemType<IHoe, ? extends IHoe> HOE;
    public static final ItemType<IWeapon, ? extends IWeapon> WEAPON;
    public static final ItemType<ISword, ? extends ISword> SWORD;
    public static final ItemType<IConsumable, ? extends IConsumable> CONSUMERABLE;
    public static final ItemType<IFood, ? extends IFood> FOOD;
    public static final ItemType<IThrowable, ? extends IThrowable> THROWABLE;
    public static final ItemType<IArmor, ? extends IArmor> ARMOR;
    public static final ItemType<IHelmet, ? extends IHelmet> HELMET;
    public static final ItemType<IChestplate, ? extends IChestplate> CHESTPLATE;
    public static final ItemType<ILeggings, ? extends ILeggings> LEGGINGS;
    public static final ItemType<IShoes, ? extends IShoes> SHOES;

    private final static EnumMap<ItemTypeEnum, ItemType<?, ?>> enumMap = new EnumMap<>(ItemTypeEnum.class);
    private final static Set<ItemType<?, ?>> registeredSet = new THashSet<>();
    private final static Set<ItemType<?, ?>> registeredSetRead = Collections.unmodifiableSet(registeredSet);

    //Enum
    public enum ItemTypeEnum {
        GENERIC,
        TOOL,
        TOOL_BLOCK_BREAKING,
        PICKAXE,
        AXE,
        SHOVEL,
        SHEARS,
        HOE,
        WEAPON,
        SWORD,
        CONSUMERABLE,
        FOOD,
        THROWABLE,
        ARMOR,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        SHOES
    }

    //Backing implementation
    public static abstract class ItemTypeHelper {
        private static ItemTypeHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(ItemTypeHelper instance) {
            if (ItemTypeHelper.instance == null) {
                synchronized (MUTEX) {
                    if (ItemTypeHelper.instance == null) {
                        ItemTypeHelper.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static ItemTypeHelper getInstance() {
            return instance;
        }
        protected abstract void apiInit(); //prepare stuff
        protected abstract void apiPostInit(); //dunno for this one
        protected abstract <TType extends IItemGeneric, TItem extends OilItem<?> & IItemGeneric> ItemType<TType, TItem> getVanillaItemType(ItemTypeEnum itemType);
        protected void unregister(ItemType<?, ?> itemType) {
            registeredSet.remove(itemType);
        }
    }

    //static methods
    static {
        ItemTypeHelper h = ItemTypeHelper.getInstance();
        h.apiInit();
        GENERIC = h.getVanillaItemType(ItemTypeEnum.GENERIC);
        TOOL = h.getVanillaItemType(ItemTypeEnum.TOOL);
        TOOL_BLOCK_BREAKING = h.getVanillaItemType(ItemTypeEnum.TOOL_BLOCK_BREAKING);
        PICKAXE = h.getVanillaItemType(ItemTypeEnum.PICKAXE);
        AXE = h.getVanillaItemType(ItemTypeEnum.AXE);
        SHOVEL = h.getVanillaItemType(ItemTypeEnum.SHOVEL);
        SHEARS = h.getVanillaItemType(ItemTypeEnum.SHEARS);
        HOE = h.getVanillaItemType(ItemTypeEnum.HOE);
        WEAPON = h.getVanillaItemType(ItemTypeEnum.WEAPON);
        SWORD = h.getVanillaItemType(ItemTypeEnum.SWORD);
        CONSUMERABLE = h.getVanillaItemType(ItemTypeEnum.CONSUMERABLE);
        FOOD = h.getVanillaItemType(ItemTypeEnum.FOOD);
        THROWABLE = h.getVanillaItemType(ItemTypeEnum.THROWABLE);
        ARMOR = h.getVanillaItemType(ItemTypeEnum.ARMOR);
        HELMET = h.getVanillaItemType(ItemTypeEnum.HELMET);
        CHESTPLATE = h.getVanillaItemType(ItemTypeEnum.CHESTPLATE);
        LEGGINGS = h.getVanillaItemType(ItemTypeEnum.LEGGINGS);
        SHOES = h.getVanillaItemType(ItemTypeEnum.SHOES);
        h.apiPostInit();
    }

    public static Set<ItemType<?, ?>> getAll() {
        return registeredSetRead;
    }

    public static <TType extends IItemGeneric, TItem extends OilItem<?> & IItemGeneric> ItemType<TType, TItem>  getStandard(ItemTypeEnum type) {
        //noinspection unchecked
        return (ItemType<TType, TItem>) enumMap.get(type);
    }


    //fields
    private final ItemTypeEnum blockTypeEnum;

    //constructor

    protected ItemType(ItemTypeEnum blockTypeEnum) {
        this.blockTypeEnum = blockTypeEnum;
        enumMap.put(blockTypeEnum, this);
        registeredSet.add(this);
    }

    //methods


    public ItemTypeEnum getBlockTypeEnum() {
        return blockTypeEnum;
    }

    /**
     * not useful
     * @return just returns itself
     */
    @Override
    public ItemType<?, ?> getItemType() {
        return this;
    }

    //java generics are too limited so this is needed
    final boolean canDestroySpecialBlock(IItemGeneric item, BlockState blockState, BlockType blockType) {
        //noinspection unchecked
        return canDestroySpecialBlock((TItem)item, blockState, blockType);
    }
    final float getDestroySpeed(IItemGeneric item, OilItemStack itemStack, BlockState blockState, BlockType blockType) {
        //noinspection unchecked
        return getDestroySpeed((TItem)item, itemStack, blockState, blockType);
    }



    //
    private static final String UNSUPPORTED = "This ItemType does not support this operation";
    protected boolean canDestroySpecialBlock(TItem item, BlockState blockState, BlockType blockType) {throw new UnsupportedOperationException(UNSUPPORTED);}
    protected float getDestroySpeed(TItem item, OilItemStack itemStack, BlockState blockState, BlockType blockType)  {throw new UnsupportedOperationException(UNSUPPORTED);}
    public int getMaxStackSize() {return 64;}

}
