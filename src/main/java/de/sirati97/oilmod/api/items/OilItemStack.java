package de.sirati97.oilmod.api.items;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class OilItemStack {
    private NMSItemStack nmsItemStack;
    private OilItemBase item;

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
}
