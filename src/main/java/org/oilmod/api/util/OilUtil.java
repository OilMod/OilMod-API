package org.oilmod.api.util;

import jdk.nashorn.internal.ir.Block;
import org.oilmod.api.OilMod;
import org.oilmod.api.entity.NMSEntity;
import org.oilmod.api.rep.block.BlockRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.entity.EntityRep;
import org.oilmod.api.rep.item.BlockItemRep;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.rep.world.LocationRep;
import org.oilmod.api.rep.world.WorldRep;

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

        protected abstract ItemStackRep[] getDrops(WorldRep w, BlockRep block, BlockStateRep state);
        protected abstract ItemStackRep[] getDropsSilktouch(WorldRep w, BlockRep block, BlockStateRep state);
        protected abstract ItemStackRep[] getDropsFortune(WorldRep w, BlockRep block, BlockStateRep state, int level);
        protected abstract ItemStackRep getRandomValidVariation(BlockItemRep mat, Random rnd);
        protected abstract boolean canBreak(EntityHumanRep player, BlockRep block, BlockStateRep state);
        protected abstract boolean canPlace(EntityHumanRep thePlayer, Block placedBlock, BlockStateRep replacedBlockState, LocationBlockRep placedAgainst, ItemStackRep itemInHand);
        protected abstract boolean canMultiPlace(EntityHumanRep player, List<BlockStateRep> states, LocationBlockRep clicked, ItemStackRep itemInHand);
        protected abstract  <T extends EntityRep> List<T> getNearbyEntities(LocationRep loc1, LocationRep loc2, Class<T> clazz);
        protected abstract void setLastDamager(EntityLivingRep entity, EntityLivingRep source);
        protected abstract boolean damageEntity(EntityLivingRep entity, double amount, EntityLivingRep source);
        protected abstract long getWorldTicksPlayed(WorldRep world);
        protected abstract Class<? extends NMSEntity> getMappedNMSClass(Class<? extends EntityRep> bukkitClass);
        protected abstract NMSKey registerOilKey(OilKey key);
        protected abstract void runTask(Runnable runnable);
        protected abstract void runTaskLater(Runnable runnable, long delay);
        protected abstract ITicker createTicker(OilMod mod, WorldRep mainWorld, int rate, int simulationSpeed);
    }

    public static ItemStackRep[] getDrops(WorldRep w, BlockRep block, BlockStateRep state){
        return UtilImpl.getInstance().getDrops(w, block, state);
    }

    public static ItemStackRep[] getDropsSilktouch(WorldRep w, BlockRep block, BlockStateRep state){
        return UtilImpl.getInstance().getDropsSilktouch(w, block, state);
    }

    public static ItemStackRep[] getDropsFortune(WorldRep w, BlockRep block, BlockStateRep state, int level){
        return UtilImpl.getInstance().getDropsFortune(w, block, state, level);
    }

    public static ItemStackRep getRandomValidVariation(BlockItemRep mat, Random rnd) {
        return UtilImpl.getInstance().getRandomValidVariation(mat, rnd);
    }

    public static boolean canBreak(EntityHumanRep player, BlockRep block, BlockStateRep state) {
        return UtilImpl.getInstance().canBreak(player, block, state);
    }

    public static boolean canPlace(EntityHumanRep player, Block placedBlock, BlockStateRep replacedBlockState, LocationBlockRep placedAgainst, ItemStackRep itemInHand) {
        return UtilImpl.getInstance().canPlace(player, placedBlock, replacedBlockState, placedAgainst, itemInHand);
    }


    public static boolean canMultiPlace(EntityHumanRep player, List<BlockStateRep> states, LocationBlockRep clicked, ItemStackRep itemInHand) {
        return UtilImpl.getInstance().canMultiPlace(player, states, clicked, itemInHand);
    }


    /*
    public static ItemStackRep createItemStackNamed(ItemRep material, String name) {
        return createItemStackNamed(material, 1, name);
    }

    public static ItemStackRep createItemStackNamed(ItemRep material, String name, List<String> lore) {
        return createItemStackNamed(material, 1, name, lore);
    }

    public static ItemStackRep createItemStackNamed(ItemRep material, int amount, String name) {
        return createItemStackNamed(material, amount, (short) 0, name);
    }

    public static ItemStackRep createItemStackNamed(ItemRep material, int amount, String name, List<String> lore) {
        return createItemStackNamed(material, amount, (short) 0, name, lore);
    }

    public static ItemStackRep createItemStackNamed(ItemRep material, int amount, short data, String name) {
        return createItemStackNamed(material, amount, data, name, null);
    }

    public static ItemStackRep createItemStackNamed(ItemRep material, int amount, short data, String name, List<String> lore) {
        ItemStackRep result = new ItemStack(material, amount, data);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(name.startsWith("§")?name:("§f"+name));
        if (lore != null && lore.size() > 0) {
            meta.setLore(lore);
        }
        result.setItemMeta(meta);
        return result;
    }*/

    public static <T extends EntityRep> List<T> getNearbyEntities(LocationRep loc1, LocationRep loc2, Class<T> clazz) {
        return UtilImpl.getInstance().getNearbyEntities(loc1, loc2, clazz);
    }


    public static <T extends EntityRep> List<T> getNearbyEntities(LocationRep loc, double aHalf, Class<T> clazz) {
        LocationRep loc1 = loc.toBuilder().subtract(aHalf).toLocation();
        LocationRep loc2 = loc.toBuilder().add(aHalf).toLocation();
        return getNearbyEntities(loc1, loc2, clazz);
    }

    /*
    public static void spawnParticleCloud(LocationRep location, ParticleRep effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
        location.getWorld().spigot().playEffect(location, effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, radius);
    }

    public static void spawnParticleCloud(LocationRep location, ParticleSpawnData d) {
        spawnParticleCloud(location, d.getParticle(), d.getId(), d.getData(), d.getOffsetX(), d.getOffsetY(), d.getOffsetZ(), d.getSpeed(), d.getParticleCount(), d.getRadius());
    }

    public static void spawnParticleCloud(LocationRep location, ParticleSpawnData d, int particleCount) {
        spawnParticleCloud(location, d.getParticle(), d.getId(), d.getData(), d.getOffsetX(), d.getOffsetY(), d.getOffsetZ(), d.getSpeed(), particleCount, d.getRadius());
    }

    public static void spawnParticleCloud(LocationRep location, ParticleSpawnData d, VectorRep v) {
        spawnParticleCloud(location, d.getParticle(), d.getId(), d.getData(), (float) v.getX(), (float) v.getY(), (float) v.getZ(), d.getSpeed(), d.getParticleCount(), d.getRadius());
    }

    public static void spawnParticleCloud(LocationRep location, ParticleSpawnData d, float offsetX, float offsetY, float offsetZ) {
        spawnParticleCloud(location, d.getParticle(), d.getId(), d.getData(), offsetX, offsetY, offsetZ, d.getSpeed(), d.getParticleCount(), d.getRadius());
    }

    public static void spawnParticleCloud(LocationRep location, ParticleSpawnData d, VectorRep v, int particleCount) {
        spawnParticleCloud(location, d.getParticle(), d.getId(), d.getData(), (float) v.getX(), (float) v.getY(), (float) v.getZ(), d.getSpeed(), particleCount, d.getRadius());
    }

    public static void spawnParticleCloud(LocationRep location, ParticleSpawnData d, float offsetX, float offsetY, float offsetZ, int particleCount) {
        spawnParticleCloud(location, d.getParticle(), d.getId(), d.getData(), offsetX, offsetY, offsetZ, d.getSpeed(), particleCount, d.getRadius());
    }


    public static void spawnParticleLine(LocationRep location, ParticleSpawnData d) {
        spawnParticleLine(location, d, d.getOffsetAsVectorRep());
    }

    public static void spawnParticleLine(LocationRep location, ParticleSpawnData d, VectorRep v) {
        VectorRep step = v.toBuilder().multiply(1/(double)d.getParticleCount()).toVector();
        LocBuilder current = location.toBuilder();
        for (int i = 0; i < d.getParticleCount(); i++) {
            spawnParticleCloud(current.toLocation(), d.getParticle(), d.getId(), d.getData(), 0,0,0, d.getSpeed(), 1, d.getRadius());
            current.add(v);
        }
    }*/ //todo readd

    public static void setLastDamager(EntityLivingRep entity, EntityLivingRep source) {
        UtilImpl.getInstance().setLastDamager(entity, source);
    }

    public static boolean damageEntity(EntityLivingRep entity, double amount, EntityLivingRep source) {
        return UtilImpl.getInstance().damageEntity(entity, amount, source);
    }

    public static long getWorldTicksPlayed(WorldRep world) {
        return UtilImpl.getInstance().getWorldTicksPlayed(world);
    }

    /*public static boolean isSimilarEnchantmentTarget(EnchantmentTarget target1, EnchantmentTarget target2) {
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
    }*/


    public static Class<? extends NMSEntity> getMappedNMSClass(Class<? extends EntityRep> bukkitClass) {
        return UtilImpl.getInstance().getMappedNMSClass(bukkitClass);
    }

    public static void runTask(Runnable runnable) {
        UtilImpl.getInstance().runTask(runnable);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        UtilImpl.getInstance().runTaskLater(runnable, delay);
    }

    public static ITicker createTicker(OilMod mod, WorldRep mainWorld, int rate, int simulationSpeed) {
        return UtilImpl.getInstance().createTicker(mod, mainWorld, rate, simulationSpeed);
    }
}
