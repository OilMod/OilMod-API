package org.oilmod.api.test;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.test.providers.modloader.TestModHelper;
import org.oilmod.api.unification.UniExpressibleRegistry;
import org.oilmod.api.unification.material.UniMaterialRegistry;
import org.oilmod.spi.MPILoader;

public class TestMain {
    public static OilMod ModMinecraft;
    public static OilMod ModOilMod;
    public static OilMod ModUniOil;
    public static OilMod ModTest;

    public static void init() {
        MPILoader.init();
        ((TestModHelper) OilMod.ModHelper.getInstance()).freeze();
    }

    public static void main(String... args) {
        init();
        TestModHelper.invokeRegister(ModUniOil, UniMaterialRegistry.class);
        TestModHelper.invokeRegister(ModTest, UniMaterialRegistry.class);
        TestModHelper.invokeRegister(ModUniOil, UniExpressibleRegistry.class);
        TestModHelper.invokeRegister(ModTest, UniExpressibleRegistry.class);
    }
}
