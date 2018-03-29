package org.oilmod.api.items.type;

import gnu.trove.set.hash.THashSet;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

public abstract class ItemTypeHelper {

    private static ItemTypeHelper instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemTypeHelper instance) {
        if (ItemTypeHelper.instance == null) {
            synchronized (MUTEX) {
                if (ItemTypeHelper.instance == null) {
                    ItemTypeHelper.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemTypeHelper getInstance() {
        return instance;
    }


    public abstract boolean handleDamage(OilItemStack stack, int damage, LivingEntity entity);
    public abstract void damageItem(OilItemStack stack, int damage, LivingEntity entity);



    //
    private static final String UNSUPPORTED = "This ItemTypeHelper does not support this operation";
    public int getMaxStackSize() {return 64;}

}
