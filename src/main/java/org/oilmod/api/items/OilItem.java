package org.oilmod.api.items;

import org.bukkit.block.BlockState;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.internal.ItemFactory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.oilmod.api.util.OilKey;

import java.util.List;

/**
 * This class is used to add new items to the game
 * @param <T> Determines which OilItemStack class is used
 */
public abstract class OilItem<T extends OilItemStack> {
    private Material material;
    private int data;
    private final OilKey key;
    private String displayName;
    private Object nmsItem;
    private int maxStackSize;
    private OilItemStackFactory[] creativeItems;
    private ItemStack[] naturalExamples;
    private ItemType itemType;

    //Constructor



    /**
     * @param key Mod unique key that identifies the item
     * @param itemType
     * @param material The Vanilla Material that is shown to the client
     * @param data The Vanilla Material Data
     * @param displayName displayed displayName of the item
     */
    public OilItem(OilKey key, ItemType itemType, Material material, int data, String displayName) {
        this(key, itemType, material, data, 64, displayName);
    }

    /**
     * @param key Mod unique key that identifies the item
     * @param itemType
     * @param material The Vanilla Material that is shown to the client
     * @param data The Vanilla Material Data
     * @param maxStackSize Maximal stack size of this item
     * @param displayName displayed displayName of the item
     */
    public OilItem(OilKey key, ItemType itemType, Material material, int data, int maxStackSize, String displayName) {
        this.itemType = itemType;
        this.material = material;
        this.data = data;
        this.key = key;
        this.maxStackSize = maxStackSize;
        this.displayName = displayName;
    }










    //Normal getters
    /**
     *
     * @return returns that material data used to display this item on vanilla clients
     */
    public int getData() {
        return data;
    }

    /**
     *
     * @return returns that material used to display this item on vanilla clients
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * This method returns the display displayName of the item that should be seen by the player. Will be used in the localisation api
     * @param player The player that should see the displayName
     * @return returns the display displayName of the item that should be seen by the player
     */
    public String getDisplayName(Player player) {
        return displayName;
    }

    /**
     *
     * @return returns the display displayName of the item
     */
    public String getDisplayName() {
        return displayName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    /**
     *
     * @return returns the maximal stack size of this item
     */
    public int getMaxStackSize() {
        return maxStackSize==0?getItemType().getMaxStackSize():maxStackSize;
    }

    /**
     *
     * @return returns the mod unique key of this item
     */
    public OilKey getOilKey() {
        return key;
    }

    /**
     * You do no need to override this method. just override getEnchantSelectModifier() and let it return a value bigger than 0
     * @return return whether the item is enchantable.
     */
    public boolean isEnchantable() {
        return getEnchantSelectModifier() > 0;
    }

    /**
     * Override this method and return a value greater than 0 to allow enchanting
     * @return return the enchant select modifier. An item with the same enchant select modifier and the same allowed enchantments will behave the same when enchanted with the same seed
     */
    public int getEnchantSelectModifier() {
        return 0;
    }

    /**
     * Override this method to allow enchanting
     * @param enchantment the enchantment that is checked
     * @return returns whether the enchantment can be applied to this item
     */
    public boolean canEnchant(Enchantment enchantment) {
        return false;
    }

    /**
     *
     * @return returns the standard description of your item
     */
    public List<String> getStandardDescription() {
        return null;
    }










    //OilMod Item methods
    public boolean canDestroySpecialBlock(BlockState blockState, BlockType blockType) {
        return getItemType().canDestroySpecialBlock(this, blockState, blockType);
    }

    public float getDestroySpeed(BlockState blockState, BlockType blockType) {
        return getItemType().getDestroySpeed(this, blockState, blockType);
    }










    //OilMod Helper methods
    /**
     * Will be used in the localisation api
     * @param player the player the itemstack is for.
     * @param size size of the itemstack
     * @return Bukkit ItemStack of this ModItem
     */
    public final ItemStack createItemStack(Player player, int size) {
        return ItemFactory.getInstance().createStack(this, player, size);
    }

    /**
     *
     * @param size size of the itemstack
     * @return Bukkit ItemStack of this ModItem
     */
    public final ItemStack createItemStack(int size) {
        return createItemStack(null, size);
    }

    /**
     *
     * @return returns an array of factories that are displayed in the oilmod creative menu. Result never changes.
     */
    public final OilItemStackFactory[] getCreativeItems() {
        if (creativeItems == null) {
            creativeItems = createCreativeItems();
        }
        return creativeItems;
    }

    /**
     *
     * @return creates the array of factories that are displayed in the oilmod creative menu.
     */
    protected OilItemStackFactory[] createCreativeItems() {
        return new OilItemStackFactory[] {() -> createItemStack(1)};
    }

    /**
     *
     * @return returns an array of instances of this item that represent this item in wildcard crafting recipes
     */
    public final ItemStack[] getNaturalExamples() {
        if (naturalExamples == null) {
            naturalExamples = createNaturalExamples();
        }
        return naturalExamples;
    }

    /**
     *
     * @return creates the array of instances of this item that represent this item in wildcard crafting recipes. Result is not supposed to vary during runtime.
     */
    protected ItemStack[] createNaturalExamples() {
        return new ItemStack[]{createItemStack(1)};
    }

    /**
     * Should be overwritten to change which class represents the itemstack of this item - should not be called by user code
     * @param nmsItemStack the NMS itemstack
     * @return instance of T
     */
    protected T createOilItemStackInstance(NMSItemStack nmsItemStack) {
        //noinspection unchecked
        return (T) new OilItemStack(nmsItemStack, this); //TODO why are two methods needed
    }










    //OilMod internal
    /**
     * Internal - should not be called by user code
     * @param nmsItem the nms item
     */
    void setNmsItem(Object nmsItem) {
        this.nmsItem = nmsItem;
    }

    /**
     * Internal - should not be called by user code
     * @return returns the nms item
     */
    public Object getNmsItem() {
        return nmsItem;
    }

    /**
     * Internal - should not be called by user code
     * @param nmsItemStack the NMS itemstack
     * @return instance of T
     */
    public final T createOilStack(NMSItemStack nmsItemStack) {
        return createOilItemStackInstance(nmsItemStack);
    }










    //OilMod Events
    /**
     * Called when a user uses this item
     * @param itemStack The OilItemStack that was used
     * @param player the player using the itemstack
     * @return
     */
    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return false;
    }

    /**
     * Called when a user uses this item on a block
     * @param itemStack The OilItemStack that was used
     * @param player the player using the itemstack
     * @param blockClicked the block that the item was used on
     * @param blockFace the block face that was clicked
     * @return
     */
    public boolean onUseOnBlock(OilItemStack itemStack, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
    }

    /**
     * Called when a user left clicks this item
     * @param itemStack The OilItemStack that was left clicked
     * @param player the player that left clicked the itemstack
     * @return
     */
    public boolean onLeftClick(OilItemStack itemStack, Player player, Action action) {
        return false;
    }

    /**
     * Called when a user left clicks this item on a block
     * @param itemStack The OilItemStack that was left clicked
     * @param player the player that left clicked the itemstack
     * @param blockClicked the block that was clicked
     * @param blockFace the block face that was clicked
     * @return
     */
    public boolean onLeftClickOnBlock(OilItemStack itemStack, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
    }

    /**
     *
     * @param itemStack ItemStack the player tries to repair the item with.
     * @param human The Human that is trying this
     * @return returns whether it is allowed to repair the OilItemStack with the itemstack
     */
    public boolean canRepairAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {
        return false;
    }

    //TODO fix this javadoc. fix ll the javadocs
    /**
     * Called when a preview of the result is needed. This code should under no circumstances have any side effects.
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the repair.
     * This should only include lightweight and fast algorithms. This method should not modify the ingredients in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a repair is really executed.
     * @param itemStack The ItemStack that is used for repairing
     * @param human The Human repairing the OilItemStack
     * @return how many of these items should be used
     */
    public int prepareRepairAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {
        return 0;
    }

    /**
     *
     * @param itemStack The ItemStack that is used for repairing
     * @param human The Human repairing the OilItemStack
     */
    public void repairAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {}

    /**
     * This method is called when a player repairs the items in an anvil. It should do anything that could not be done in prepareRepairAnvil.
     * @param itemStack The ItemStack is question to be combined with the OilItemStack
     * @param human The Human that is trying this
     * @return returns whether it is allowed to combine the itemstack with the OilItemStack
     */
    public boolean canCombineAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {
        return false;
    }

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the combination.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a combination is really executed.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    public void prepareCombineAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {}

    /**
     * This method is called when a player combines the items in an anvil. It should do anything that could not be done in prepareCombineAnvil.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    public void combineAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {}
}
