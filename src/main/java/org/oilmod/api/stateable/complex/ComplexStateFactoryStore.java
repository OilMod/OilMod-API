package org.oilmod.api.stateable.complex;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.oilmod.api.stateable.IState;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static org.oilmod.util.Strings.simpleName;

public class ComplexStateFactoryStore<TStateable extends ICStateable> {
    private final Set<ComplexStateFactory<TStateable, ?>> registeredFactories = new ObjectOpenHashSet<>();
    private final Set<ComplexStateFactory<TStateable, ?>> readonly_registeredFactories = Collections.unmodifiableSet(registeredFactories);
    private final TStateable stateable;
    private boolean initiated = false;

    public ComplexStateFactoryStore(TStateable stateable) {
        this.stateable = stateable;
    }

    public final void init() {
        if (initiated) {
            throw new IllegalStateException(simpleName(getClass()) + " is has already been initiated");
        }
        onInit();
        initiated = true;
    }

    protected void onInit() {      }


    public <TCState extends IComplexState> void registerComplexState(Class<TCState> stateClass, BiFunction<TStateable, IState, TCState> factoryFunction, BiPredicate<TStateable, IState> validator) {
        registerComplexState(new ComplexStateFactory<TStateable, TCState>((Class<TStateable>) stateable.getClass(), stateClass, factoryFunction, validator));

    }
    public <TCState extends IComplexState> void registerComplexState(ComplexStateFactory<TStateable, TCState> factory) {
        if (initiated)throw new IllegalStateException("Cannot register new ComplexStateFactory after initialisation!");
        registeredFactories.add(factory);
    }

    public Set<ComplexStateFactory<TStateable, ?>> getRegisteredFactories() {
        return readonly_registeredFactories;
    }
}
