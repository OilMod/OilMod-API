package de.sirati97.oilmod.api.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 12.03.2016.
 */
public class OilUtil {
    public static abstract class UtilImplBase {
        private static UtilImplBase instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(UtilImplBase instance) {
            if (UtilImplBase.instance == null) {
                synchronized (MUTEX) {
                    if (UtilImplBase.instance == null) {
                        UtilImplBase.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static UtilImplBase getInstance() {
            return instance;
        }

        protected abstract ItemStack[] getDrops(Block block);
        protected abstract ItemStack[] getDropsSilktouch(Block block);
        protected abstract ItemStack[] getDropsFortune(Block block, int level);
        protected abstract ItemStack getRandomValidVariation(Material mat, Random rnd);
        protected abstract boolean canBreak(Player player, Block block);
        protected abstract boolean canPlace(Player thePlayer, Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand);
    }

    public static ItemStack[] getDrops(Block block){
        return UtilImplBase.getInstance().getDrops(block);
    }

    public static ItemStack[] getDropsSilktouch(Block block){
        return UtilImplBase.getInstance().getDropsSilktouch(block);
    }

    public static ItemStack[] getDropsFortune(Block block, int level){
        return UtilImplBase.getInstance().getDropsFortune(block, level);
    }

    public static  ItemStack getRandomValidVariation(Material mat, Random rnd) {
        return UtilImplBase.getInstance().getRandomValidVariation(mat, rnd);
    }

    public static  boolean canBreak(Player player, Block block) {
        return UtilImplBase.getInstance().canBreak(player, block);
    }

    public static  boolean canPlace(Player thePlayer, Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand) {
        return UtilImplBase.getInstance().canPlace(thePlayer, placedBlock, replacedBlockState, placedAgainst, itemInHand);
    }

    public static ItemStack createItemStackNamed(Material material, String name) {
        return createItemStackNamed(material, 1, name);
    }

    public static ItemStack createItemStackNamed(Material material, String name, List<String> lore) {
        return createItemStackNamed(material, 1, name, lore);
    }

    public static ItemStack createItemStackNamed(Material material, int amount, String name) {
        return createItemStackNamed(material, amount, (short) 0, name);
    }

    public static ItemStack createItemStackNamed(Material material, int amount, String name, List<String> lore) {
        return createItemStackNamed(material, amount, (short) 0, name, lore);
    }

    public static ItemStack createItemStackNamed(Material material, int amount, short data, String name) {
        return createItemStackNamed(material, amount, data, name, null);
    }

    public static ItemStack createItemStackNamed(Material material, int amount, short data, String name, List<String> lore) {
        ItemStack result = new ItemStack(material, amount, data);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(name.startsWith("§")?name:("§f"+name));
        if (lore != null && lore.size() > 0) {
            meta.setLore(lore);
        }
        result.setItemMeta(meta);
        return result;
    }
}
