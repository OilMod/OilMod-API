package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.data.DataParent;
import de.sirati97.oilmod.api.data.IData;
import de.sirati97.oilmod.api.inventory.ModInventoryObject;
import gnu.trove.map.hash.THashMap;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
    private ModInventoryObject mainInventory;
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

    public void setMainInventory(ModInventoryObject mainInventory) {
        this.mainInventory = mainInventory;
    }

    public ModInventoryObject getMainInventory() {
        return mainInventory;
    }
}
