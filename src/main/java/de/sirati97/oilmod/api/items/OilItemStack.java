package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.data.DataParent;
import de.sirati97.oilmod.api.data.IData;
import gnu.trove.set.hash.THashSet;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Set;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class OilItemStack implements DataParent, InventoryHolder{
    private NMSItemStack nmsItemStack;
    private OilItemBase item;
    private final Set<IData<?>> registeredIData = new THashSet<>();
    private final Set<IData<?>> readonly_registeredIData = Collections.unmodifiableSet(registeredIData);
    private Inventory mainInventory;
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
        registeredIData.add(iData);
    }

    @Override
    public Set<IData<?>> getRegisteredIData() {
        return readonly_registeredIData;
    }

    @Override
    public Inventory getInventory() {
        return mainInventory;
    }

    public void setMainInventory(Inventory mainInventory) {
        this.mainInventory = mainInventory;
    }
}
