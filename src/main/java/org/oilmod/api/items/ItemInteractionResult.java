package org.oilmod.api.items;

import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.util.InteractionResult;

public class ItemInteractionResult {
    private final InteractionResult interactionResult;
    private final ItemStackRep itemStack;

    public ItemInteractionResult(InteractionResult interactionResult, ItemStackRep itemStack) {
        this.interactionResult = interactionResult;
        this.itemStack = itemStack;
    }
    public ItemInteractionResult(InteractionResult interactionResult, OilItemStack itemStack) {
        this(interactionResult, itemStack.asBukkitItemStack());
    }

    public InteractionResult getInteractionResult() {
        return interactionResult;
    }

    public ItemStackRep getItemStack() {
        return itemStack;
    }
}
