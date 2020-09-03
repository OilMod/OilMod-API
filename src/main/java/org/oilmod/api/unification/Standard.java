package org.oilmod.api.unification;

import org.oilmod.api.OilMod;
import org.oilmod.api.unification.material.Category;
import org.oilmod.api.unification.material.Composite;
import org.oilmod.api.unification.material.Element;
import org.oilmod.api.unification.material.MaterialBuilder;

public final class Standard {

    //todo this is bad. because we create the mod when the static instance is created this is dependent on when the class is loaded the first time
    public static UniMod mod = OilMod.ModHelper.createInstance(UniMod.class, OilMod.ModHelper.getDefaultContext(),"oiluni", "OilMod Unification");

   
    //registry\.register\(\"(\w)(\w*)\"\, b\-\>b[ \",\.\(\)\w]*\.(\w)(\w*)\([ \",\.\(\)\w]*\)\)\;
    //public static \U$3\L$4 \U$1$2;


    
    public static Category METAL;
    public static Category PURE_METAL;
    public static Category ALLOY;
    public static Element IRON;
    public static Element COPPER;
    public static Element TIN;
    public static Element SILVER;
    public static Element GOLD;
    public static Composite BRONZE;
    public static Composite ELECTRUM;

    public static Category PLATE;
    public static Category PLATE_METAL;

    public static Category STICK;
    public static Category STICK_METAL;

    public static Category INGOT;
    public static Category INGOT_METAL;

    public static Category ORE;
    public static Category ORE_METAL;




    public static Category SPECIES_TREE;
    public static Category OAK;
    public static Category BIRCH;
    public static Category SPRUCE;
    public static Category JUNGLE;
    public static Category DARK_OAK;
    public static Category ACACIA;

}
