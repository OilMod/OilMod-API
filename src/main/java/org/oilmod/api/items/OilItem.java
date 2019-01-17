package org.oilmod.api.items;

import org.oilmod.api.items.internal.ItemFactory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.oilmod.api.items.type.IItemGeneric;
import org.oilmod.api.util.OilKey;

import java.util.List;

/**
 * This class is used to add new items to the game
 */
public abstract class OilItem implements IItemGeneric {
    private Material vanillaMaterial;
    private final OilKey key;
    private String displayName;
    private Object nmsItem;
    private OilItemStackFactory[] creativeItems;
    private ItemStack[] naturalExamples;

    //Constructor


    /**
     * @param key Mod unique key that identifies the item
     * @param vanillaMaterial The Vanilla Material that is shown to the client
     * @param displayName displayed displayName of the item
     */
    public OilItem(OilKey key, Material vanillaMaterial, String displayName) {
        this.vanillaMaterial = vanillaMaterial;
        this.key = key;
        this.displayName = displayName;
    }










    //Normal getters

    /**
     *
     * @return returns that material used to display this item on vanilla clients
     */
    public Material getVanillaMaterial(OilItemStack itemStack) {
        return vanillaMaterial;
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
        return getItemEnchantability() > 0;
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










    //OilMod Helper methods
    /**
     * Will be used in the localisation api
     * @param player the player the itemstack is for.
     * @param count count of the itemstack
     * @return Bukkit ItemStack of this ModItem
     */
    public final ItemStack createItemStack(Player player, int count, int data) {
        return ItemFactory.getInstance().createStack(this, player, count, data);
    }

    /**
     *
     * @param count count of the itemstack
     * @return Bukkit ItemStack of this ModItem
     */
    public final ItemStack createItemStack(int count, int data) {
        return createItemStack(null, count, data);
    }

    /**
     * Will be used in the localisation api
     * @param player the player the itemstack is for.
     * @param count count of the itemstack
     * @return Bukkit ItemStack of this ModItem
     */
    public final ItemStack createItemStack(Player player, int count) {
        return createItemStack(player, count, 0);
    }

    /**
     *
     * @param count count of the itemstack
     * @return Bukkit ItemStack of this ModItem
     */
    public final ItemStack createItemStack(int count) {
        return createItemStack(null, count, 0);
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
    protected OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        //noinspection unchecked
        return new OilItemStack(nmsItemStack, this); //TODO why are two methods needed
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
    public final OilItemStack createOilStack(NMSItemStack nmsItemStack) {
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
    public boolean canRepairAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {
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
    public int prepareRepairAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {
        return 0;
    }

    /**
     *
     * @param itemStack The ItemStack that is used for repairing
     * @param human The Human repairing the OilItemStack
     */
    public void repairAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {}

    /**
     * This method is called when a player repairs the items in an anvil. It should do anything that could not be done in prepareRepairAnvil.
     * @param itemStack The ItemStack is question to be combined with the OilItemStack
     * @param human The Human that is trying this
     * @return returns whether it is allowed to combine the itemstack with the OilItemStack
     */
    public boolean canCombineAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {
        return false;
    }

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the combination.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a combination is really executed.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    public void prepareCombineAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {}

    /**
     * This method is called when a player combines the items in an anvil. It should do anything that could not be done in prepareCombineAnvil.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    public void combineAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {}
}
