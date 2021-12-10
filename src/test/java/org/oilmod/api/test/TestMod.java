package org.oilmod.api.test;

import javafx.scene.chart.CategoryAxis;
import org.oilmod.api.OilMod;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.rep.providers.ItemProvider;
import org.oilmod.api.unification.Standard;
import org.oilmod.api.unification.UniExpressibleRegistry;
import org.oilmod.api.unification.item.ItemExpressable;
import org.oilmod.api.unification.item.UniItem;
import org.oilmod.api.unification.item.UniItemBuilder;
import org.oilmod.api.unification.material.Category;
import org.oilmod.api.unification.material.MaterialRequestBuilder;
import org.oilmod.api.unification.material.UniMaterialRegistry;
import org.oilmod.api.unification.material.UniMaterialWrapper;

public class TestMod extends OilMod {

    @Override
    protected void onRegisterUnification(UniMaterialRegistry registry) {
        registry.register("intermediate", () -> new MaterialRequestBuilder().generalisation("metal").specialisation("alloy")); //todo this is supposed to be inserted between metal and alloy!

        registry.register("a1", (b) -> b.generalisation("a2", "b1"));
        //registry.register("a2", () -> new MaterialRequestBuilder().specialisation("a3"));


        registry.register("b1", (b) -> b.generalisation("a2").category(), (c)->b1=c);
        //registry.register("b2", () -> new MaterialRequestBuilder().specialisation("b3"));
        //registry.register("b3", () -> new MaterialRequestBuilder().specialisation("a3"));
    }


    @Override
    protected void onRegisterUnification2(UniExpressibleRegistry registry) {
        registry.register("item_ingot2", ItemExpressable.class, UniItem.class, req -> req.builder(new UniItemBuilder()).entryPoint(Standard.INGOT));
        System.out.println("b1 was set to: " + b1);
    }

    Category b1;

    private class TestUniItem extends UniItem{

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
