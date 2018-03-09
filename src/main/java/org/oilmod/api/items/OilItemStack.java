package org.oilmod.api.items;

import org.oilmod.api.inventory.ModInventoryObjectBase;
import org.oilmod.api.data.DataParent;
import org.oilmod.api.data.IData;
import gnu.trove.map.hash.THashMap;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class is used the handle special itemstack bound behavior.
 */
public class OilItemStack implements DataParent, InventoryHolder{
    private NMSItemStack nmsItemStack;
    private OilItem item;
    private final Map<String,IData<?>> registeredIData = new THashMap<>();
    private final Map<String,IData<?>> readonly_registeredIData = Collections.unmodifiableMap(registeredIData);
    private ModInventoryObjectBase mainInventory;
    private ItemDescription itemDescription;
    private boolean initiated = false;

    public OilItemStack(NMSItemStack nmsItemStack, OilItem item) {
        this.nmsItemStack = nmsItemStack;
        this.item = item;
    }

    /**
     * Internal - should not be called by user code
     */
    public void init() {
        if (initiated) {
            throw new IllegalStateException(getClass().getSimpleName() + " is has already been initiated");
        }
        initiated = true;
        List<String> description = createDescription();
        if (description != null) {
            itemDescription = new ItemDescription(createDescription(), nmsItemStack);
            itemDescription.init();
        }
    }

    public boolean onUse(Player player, Action action) {
        return false;
    }

    public boolean onUseOnBlock(Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
    }

    public boolean onLeftClick(Player player, Action action) {
        return false;
    }

    public boolean onLeftClickOnBlock(Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
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
    public ItemStack asBukkitItemStack() {
        return getNmsItemStack().asBukkitItemStack();
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
    public Inventory getInventory() {
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
     * Called after the item was cloned
     * @param original old/original itemstack
     */
    public void onCloned(OilItemStack original) {}

    /**
     *
     * @return returns the current display name
     */
    public String getCurrentDisplayName() {
        return getNmsItemStack().getDisplayName();
    }

    /**
     * sets the display name of the itemstack
     * @param name new name
     */
    public void setDisplayName(String name) {
        setDisplayName(name, false);
    }

    /**
     * sets the display name of the itemstack
     * @param name new name
     * @param renamed when true forces this name without applying prefixes
     */
    public void setDisplayName(String name, boolean renamed) {
        getNmsItemStack().setDisplayName(name, renamed);
    }
    /**
     * You do no need to override this method. just override getEnchantSelectModifier() and let it return a value bigger than 0
     * @return return whether the item is enchantable.
     */
    public boolean isEnchantable() {
        return getEnchantSelectModifier() > 0;
    }

    /**
     *
     * @return returns the enchantment level of the enchantment in question.
     */
    public int getEnchantmentLevel(Enchantment enchantment) {
        return getNmsItemStack().asBukkitItemStack().getEnchantmentLevel(enchantment);
    }

    /**
     *
     * @return returns whether the itemstack is enchanted with the enchantment in question
     */
    public boolean hasEnchantment(Enchantment enchantment) {
        return getEnchantmentLevel(enchantment) > 0;
    }
    /**
     * Override this method when allowing enchanting.
     * Standard implementation calls same-named method of the OilItem
     * @param enchantment the enchantment that is checked
     * @return returns whether the enchantment can be applied to this item
     */
    protected boolean canEnchant(Enchantment enchantment) {
        return getItem().canEnchant(enchantment);
    }

    /**
     *
     * @param enchantment the enchantment that is checked
     * @return returns whether the enchantment can be applied to this item
     */
    public final boolean canEnchantIntern(Enchantment enchantment) {
        if (isEnchantable() && getEnchantSelectModifier() < 1) {
            throw new IllegalStateException("EnchantSelectModifier is < 1 while item is enchantable");
        }
        return canEnchant(enchantment);
    }
    /**
     * Override this method and return a value greater than 0 to allow enchanting.
     * Standard implementation calls same-named method of the OilItem
     * @return return the enchant select modifier. An item with the same enchant select modifier and the same allowed enchantments will behave the same when enchanted with the same seed
     */
    public int getEnchantSelectModifier() {
        return getItem().getEnchantSelectModifier();
    }

    /**
     * Override this to change standard description. You need to override this or OilItem.getStandardDescription() to enable editing of ItemDescription
     * Standard implementation calls getStandardDescription() of the OilItem
     * @return returns the standard description of your itemstack
     */
    protected List<String> createDescription() {
        return getItem().getStandardDescription();
    }

    /**
     *
     * @return returns ItemDescription Object of this ItemStack. Used to manage description. Only available if createDescription() does not return null
     */
    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    /**
     *
     * @return returns whether the itemstack has a ItemDescription Object
     */
    public boolean hasItemDescription() {
        return itemDescription!=null;
    }

    /**
     * Override the change the name of the Itemstack arcourding to properties of the ItemStack
     * Standard implementation calls getDisplayName() of the OilItem
     * @return returns standard display name for this itemstack
     */
    public String createDisplayName() {
        return getItem().getDisplayName();
    }

    /**
     *
     * Standard implementation calls same-named method of the OilItem
     * @param itemStack The Itemstack is question to be used for repairing the OilItemStack
     * @param human The Human that is trying this
     * @return returns whether it is allowed to repair the OilItemStack with the itemstack
     */
    public boolean canRepairAnvil(ItemStack itemStack, HumanEntity human) {
        return getItem().canRepairAnvil(this, itemStack, human);
    }

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the repair.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a repair is really executed.
     * Standard implementation calls same-named method of the OilItem
     * @param itemStack The itemstack that is used for repairing
     * @param human The Human repairing the OilItemStack
     * @return how many of these items should be used
     */
    public int prepareRepairAnvil(ItemStack itemStack, HumanEntity human) {
        return getItem().prepareRepairAnvil(this, itemStack, human);
    }

    /**
     *
     * Standard implementation calls same-named method of the OilItem
     * @param itemStack The itemstack that is used for repairing
     * @param human The Human repairing the OilItemStack
     */
    public void repairAnvil(ItemStack itemStack, HumanEntity human) {
        getItem().repairAnvil(this, itemStack, human);
    }

    /**
     * This method is called when a player repairs the items in an anvil. It should do anything that could not be done in prepareRepairAnvil.
     * Standard implementation calls same-named method of the OilItem
     * @param itemStack The Itemstack is question to be combined with the OilItemStack
     * @param human The Human that is trying this
     * @return returns whether it is allowed to combine the itemstack with the OilItemStack
     */
    public boolean canCombineAnvil(ItemStack itemStack, HumanEntity human) {
        return getItem().canCombineAnvil(this, itemStack, human);
    }

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the combination.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a combination is really executed.
     * Standard implementation calls same-named method of the OilItem
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    public void prepareCombineAnvil(ItemStack itemStack, HumanEntity human) {
        getItem().prepareCombineAnvil(this, itemStack, human);
    }

    /**
     * This method is called when a player combines the items in an anvil. It should do anything that could not be done in prepareCombineAnvil.
     * Standard implementation calls same-named method of the OilItem
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    public void combineAnvil(ItemStack itemStack, HumanEntity human) {
        getItem().combineAnvil(this, itemStack, human);
    }
}
