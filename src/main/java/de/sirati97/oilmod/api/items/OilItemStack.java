package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.data.DataParent;
import de.sirati97.oilmod.api.data.IData;
import de.sirati97.oilmod.api.inventory.ModInventoryObjectBase;
import gnu.trove.map.hash.THashMap;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Map;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class OilItemStack implements DataParent, InventoryHolder{
    private NMSItemStack nmsItemStack;
    private OilItemBase item;
    private final Map<String,IData<?>> registeredIData = new THashMap<>();
    private final Map<String,IData<?>> readonly_registeredIData = Collections.unmodifiableMap(registeredIData);
    private ModInventoryObjectBase mainInventory;
    public OilItemStack(NMSItemStack nmsItemStack, OilItemBase item) {
        this.nmsItemStack = nmsItemStack;
        this.item = item;
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

    public NMSItemStack getNmsItemStack() {
        return nmsItemStack;
    }

    public OilItemBase getItem() {
        return item;
    }

    public ItemStack asBukkitItemStack() {
        return getNmsItemStack().asBukkitItemStack();
    }

    @Override
    public void registerIData(IData<?> iData) {
        registeredIData.put(iData.getName(),iData);
    }

    @Override
    public Map<String, IData<?>> getRegisteredIData() {
        return readonly_registeredIData;
    }

    @Override
    public Inventory getInventory() {
        return mainInventory==null?null:mainInventory.getBukkitInventory();
    }

    public void setMainInventory(ModInventoryObjectBase mainInventory) {
        this.mainInventory = mainInventory;
    }

    public ModInventoryObjectBase getMainInventory() {
        return mainInventory;
    }

    public void onCloned(OilItemStack original) {}

    public String getDisplayName() {
        return getNmsItemStack().getDisplayName();
    }

    public void setDisplayName(String name) {
        getNmsItemStack().setDisplayName(name);
    }

    public boolean isEnchantable() {
        return getEnchantSelectModifier() > 0;
    }

    public int getEnchantmentLevel(Enchantment enchantment) {
        return getNmsItemStack().asBukkitItemStack().getEnchantmentLevel(enchantment);
    }

    public boolean hasEnchantment(Enchantment enchantment) {
        return getEnchantmentLevel(enchantment) > 0;
    }

    protected boolean canEnchant(Enchantment enchantment) {
        return getItem().canEnchant(enchantment);
    }

    public final boolean canEnchantIntern(Enchantment enchantment) {
        if (isEnchantable() && getEnchantSelectModifier() < 1) {
            throw new IllegalStateException("EnchantSelectModifier is < 1 while item is enchantable");
        }
        return canEnchant(enchantment);
    }

    public int getEnchantSelectModifier() {
        return getItem().getEnchantSelectModifier();
    }
}
