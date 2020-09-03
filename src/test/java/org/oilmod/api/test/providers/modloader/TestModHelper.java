package org.oilmod.api.test.providers.modloader;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.test.TestMain;
import org.oilmod.api.test.TestMod;
import org.oilmod.api.unification.Standard;

public class TestModHelper extends OilMod.ModHelper<TestModHelper>{
    public static class OilModMinecraft extends OilMod {
        @Override
        public boolean isGame() {
            return true;
        }
    }
    public static class OilModOilForge extends OilMod {
        @Override
        public boolean isImplementation() {
            return true;
        }
    }

    @Override
    protected void register(OilMod mod) {
        super.register(mod);
        initialise(mod);
    }

    @Override
    public void onReady() {
        super.onReady();
        TestMain.ModMinecraft = createInstance(OilModMinecraft.class, getDefaultContext(),"minecraft", "Minecraft");
        TestMain.ModOilMod =  createInstance(OilModOilForge.class, getDefaultContext(),"oilmod", "OilMod");
        TestMain.ModTest =  createInstance(TestMod.class, getDefaultContext(),"test", "Debug Test Mod");
        TestMain.ModUniOil  = Standard.mod; //to load class
    }


    public static <T extends RegistryBase<?, T, ?, ?>> void invokeRegister(OilMod mod, Class<T> clazz) {
        OilMod.ModHelper.invokeRegister(mod, clazz);
    }

    @Override
    public void freeze() {
        super.freeze();
    }
}
