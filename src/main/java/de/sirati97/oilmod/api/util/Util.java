package de.sirati97.oilmod.api.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 12.03.2016.
 */
public class Util {
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

}
