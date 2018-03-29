package org.oilmod.api.items;

import org.bukkit.inventory.ItemStack;
import org.oilmod.api.util.InteractionResult;

public class ItemInteractionResult {
    private final InteractionResult interactionResult;
    private final ItemStack itemStack;

    public ItemInteractionResult(InteractionResult interactionResult, ItemStack itemStack) {
        this.interactionResult = interactionResult;
        this.itemStack = itemStack;
    }
    public ItemInteractionResult(InteractionResult interactionResult, OilItemStack itemStack) {
        this(interactionResult, itemStack.asBukkitItemStack());
    }

    public InteractionResult getInteractionResult() {
        return interactionResult;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
