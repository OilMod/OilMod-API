package org.oilmod.api.test;

import org.oilmod.api.OilMod;
import org.oilmod.api.rep.providers.ItemProvider;
import org.oilmod.api.rep.providers.minecraft.MinecraftItem;
import org.oilmod.api.unification.Standard;
import org.oilmod.api.unification.UniExpressibleRegistry;
import org.oilmod.api.unification.item.ItemExpressable;
import org.oilmod.api.unification.item.UniItem;
import org.oilmod.api.unification.item.UniItemBuilder;
import org.oilmod.api.unification.material.MaterialRequestBuilder;
import org.oilmod.api.unification.material.UniMaterial;
import org.oilmod.api.unification.material.UniMaterialRegistry;
import org.oilmod.api.unification.material.UniMaterialWrapper;

public class Test2Mod extends OilMod {

    @Override
    protected void onRegisterUnification(UniMaterialRegistry registry) {
        registry.register("intermediate", () -> new MaterialRequestBuilder().generalisation("metal").specialisation("alloy")); //todo this is supposed to be inserted between metal and alloy!
        //add special insert method? no because this needs to work agnostic of what other mods are doing

        registry.register("a1", (b) -> b.generalisation("a2", "b1"));
        registry.register("a2", (b) -> b.specialisation("a3"), (c)->a2=c);


        registry.register("b1", (b) -> b.generalisation("a2").element(), (c)->b1=c);
        registry.register("b2", (b) -> b.specialisation("b3"));
        registry.register("b3", (b) -> b.specialisation("a3"));
    }


    @Override
    protected void onRegisterUnification2(UniExpressibleRegistry registry) {
        registry.register("item_ingot2", ItemExpressable.class, UniItem.class, req -> req.builder(new UniItemBuilder(MinecraftItem.IRON_INGOT)).entryPoint(()->Standard.INGOT));
        System.out.println("b1 was set to: " + b1);
        System.out.println("a2 was set to: " + a2);
    }

    UniMaterial b1;
    UniMaterial a2;

    private static class TestUniItem extends UniItem{

        /**
         * @param vanillaItem The Vanilla Material that is shown to the client
         * @param displayName displayed displayName of the item
         * @param uniMaterial
         */
        public TestUniItem(ItemProvider vanillaItem, String displayName, UniMaterialWrapper uniMaterial) {
            super(vanillaItem, displayName, uniMaterial);
        }
    }
}
