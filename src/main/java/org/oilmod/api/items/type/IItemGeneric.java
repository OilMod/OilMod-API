package org.oilmod.api.items.type;

import org.oilmod.api.blocks.IBlockState;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.enchant.EnchantmentRep;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.rep.world.WorldRep;
import org.oilmod.api.util.InteractionResult;

public interface IItemGeneric {

    default boolean onEntityHit(OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker) {return true;}

    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, LocationBlockRep pos, EntityLivingRep entityLiving) {return true;}


    default InteractionResult onItemUseOnBlock(OilItemStack stack, EntityHumanRep human, LocationBlockRep loc, boolean offhand, BlockFaceRep blockFace, float hitX, float hitY, float hitZ) {return InteractionResult.NONE;}
    default InteractionResult onItemLeftClickOnBlock(OilItemStack stack, EntityHumanRep human, LocationBlockRep loc, boolean offhand, BlockFaceRep blockFace, float hitX, float hitY, float hitZ) {return InteractionResult.NONE;}

    default ItemInteractionResult onItemRightClick(OilItemStack stack, WorldRep world, EntityHumanRep human, boolean offhand) {return new ItemInteractionResult(InteractionResult.NONE, stack.asBukkitItemStack());}
    default ItemInteractionResult onItemLeftClick(OilItemStack stack, WorldRep world, EntityHumanRep human, boolean offhand) {return new ItemInteractionResult(InteractionResult.NONE, stack.asBukkitItemStack());}

    default boolean handleItemDamage(OilItemStack stack, int damage, EntityLivingRep entity) {return false;}

    default void damageItem(OilItemStack stack, int damage, EntityLivingRep entity) {
        return;
    }

    default int getItemDamage(OilItemStack stack) {
        return 0;
    }

    /**
     *
     * @return returns the maximal stack size of this item
     */
    default int getMaxStackSize() {
        return 64;
    }

    /**
     * reaches normally from 5 (stone) to 22 (gold). Higher value gives better enchantments
     * @return
     */
    default int getItemEnchantability() {return 0;}

    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.NONE;
    }

    /**
     * Repairable materials might be used for crafting, anvils etc. this is for vanilla like behavior
     * */
    default boolean isRepairable(OilItemStack toRepair, ItemStackRep repair) {return false;}


    //TODO fix this javadoc. fix ll the javadocs
    /**
     * Called when a preview of the result is needed. This code should under no circumstances have any side effects.
     * Should modify the OilItemStack in a way that it looks visually like the ItemStackRep after the repair.
     * This should only include lightweight and fast algorithms. This method should not modify the ingredients in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a repair is really executed.
     * @param itemStack The ItemStackRep that is used for repairing
     * @param human The Human repairing the OilItemStack
     * @param oldDurability
     * @return how many of these items should be used
     */
    default int prepareRepairAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human, int usedItems, int oldDurability) {
        return usedItems;
    }

    /**
     *
     * @param itemStack The ItemStackRep that is used for repairing
     * @param human The Human repairing the OilItemStack
     */
    default void repairAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human) {}

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStackRep after the combination.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a combination is really executed.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    default int prepareCombineAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human, int cost) {return cost;}

    /**
     * This method is called when a player combines the items in an anvil. It should do anything that could not be done in prepareCombineAnvil.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    default void combineAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human) {}

    /**
     * Override this method to allow enchanting
     * @param enchantment the enchantment that is checked
     * @return returns whether the enchantment can be applied to this item
     */ //TODO: replace boolean with enum like class
    default boolean canEnchantSpecial(EnchantmentRep enchantment, boolean anvil) {return false;}
}
