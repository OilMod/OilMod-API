package org.oilmod.api.items.crafting;

import org.oilmod.api.rep.item.BlockItemRep;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.util.OilUtil;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaMaterialIngredient extends VanillaIngredientBase {
    private final ItemRep material;

    public VanillaMaterialIngredient(ItemRep material) {
        this.material = material;
    }

    @Override
    public boolean match(ItemStackRep itemStack, DataHolder dataHolder) {
        return super.match(itemStack, dataHolder) && itemStack.getItem().equals(material);
    }

    @Override
    public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
        return OilUtil.getRandomValidVariation((BlockItemRep) material, rnd); //todo use new state system
    }
}
