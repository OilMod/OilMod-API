package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.itemstack.ItemStackFactory;
import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaOilCraftingResult implements OilCraftingResult {
    private final ItemStackRep result;


    public VanillaOilCraftingResult(ItemStackRep result) {
        if (result instanceof OilBukkitItemStack) {
            throw new IllegalStateException("Itemstack is not a vanilla itemstack.");
        }
        this.result = result;
    }

    public VanillaOilCraftingResult(ItemRep material) {
        this(ItemStackFactory.INSTANCE.create(material));
    }

    public VanillaOilCraftingResult(ItemRep material, int amount) {
        this(ItemStackFactory.INSTANCE.create(material, amount));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStackRep preCraftResult(ItemStackRep[] matrix, boolean shaped, int width, int height) {
        return result.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void craftResult(ItemStackRep result, ItemStackRep[] matrix, boolean shaped, int width, int height) {}
}
