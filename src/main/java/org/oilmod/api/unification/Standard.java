package org.oilmod.api.unification;

import org.oilmod.api.OilMod;
import org.oilmod.api.unification.material.Category;
import org.oilmod.api.unification.material.MaterialBuilder;

public final class Standard {

    public static UniMod mod = OilMod.ModHelper.createInstance(UniMod.class, OilMod.ModHelper.getDefaultContext(),"oiluni", "OilMod Unification");

    public static Category metal = new MaterialBuilder(mod).identifiers("metal").createCategory();
    public static Category metalPure = new MaterialBuilder(mod).identifiers("pure_metal").generalisation(metal).createCategory();
    public static Category metalAlloy = new MaterialBuilder(mod).identifiers("alloy").generalisation(metal).createCategory();


    public static Category plate = new MaterialBuilder(mod).identifiers("plate").createCategory();
    public static Category metalPlate = new MaterialBuilder(mod).identifiers("metal_plate").specialisation(metal).generalisation(plate).createCategory();

    static {

    }

    //variantSupplier
    public static Category ore = new MaterialBuilder(mod).identifiers("ore").createCategory();
    public static Category oreMetal = new MaterialBuilder(mod).identifiers("metal_ore").specialisation(ore).createCategory();


    public static Category treeSpecies = new MaterialBuilder(mod).identifiers("tree_species").createCategory();
}
