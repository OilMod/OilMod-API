package org.oilmod.api.registry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.oilmod.api.OilMod;
import org.oilmod.api.util.OilKey;
import org.oilmod.spi.provider.ImplementationBase;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Internal - should not be called by user code
 */
public abstract class RegistryHelperBase<Type, TReg extends Registry<Type, TReg, MPI, IB>, MPI extends RegistryMPIBase<Type, TReg, MPI, IB>, IB extends RegistryHelperBase<Type, TReg, MPI, IB, ? extends IB>, Impl extends IB>  extends ImplementationBase<MPI, IB, Impl> {
    protected static final Map<Class<? extends Registry>, RegistryHelperBase> registries = new Object2ObjectOpenHashMap<>();
    private BiConsumer<OilMod, TReg> eventCaller;

    @Override
    public void onReady() {
        registries.put(getMpi().getRegistryClass(), this);
        eventCaller = OilMod.ModHelper.getEventCaller(getMpi().getRegistryClass());
    }

    public abstract <T extends Type> void register(OilKey key, TReg register, T entry);

    protected void initRegister(TReg register, InitRegisterCallback callback) {
        callback.callback(true, null);
    }

    protected abstract TReg create(OilMod mod);

    public BiConsumer<OilMod, TReg> getEventCaller() {
        return eventCaller;
    }


}
