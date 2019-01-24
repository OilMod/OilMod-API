package org.oilmod.api.items.type;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.oilmod.api.blocks.IBlockState;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.util.InteractionResult;

public interface IItemGeneric {

    default boolean onEntityHit(OilItemStack stack, LivingEntity target, LivingEntity attacker) {return true;}

    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, Location pos, LivingEntity entityLiving) {return true;}


    default InteractionResult onItemUseOnBlock(OilItemStack stack, HumanEntity human, Location pos, boolean offhand, BlockFace facing, float hitX, float hitY, float hitZ) {return InteractionResult.NONE;}

    default ItemInteractionResult onItemRightClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {return new ItemInteractionResult(InteractionResult.NONE, stack.asBukkitItemStack());}

    default boolean handleItemDamage(OilItemStack stack, int damage, LivingEntity entity) {return false;}

    default void damageItem(OilItemStack stack, int damage, LivingEntity entity) {
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
    default boolean isRepairable(OilItemStack toRepair, ItemStack repair) {return false;}


    //TODO fix this javadoc. fix ll the javadocs
    /**
     * Called when a preview of the result is needed. This code should under no circumstances have any side effects.
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the repair.
     * This should only include lightweight and fast algorithms. This method should not modify the ingredients in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a repair is really executed.
     * @param itemStack The ItemStack that is used for repairing
     * @param human The Human repairing the OilItemStack
     * @param oldDurability
     * @return how many of these items should be used
     */
    default int prepareRepairAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human, int usedItems, int oldDurability) {
        return usedItems;
    }

    /**
     *
     * @param itemStack The ItemStack that is used for repairing
     * @param human The Human repairing the OilItemStack
     */
    default void repairAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {}

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStack after the combination.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a combination is really executed.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    default int prepareCombineAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human, int cost) {return cost;}

    /**
     * This method is called when a player combines the items in an anvil. It should do anything that could not be done in prepareCombineAnvil.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    default void combineAnvil(OilItemStack oilItemStack, ItemStack itemStack, HumanEntity human) {}

    /**
     * Override this method to allow enchanting
     * @param enchantment the enchantment that is checked
     * @return returns whether the enchantment can be applied to this item
     */ //TODO: replace boolean with enum like class
    default boolean canEnchantSpecial(Enchantment enchantment, boolean anvil) {return false;}
}
