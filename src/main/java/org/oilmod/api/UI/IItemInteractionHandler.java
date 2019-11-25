package org.oilmod.api.UI;

import org.oilmod.api.rep.entity.EntityPlayerRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;

public interface IItemInteractionHandler {

    boolean isItemValid(IItemRef ref, ItemStackRep stack);


    /**
     * if par2 has more items than par1, onCrafting(item,countIncrease) is called
     */
    default void onSlotChange(IItemRef ref, ItemStackRep stack1, ItemStackRep stack2) {
        int i = stack2.getAmount() - stack1.getAmount();
        if (i > 0) {
            this.onCrafting(ref, stack2, i);
        }
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    void onCrafting(IItemRef ref, ItemStackRep stack, int amount);

    void onSwapCraft(IItemRef ref, int p_190900_1_);

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    void onCrafting(IItemRef ref, ItemStackRep stack);

    default ItemStackRep onTake(IItemRef ref, EntityPlayerRep thePlayer, ItemStackRep stack) {
        this.onSlotChanged(ref);
        return stack;
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    ItemStackRep getStack(IItemRef ref);

    /**
     * Returns if this slot contains a stack.
     */
    default boolean getHasStack(IItemRef ref) {
        return !this.getStack(ref).isEmpty();
    }

    /**
     * Helper method to put a stack in the slot.
     */
    void putStack(IItemRef ref, ItemStackRep stack);

    /**
     * Called when the stack in a Slot changes
     */
    void onSlotChanged(IItemRef ref);

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    int getSlotStackLimit(IItemRef ref);

    int getItemStackLimit(IItemRef ref, ItemStackRep stack);


    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
     */
    ItemStackRep decrStackSize(IItemRef ref, int amount);

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    boolean canTakeStack(IItemRef ref, EntityPlayerRep playerIn);

    /**
     * Actualy only call when we want to render the white square effect over the slots. Return always True, except for
     * the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
     */
    boolean isEnabled(IItemRef ref);


    void setNMS(Object nms);
    Object getNMS();

}
