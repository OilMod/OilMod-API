package org.oilmod.api.unification;

import org.oilmod.api.OilMod;
import org.oilmod.api.blocks.BlockRegistry;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.unification.material.Category;
import org.oilmod.api.unification.material.MaterialBuilder;
import org.oilmod.api.unification.material.MaterialRequestBuilder;
import org.oilmod.api.unification.material.UniMaterialRegistry;

public class UniMod extends OilMod {

    @Override
    protected void onRegisterBlocks(BlockRegistry registry) {
        super.onRegisterBlocks(registry);
    }

    @Override
    protected void onRegisterItems(ItemRegistry registry) {
        super.onRegisterItems(registry);
    }
    
    //(registry\.register\(\"(\w+)\"\, b\-\>b[ \",\.\(\)\w]*\.(\w+)\([ \",\.\(\)\w]*\))\)\;
    //$1, c->Standard.\U$2\L=c);

    @Override
    protected void onRegisterUnification(UniMaterialRegistry registry) {

        registry.register("metal", MaterialRequestBuilder::category, c->Standard.METAL=c);
        registry.register("pure_metal", b->b.generalisation("metal").category(), c->Standard.PURE_METAL=c);
        registry.register("alloy", b->b.generalisation("metal").category(), c->Standard.ALLOY=c);
        registry.register("iron", b->b.generalisation("pure_metal").element(), c->Standard.IRON=c);
        registry.register("copper", b->b.generalisation("pure_metal").element(), c->Standard.COPPER=c);
        registry.register("tin", b->b.generalisation("pure_metal").element(), c->Standard.TIN=c);
        registry.register("silver", b->b.generalisation("pure_metal").element(), c->Standard.SILVER=c);
        registry.register("gold", b->b.generalisation("pure_metal").element(), c->Standard.GOLD=c);
        registry.register("bronze", b->b.generalisation("alloy").constituents("tin", "copper"), c->Standard.BRONZE=c);
        registry.register("electrum", b->b.generalisation("alloy").constituents("silver", "gold"), c->Standard.ELECTRUM=c);


        registry.register("plate", MaterialRequestBuilder::category, c->Standard.PLATE=c);
        registry.register("plate_metal", b->b.generalisation("plate", "metal").category(), c->Standard.PLATE_METAL=c); //todo metal is not a generalisation but a variant supplier

        registry.register("stick", MaterialRequestBuilder::category, c->Standard.STICK=c);
        registry.register("stick_metal", b->b.generalisation("stick", "metal").category(), c->Standard.STICK_METAL=c);

        registry.register("ingot", MaterialRequestBuilder::category, c->Standard.INGOT=c);
        registry.register("ingot_metal", b->b.generalisation("ingot", "metal").category(), c->Standard.INGOT_METAL=c);

        registry.register("ore", MaterialRequestBuilder::category, c->Standard.ORE=c);
        registry.register("ore_metal", b->b.generalisation("ore", "metal").category(), c->Standard.ORE_METAL=c);




        registry.register("species_tree", MaterialRequestBuilder::category, c->Standard.SPECIES_TREE=c);
        registry.register("oak", b->b.generalisation("species_tree").category(), c->Standard.OAK=c);
        registry.register("birch", b->b.generalisation("species_tree").category(), c->Standard.BIRCH=c);
        registry.register("spruce", b->b.generalisation("species_tree").category(), c->Standard.SPRUCE=c);
        registry.register("jungle", b->b.generalisation("species_tree").category(), c->Standard.JUNGLE=c);
        registry.register("dark_oak", b->b.generalisation("species_tree").category(), c->Standard.DARK_OAK=c);
        registry.register("acacia", b->b.generalisation("species_tree").category(), c->Standard.ACACIA=c);
    }


}
