package org.oilmod.api.registry.enumpop;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.util.OilKey;
import org.oilmod.spi.dependencies.DependencyPipe;

import java.util.EnumMap;

import static org.oilmod.util.ReflectionUtils.resolveGenericSuperClass;
import static org.oilmod.util.ReflectionUtils.resolveGenericSuperInterface;

public abstract class EnumPopRegistryHelperBase<
        Type extends IEnumPopType<Type, PopEnum, TReg, MPI, IB>,
        PopEnum extends Enum<PopEnum> & IEnumPopEnum<Type, PopEnum, TReg, MPI, IB>,
        TReg extends EnumPopRegistry<Type, PopEnum, TReg, MPI, IB>,
        MPI extends RegistryMPIBase<Type, TReg, MPI, IB>,
        IB extends EnumPopRegistryHelperBase<Type, PopEnum, TReg, MPI, IB, ? extends IB>,
        Impl extends IB>
        extends RegistryHelperBase<Type, TReg, MPI, IB, Impl> {

    private final EnumMap<PopEnum, Type> enumMap;
    private final Class<PopEnum> enumClass;

    public EnumPopRegistryHelperBase() {
        Class<?>[] generics = resolveGenericSuperClass(getClass(), EnumPopRegistryHelperBase.class, 0, 4);
        //noinspection unchecked
        this.enumClass = (Class<PopEnum>) generics[1];

        Class<?>[] enumGen = resolveGenericSuperInterface(enumClass, IEnumPopEnum.class, 0, 4);
        Validate.isTrue(generics[1] == enumGen[1], "Malformed generics, mpi does not match: %s vs %s", generics[1], enumGen[1]);
        enumMap = new EnumMap<>(enumClass);
    }

    @Override
    public void addDependencies(DependencyPipe p) {
        p.add(OilMod.ModHelper.class, true, true); //we need to be initialised after the Game mod was initialised
    }

    @Override
    protected final  <T extends Type> void onRegister(OilKey key, TReg registry, T entry) { //todo remove final after we did all the refactoring (final so we do not accidentally override the method and then have a hard time debugging)
        super.onRegister(key, registry, entry);
        if (!entry.getTypeEnum().isSpecial()) enumMap.put(entry.getTypeEnum(), entry);
        onRegister2(key, registry, entry); //todo remove (rename to onRegister)
    }


    protected <T extends Type> void onRegister2(OilKey key, TReg registry, T entry) {

    }

        @Override
    protected final void onUnregister(OilKey key, TReg registry, Type entry) {
        super.onUnregister(key, registry, entry);
        if (!entry.getTypeEnum().isSpecial()) enumMap.remove(entry.getTypeEnum());
    }

    public Type get(PopEnum key) {
        if (key.isSpecial())throw new IllegalArgumentException("Cannot get entry for non unique key: " + key.toString());
        return enumMap.get(key);
    }



    protected void autoRegister(TReg registry, Type entry) {
        registry.register(entry.getTypeEnum().toString().toLowerCase(), ()->entry);
    }

    protected TReg getGameRegistry() {
        return OilMod.ModHelper.getRegistry(OilMod.ModHelper.getGame(), getRegistryClass());
    }

    protected TReg getImplementationRegistry() {
        return OilMod.ModHelper.getRegistry(OilMod.ModHelper.getImplementation(), getRegistryClass());
    }

    protected abstract LazyResolver<Type, PopEnum> getLazyResolver();

    @Override
    public void onReady() {
        super.onReady();
        setSingleton();
        apiInit();
        getLazyResolver().resolveAll(enumClass.getEnumConstants());
        apiPostInit();
    }

    protected abstract void setSingleton();

    public Class<PopEnum> getEnumClass() {
        return enumClass;
    }

    protected void apiInit() {}
    protected void apiPostInit() {}
}
