package org.oilmod.api.items;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.items.internal.ItemFactory;
import org.oilmod.api.items.type.IItemGeneric;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.providers.ItemProvider;
import org.oilmod.api.util.OilKey;

import java.util.Collections;
import java.util.List;

/**
 * This class is used to add new items to the game
 */
public abstract class OilItem implements IItemGeneric {
    private ItemRep vanillaItem;
    private OilKey key;
    private String displayName;
    private Object nmsItem;
    private OilItemStackFactory[] creativeItems;
    private ItemStackRep[] naturalExamples;

    //Constructor


    /**
     * @param vanillaItem The Vanilla Material that is shown to the client
     * @param displayName displayed displayName of the item
     */
    public OilItem(ItemProvider vanillaItem, String displayName) {
        this.vanillaItem = vanillaItem.getProvidedItem();
        this.displayName = displayName;
    }

    @Override
    public void setOilKey(OilKey key) {
        Validate.isTrue(this.key == null, "Cannot manually set key! This is done by the registry!");
        this.key = key;
    }

    //Normal getters

    /**
     *
     * @return returns that material used to display this item on vanilla clients
     */
    public ItemRep getVanillaItem(OilItemStack itemStack) {
        return vanillaItem;
    }

    /**
     * This method returns the display displayName of the item that should be seen by the player. Will be used in the localisation api
     * @param player The player that should see the displayName
     * @return returns the display displayName of the item that should be seen by the player
     */
    public String getDisplayName(EntityHumanRep player) {
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
    public final OilKey getOilKey() {
        return key;
    }








    //####EVERYTHING BELOW THIS IS OUTDATED AND CONSIDERED FOR REMOVAL. TODO: check for removal or improve/implement


    /**
     * You do no need to override this method. just override getEnchantSelectModifier() and let it return a value bigger than 0
     * @return return whether the item is enchantable.
     */
    public boolean isEnchantable() {
        return getItemEnchantability() > 0;
    }


    /**
     *
     * @return returns the standard description of your item
     */
    public List<String> getStandardDescription() {
        return Collections.emptyList();
    }



    ///####SECTION END#####












    //OilMod Helper methods
    /**
     * Will be used in the localisation api
     * @param player the player the itemstack is for.
     * @param count count of the itemstack
     * @return Bukkit ItemStackRep of this ModItem
     */
    public final ItemStackRep createItemStack(EntityHumanRep player, int count, int data) {
        return ItemFactory.getInstance().createStack(this, player, count, data);
    }

    /**
     *
     * @param count count of the itemstack
     * @return Bukkit ItemStackRep of this ModItem
     */
    public final ItemStackRep createItemStack(int count, int data) {
        return createItemStack(null, count, data);
    }

    /**
     * Will be used in the localisation api
     * @param player the player the itemstack is for.
     * @param count count of the itemstack
     * @return Bukkit ItemStackRep of this ModItem
     */
    public final ItemStackRep createItemStack(EntityHumanRep player, int count) {
        return createItemStack(player, count, 0);
    }

    /**
     *
     * @param count count of the itemstack
     * @return Bukkit ItemStackRep of this ModItem
     */
    public final ItemStackRep createItemStack(int count) {
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
    public final ItemStackRep[] getNaturalExamples() {
        if (naturalExamples == null) {
            naturalExamples = createNaturalExamples();
        }
        return naturalExamples;
    }

    /**
     *
     * @return creates the array of instances of this item that represent this item in wildcard crafting recipes. Result is not supposed to vary during runtime.
     */
    protected ItemStackRep[] createNaturalExamples() {
        return new ItemStackRep[]{createItemStack(1)};
    }

    /**
     * Should be overwritten to change which class represents the itemstack of this item - should not be called by user code
     * @param nmsItemStack the NMS itemstack
     * @return instance of T
     */
    protected OilItemStack createOilItemStackInstance(NMSItemStack nmsItemStack) {
        //noinspection unchecked
        return new OilItemStack(nmsItemStack, this); //this allows the user to change the class used for OilItemStacks
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
        OilItemStack result = createOilItemStackInstance(nmsItemStack);
        initStack(result);
        return result;
    }








}
