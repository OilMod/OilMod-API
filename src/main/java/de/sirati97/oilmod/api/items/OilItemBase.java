package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.items.internal.ItemFactoryBase;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * This class is used to add new items to the game
 * @param <T> Determines which OilItemStack class is used
 */
public abstract class OilItemBase<T extends OilItemStack> {
    private Material material;
    private int data;
    private final String itemIdentifier;
    private String name;
    private Object nmsItem;
    private int maxStackSize;
    private OilSpecificItemstackFactory[] creativeItems;
    private ItemStack[] naturalExamples;

    /**
     *
     * @param material The Vanilla Material that is shown to the client
     * @param data The Vanilla Material Data
     * @param itemIdentifier Mod unique key that identifies the item
     * @param name displayed name of the item
     */
    public OilItemBase(Material material, int data, String itemIdentifier, String name) {
        this(material, data, itemIdentifier, 64, name);
    }

    /**
     *
     * @param material The Vanilla Material that is shown to the client
     * @param data The Vanilla Material Data
     * @param itemIdentifier Mod unique key that identifies the item
     * @param maxStackSize Maximal stack size of this item
     * @param name displayed name of the item
     */
    public OilItemBase(Material material, int data, String itemIdentifier, int maxStackSize, String name) {
        this.material = material;
        this.data = data;
        this.itemIdentifier = itemIdentifier;
        this.maxStackSize = maxStackSize;
        this.name = name;
    }

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
     * This method returns the display name of the item that should be seen by the player. Will be used in the localisation api
     * @param player The player that should see the name
     * @return returns the display name of the item that should be seen by the player
     */
    public String getName(Player player) {
        return name;
    }

    /**
     *
     * @return returns the display name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Will be used in the localisation api
     * @param player the player the itemstack is for.
     * @param size size of the itemstack
     * @return Bukkit ItemStack of this ModItem
     */
    public final ItemStack createItemStack(Player player, int size) {
        return ItemFactoryBase.getInstance().createStack(this, player, size);
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
    public final OilSpecificItemstackFactory[] getCreativeItems() {
        if (creativeItems == null) {
            creativeItems = createCreativeItems();
        }
        return creativeItems;
    }

    /**
     *
     * @return creates the array of factories that are displayed in the oilmod creative menu.
     */
    protected OilSpecificItemstackFactory[] createCreativeItems() {
        return new OilSpecificItemstackFactory[] {new OilSpecificItemstackFactory() {
            @Override
            public ItemStack create() {
                return createItemStack(1);
            }
        }};
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
     * @return creates the array of instances of this item that represent this item in wildcard crafting recipes. Result never changes.
     */
    protected ItemStack[] createNaturalExamples() {
        return new ItemStack[]{createItemStack(1)};
    }

    /**
     * Internal - should not e called by user code
     * @param nmsItem the nms item
     */
    void setNmsItem(Object nmsItem) {
        this.nmsItem = nmsItem;
    }

    /**
     * Internal - should not e called by user code
     * @return returns the nms item
     */
    public Object getNmsItem() {
        return nmsItem;
    }

    /**
     *
     * @return returns the maximal stack size of this item
     */
    public int getMaxStackSize() {
        return maxStackSize;
    }

    /**
     *
     * @return returns the mod unique identifier of this item
     */
    public String getItemIdentifier() {
        return itemIdentifier;
    }

    /**
     * Should be overwritten to change which class represents the itemstack of this item - should not be called by user code
     * @param nmsItemStack the NMS itemstack
     * @return instance of T
     */
    protected T createOilItemStackInstance(NMSItemStack nmsItemStack) {
        //noinspection unchecked
        return (T) new OilItemStack(nmsItemStack, this);
    }

    /**
     * Internal - should not e called by user code
     * @param nmsItemStack the NMS itemstack
     * @return instance of T
     */
    public final T createOilStack(NMSItemStack nmsItemStack) {
        return createOilItemStackInstance(nmsItemStack);
    }

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
     * Override this method when allowing enchanting
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

    /**
     *
     * @param itemStack The Itemstack is question to be used for repairing the OilItemStack
     * @param human The Human that is trying this
     * @return returns whether it is allowed to repair the OilItemStack with the itemstack
     */
    public boolean canRepairAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {
        return false;
    }

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the repair.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a repair is really executed.
     * @param itemStack The itemstack that is used for repairing
     * @param human The Human repairing the OilItemStack
     * @return how many of these items should be used
     */
    public int prepareRepairAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {
        return 0;
    }

    /**
     *
     * @param itemStack The itemstack that is used for repairing
     * @param human The Human repairing the OilItemStack
     */
    public void repairAnvil(T oilItemStack, ItemStack itemStack, HumanEntity human) {}

    /**
     * This method is called when a player repairs the items in an anvil. It should do anything that could not be done in prepareRepairAnvil.
     * @param itemStack The Itemstack is question to be combined with the OilItemStack
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
