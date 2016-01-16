package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.items.internal.ItemFactoryBase;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 15.01.2016.
 */
public class OilItemBase {
    private Material material;
    private int data;
    private int modData;
    private String name;
    private Object nmsItem;
    private int maxStackSize;


    public OilItemBase(Material material, int data, int modData, String name) {
        this(material, data, modData, 64, name);
    }

    public OilItemBase(Material material, int data, int modData, int maxStackSize, String name) {
        this.material = material;
        this.data = data;
        this.modData = modData;
        this.maxStackSize = maxStackSize;
        this.name = name;
    }

    public int getData() {
        return data;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName(Player player) {
        return name;
    }

    public String getName() {
        return name;
    }

    public final ItemStack createItemStack(Player player, int size) {
        return ItemFactoryBase.getInstance().createStack(this, player, size);
    }

    void setNmsItem(Object nmsItem) {
        this.nmsItem = nmsItem;
    }

    public Object getNmsItem() {
        return nmsItem;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getModData() {
        return modData;
    }

    public OilItemStack createOilStack(NMSItemStack nmsItemStack) {
        return new OilItemStack(nmsItemStack, this);
    }

    public boolean onUse(OilItemStack itemStack, Player player, Action action) {
        return false;
    }

    public boolean onUseOnBlock(OilItemStack itemStack, Player player, Action action, Block blockClicked, BlockFace blockFace) {
        return false;
    }
}
