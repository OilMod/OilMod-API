package org.oilmod.api.items.crafting;


import org.oilmod.api.rep.itemstack.ItemStackRep;

import java.util.Random;

/**
 * Created by sirati97 on 02.07.2016 for OilMod-Api.
 */
/*public class VanillaPotionIngredient extends VanillaIngredientBase {
    private final PotionType potionType;
    private final PotionUpgraded potionUpgraded;
    private final PotionExtended potionExtended;
    private final PotionBottleType[] potionBottleTypes;

    public VanillaPotionIngredient(PotionType potionType, PotionUpgraded potionUpgraded, PotionExtended potionExtended, PotionBottleType... potionBottleTypes) {
        this.potionType = potionType;
        this.potionUpgraded = potionUpgraded;
        this.potionExtended = potionExtended;
        this.potionBottleTypes = potionBottleTypes.length==0?new PotionBottleType[]{PotionBottleType.Normal}:potionBottleTypes;
        potionUpgraded.checkPotion(potionType);
        potionExtended.checkPotion(potionType);
    }

    @Override
    public boolean match(ItemStackRep itemStack, DataHolder dataHolder) {
        if (!matchMaterial(itemStack.getType())) {
            return false;
        }
        PotionData potionData = ((PotionMeta)itemStack.getItemMeta()).getBasePotionData();
        return potionUpgraded.checkPotionData(potionData) && potionExtended.checkPotionData(potionData);
    }

    private boolean matchMaterial(Material material) {
        for (PotionBottleType bottleType:potionBottleTypes) {
            if (material.equals(bottleType.getBukkitMaterial())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
        ItemStackRep itemStack = new ItemStack(potionBottleTypes[rnd.nextInt(potionBottleTypes.length)].getBukkitMaterial());
        PotionMeta meta = (PotionMeta)itemStack.getItemMeta();
        meta.setBasePotionData(new PotionData(potionType, potionExtended.generateExtended(rnd), potionUpgraded.generateUpgradeed(rnd)));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public enum PotionExtended {
        NotExtended,
        Extended,
        Both;

        public void checkPotion(PotionType potionType) {
            if (this!=NotExtended && !potionType.isExtendable()) {
                throw new IllegalStateException("Potion " + potionType.toString() + " is not extendable.");
            }
        }

        public boolean generateExtended(Random random) {
            switch (this) {
                case NotExtended:
                    return false;
                case Extended:
                    return true;
                case Both:
                    return random.nextBoolean();
                default:
                    throw new IllegalStateException("Unknown PotionExtended enum instance. This should not happen. Please report this bug!");
            }
        }

        public boolean checkPotionData(PotionData potionData) {
            switch (this) {
                case NotExtended:
                    return !potionData.isExtended();
                case Extended:
                    return potionData.isExtended();
                case Both:
                    return true;
                default:
                    throw new IllegalStateException("Unknown PotionExtended enum instance. This should not happen. Please report this bug!");
            }
        }
    }

    public enum PotionUpgraded {
        NotUpgraded,
        Upgraded,
        Both;

        public void checkPotion(PotionType potionType) {
            if (this!=NotUpgraded && !potionType.isUpgradeable()) {
                throw new IllegalStateException("Potion " + potionType.toString() + " is not upgradeable.");
            }
        }

        public boolean generateUpgradeed(Random random) {
            switch (this) {
                case NotUpgraded:
                    return false;
                case Upgraded:
                    return true;
                case Both:
                    return random.nextBoolean();
                default:
                    throw new IllegalStateException("Unknown PotionUpgraded enum instance. This should not happen. Please report this bug!");
            }
        }

        public boolean checkPotionData(PotionData potionData) {
            switch (this) {
                case NotUpgraded:
                    return !potionData.isUpgraded();
                case Upgraded:
                    return potionData.isUpgraded();
                case Both:
                    return true;
                default:
                    throw new IllegalStateException("Unknown PotionUpgraded enum instance. This should not happen. Please report this bug!");
            }
        }
    }


    public enum PotionBottleType {
        Normal(Material.POTION),
        Throwable(Material.SPLASH_POTION),
        Lingering(Material.LINGERING_POTION);

        private final Material bukkitMaterial;

        PotionBottleType(Material bukkitMaterial) {
            this.bukkitMaterial = bukkitMaterial;
        }

        public Material getBukkitMaterial() {
            return bukkitMaterial;
        }
    }
}*/ //TODO: readd
