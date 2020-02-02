package org.oilmod.api.stateable.complex;

import org.oilmod.api.stateable.IState;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class ComplexStateFactory<TStateable extends ICStateable, TCState extends IComplexState> {
    private final Class<TStateable> stateableClass;
    private final Class<TCState> stateClass;
    private final BiFunction<TStateable, IState, TCState> factoryFunction;
    private final BiPredicate<TStateable, IState> validator;

    public ComplexStateFactory(Class<TStateable> stateableClass, Class<TCState> stateClass, BiFunction<TStateable, IState, TCState> factoryFunction, BiPredicate<TStateable, IState> validator) {
        this.stateableClass = stateableClass;
        this.stateClass = stateClass;
        this.factoryFunction = factoryFunction;
        this.validator = validator;
    }

    public TCState create(TStateable stateable, IState baseState) {
        return factoryFunction.apply(stateable, baseState);
    }

    public boolean applicable(TStateable stateable, IState baseState) {
        return validator.test(stateable, baseState);
    }

    public Class<TStateable> getStateableClass() {
        return stateableClass;
    }

    public Class<TCState> getStateClass() {
        return stateClass;
    }
}
