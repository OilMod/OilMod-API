package org.oilmod.api.util;

import org.oilmod.api.entity.NMSEntity;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 12.03.2016.
 */
public class OilUtil {

    public static abstract class UtilImpl {
        private static UtilImpl instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(UtilImpl instance) {
            if (UtilImpl.instance == null) {
                synchronized (MUTEX) {
                    if (UtilImpl.instance == null) {
                        UtilImpl.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static UtilImpl getInstance() {
            return instance;
        }

        protected abstract ItemStack[] getDrops(Block block);
        protected abstract ItemStack[] getDropsSilktouch(Block block);
        protected abstract ItemStack[] getDropsFortune(Block block, int level);
        protected abstract ItemStack getRandomValidVariation(Material mat, Random rnd);
        protected abstract boolean canBreak(Player player, Block block);
        protected abstract boolean canPlace(Player thePlayer, Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand);
        protected abstract boolean canMultiPlace(Player player, List<BlockState> states, Block clicked, ItemStack itemInHand);
        protected abstract  <T extends Entity> List<T> getNearbyEntities(Location loc1, Location loc2, Class<T> clazz);
        protected abstract void setLastDamager(LivingEntity entity, LivingEntity source);
        protected abstract boolean damageEntity(LivingEntity entity, double amount, LivingEntity source);
        protected abstract long getWorldTicksPlayed(World world);
        protected abstract  Class<? extends NMSEntity> getMappedNMSClass(Class<? extends Entity> bukkitClass);
        protected abstract NMSKey registerOilKey(OilKey key);
    }

    public static ItemStack[] getDrops(Block block){
        return UtilImpl.getInstance().getDrops(block);
    }

    public static ItemStack[] getDropsSilktouch(Block block){
        return UtilImpl.getInstance().getDropsSilktouch(block);
    }

    public static ItemStack[] getDropsFortune(Block block, int level){
        return UtilImpl.getInstance().getDropsFortune(block, level);
    }

    public static ItemStack getRandomValidVariation(Material mat, Random rnd) {
        return UtilImpl.getInstance().getRandomValidVariation(mat, rnd);
    }

    public static boolean canBreak(Player player, Block block) {
        return UtilImpl.getInstance().canBreak(player, block);
    }

    public static boolean canPlace(Player player, Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand) {
        return UtilImpl.getInstance().canPlace(player, placedBlock, replacedBlockState, placedAgainst, itemInHand);
    }


    public static boolean canMultiPlace(Player player, List<BlockState> states, Block clicked, ItemStack itemInHand) {
        return UtilImpl.getInstance().canMultiPlace(player, states, clicked, itemInHand);
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

    public static <T extends Entity> List<T> getNearbyEntities(Location loc1, Location loc2, Class<T> clazz) {
        return UtilImpl.getInstance().getNearbyEntities(loc1, loc2, clazz);
    }


    public static <T extends Entity> List<T> getNearbyEntities(Location loc, double aHalf, Class<T> clazz) {
        Location loc1 = loc.clone().subtract(aHalf, aHalf, aHalf);
        Location loc2 = loc.clone().add(aHalf, aHalf, aHalf);
        return getNearbyEntities(loc1, loc2, clazz);
    }

    public static void spawnParticleCloud(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
        location.getWorld().spigot().playEffect(location, effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, radius);
    }

    public static void spawnParticleCloud(Location location, ParticleSpawnData d) {
        spawnParticleCloud(location, d.getEffect(), d.getId(), d.getData(), d.getOffsetX(), d.getOffsetY(), d.getOffsetZ(), d.getSpeed(), d.getParticleCount(), d.getRadius());
    }

    public static void spawnParticleCloud(Location location, ParticleSpawnData d, int particleCount) {
        spawnParticleCloud(location, d.getEffect(), d.getId(), d.getData(), d.getOffsetX(), d.getOffsetY(), d.getOffsetZ(), d.getSpeed(), particleCount, d.getRadius());
    }

    public static void spawnParticleCloud(Location location, ParticleSpawnData d, Vector v) {
        spawnParticleCloud(location, d.getEffect(), d.getId(), d.getData(), (float) v.getX(), (float) v.getY(), (float) v.getZ(), d.getSpeed(), d.getParticleCount(), d.getRadius());
    }

    public static void spawnParticleCloud(Location location, ParticleSpawnData d, float offsetX, float offsetY, float offsetZ) {
        spawnParticleCloud(location, d.getEffect(), d.getId(), d.getData(), offsetX, offsetY, offsetZ, d.getSpeed(), d.getParticleCount(), d.getRadius());
    }

    public static void spawnParticleCloud(Location location, ParticleSpawnData d, Vector v, int particleCount) {
        spawnParticleCloud(location, d.getEffect(), d.getId(), d.getData(), (float) v.getX(), (float) v.getY(), (float) v.getZ(), d.getSpeed(), particleCount, d.getRadius());
    }

    public static void spawnParticleCloud(Location location, ParticleSpawnData d, float offsetX, float offsetY, float offsetZ, int particleCount) {
        spawnParticleCloud(location, d.getEffect(), d.getId(), d.getData(), offsetX, offsetY, offsetZ, d.getSpeed(), particleCount, d.getRadius());
    }


    public static void spawnParticleLine(Location location, ParticleSpawnData d) {
        spawnParticleLine(location, d, d.getOffsetAsVector());
    }

    public static void spawnParticleLine(Location location, ParticleSpawnData d, Vector v) {
        v = v.clone().multiply(1/(double)d.getParticleCount());
        location = location.clone();
        for (int i = 0; i < d.getParticleCount(); i++) {
            spawnParticleCloud(location, d.getEffect(), d.getId(), d.getData(), 0,0,0, d.getSpeed(), 1, d.getRadius());
            location.add(v);
        }
    }

    public static void setLastDamager(LivingEntity entity, LivingEntity source) {
        UtilImpl.getInstance().setLastDamager(entity, source);
    }

    public static boolean damageEntity(LivingEntity entity, double amount, LivingEntity source) {
        return UtilImpl.getInstance().damageEntity(entity, amount, source);
    }

    public static long getWorldTicksPlayed(World world) {
        return UtilImpl.getInstance().getWorldTicksPlayed(world);
    }

    public static boolean isSimilarEnchantmentTarget(EnchantmentTarget target1, EnchantmentTarget target2) {
        return isEnchantmentTargetChild(target1, target2) || isEnchantmentTargetChild(target2, target1);
    }

    public static boolean isEnchantmentTargetChild(EnchantmentTarget targetParent, EnchantmentTarget targetChild) {
        switch (targetParent) {
            case ALL:
                return true;
            case ARMOR:
                switch (targetChild) {
                    case ARMOR:
                    case ARMOR_FEET:
                    case ARMOR_LEGS:
                    case ARMOR_TORSO:
                    case ARMOR_HEAD:
                        return true;
                    default:
                        return false;
                }
            default:
                return targetParent.equals(targetChild);
        }
    }


    public Class<? extends NMSEntity> getMappedNMSClass(Class<? extends Entity> bukkitClass) {
        return UtilImpl.getInstance().getMappedNMSClass(bukkitClass);
    }
}
