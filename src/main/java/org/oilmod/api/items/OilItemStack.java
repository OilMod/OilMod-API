package org.oilmod.api.items;

import gnu.trove.map.hash.THashMap;
import org.oilmod.api.data.DataParent;
import org.oilmod.api.data.IData;
import org.oilmod.api.inventory.ModInventoryObjectBase;
import org.oilmod.api.rep.enchant.EnchantmentRep;
import org.oilmod.api.rep.inventory.InventoryHolderRep;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.itemstack.state.Enchantments;

import java.util.Collections;
import java.util.Map;

/**
 * This class is used the handle special itemstack bound behavior.
 */
public class OilItemStack implements DataParent, InventoryHolderRep {
    private NMSItemStack nmsItemStack;
    private OilItem item;
    private final Map<String,IData<?>> registeredIData = new THashMap<>();
    private final Map<String,IData<?>> readonly_registeredIData = Collections.unmodifiableMap(registeredIData);
    private ModInventoryObjectBase mainInventory;
    private boolean initiated = false;

    public OilItemStack(NMSItemStack nmsItemStack, OilItem item) {
        this.nmsItemStack = nmsItemStack;
        this.item = item;
    }

    /**
     * Internal - should not be called by user code. Is called AFTER nms representation is initialised.
     */
    public void init() {
        if (initiated) {
            throw new IllegalStateException(getClass().getSimpleName() + " is has already been initiated");
        }
        initiated = true;
    }



    public int getData() {
        return getNmsItemStack().getDataNMS();
    }
    public void setData(int data) {
        getNmsItemStack().setDataNMS(data);
    }





    /**
     * Internal - should not be called by user code
     * @return returns the NMS ItemStack
     */
    public NMSItemStack getNmsItemStack() {
        return nmsItemStack;
    }

    /**
     *
     * @return returns the OilItem
     */
    public OilItem getItem() {
        return item;
    }

    /**
     *
     * @return returns the item as a Bukkit ItemStack
     */
    public ItemStackRep asBukkitItemStack() {
        return getNmsItemStack().asItemStackRep();
    }

    /**
     * Attaches an iData to the ItemStack. It value will be saved to nbt. You normally do not need to call this manually
     * @param iData the datatag that should be added
     */
    @Override
    public void registerIData(IData<?> iData) {
        registeredIData.put(iData.getName(),iData);
    }

    /**
     *
     * @return returns all attached iData mapped with there NBT-key
     */
    @Override
    public Map<String, IData<?>> getRegisteredIData() {
        return readonly_registeredIData;
    }

    /**
     *
     * @return returns the main inventory as a bukkit inventory
     */
    @Override
    public InventoryRep getInventory() {
        return mainInventory==null?null:mainInventory.getBukkitInventory();
    }

    /**
     * Sets the main inventory - You normally do not need to call this manually
     * @param mainInventory the new main inventory
     */
    public void setMainInventory(ModInventoryObjectBase mainInventory) {
        this.mainInventory = mainInventory;
    }

    /**
     *
     * @return returns the main inventory as a oil inventory
     */
    public ModInventoryObjectBase getMainInventory() {
        return mainInventory;
    }


    /**
     *
     * @return returns the enchantment level of the enchantment in question.
     */
    public int getEnchantmentLevel(EnchantmentRep enchantment) {
        return Enchantments.getEnchantmentLevel(getNmsItemStack().asItemStackRep(), enchantment);
    }

    /**
     *
     * @return returns whether the itemstack is enchanted with the enchantment in question
     */
    public boolean hasEnchantment(EnchantmentRep enchantment) {
        return getEnchantmentLevel(enchantment) > 0;
    }

    /**
     * Called after the item was cloned
     * @param original old/original itemstack
     */
    public void onCloned(OilItemStack original) {}

    /**
     * The new name of the item if it was renamed by the player
     * @return The new name or if not renamed null or empty string
     */
    public String getRename() {
        return getNmsItemStack().getRenameNMS();
    }

    public boolean isRenamed() {
        return getNmsItemStack().isRenamedNMS();
    }

    public void setRename(String name) {
        getNmsItemStack().setRenameNMS(name);
    }


}
