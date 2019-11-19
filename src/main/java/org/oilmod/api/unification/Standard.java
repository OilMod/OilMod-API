package org.oilmod.api.unification;

import org.oilmod.api.OilMod;
import org.oilmod.api.unification.material.Category;
import org.oilmod.api.unification.material.MaterialBuilder;

public final class Standard {

    public static OilMod mod = OilMod.ModHelper.createInstance(OilMod.class, OilMod.ModHelper.getDefaultContext(),"unimod", "OilMod Unification");

    public static Category metal = new MaterialBuilder(mod).identifiers("metal").createCategory();
    public static Category metalPure = new MaterialBuilder(mod).identifiers("pure_metal").generalisation(metal).createCategory();
    public static Category metalAlloy = new MaterialBuilder(mod).identifiers("alloy").generalisation(metal).createCategory();


    public static Category ore = new MaterialBuilder(mod).identifiers("ore").createCategory();
    public static Category oreMetal = new MaterialBuilder(mod).identifiers("metal_ore").createCategory();


    public static Category treeSpecies = new MaterialBuilder(mod).identifiers("tree_species").createCategory();
}
