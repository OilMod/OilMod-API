package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.inventory.ItemFilter;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-Api.
 */
public class SingleStackInteractableUIElement extends InteractableUIElementBase {
    private final ItemStackHolder itemStackHolder;
    private final ItemFilter itemFilter;
    private final int maxStackSize;

    public SingleStackInteractableUIElement(ItemStackHolder itemStackHolder, ItemFilter itemFilter, int maxStackSize) {
        this.itemStackHolder = itemStackHolder;
        this.itemFilter = itemFilter;
        this.maxStackSize = maxStackSize;
    }

    public SingleStackInteractableUIElement(ItemStackHolder itemStackHolder, ItemFilter itemFilter) {
        this(itemStackHolder, itemFilter, 64);
    }


    public SingleStackInteractableUIElement(ItemStackHolder itemStackHolder, int maxStackSize) {
        this(itemStackHolder, null, maxStackSize);
    }


    public SingleStackInteractableUIElement(ItemStackHolder itemStackHolder) {
        this(itemStackHolder, null);
    }

    @Override
    public boolean isItemstackAllowed(int index, ItemStack itemStack) {
        return itemFilter==null||itemFilter.allowed(itemStack);
    }

    @Override
    public int getMaxStackSize(int index) {
        return maxStackSize;
    }

    @Override
    public ItemStack getDisplayed(int index) {
        return itemStackHolder.getItemStack();
    }

    @Override
    public void setDisplayed(int index, ItemStack itemStack) {
        itemStackHolder.setItemStack(itemStack);
    }
}
