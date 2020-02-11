package org.oilmod.api.stateable.complex;

import org.oilmod.api.stateable.IState;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class ComplexStateFactory<TStateable extends ICStateable<TStateable>, TCState extends IComplexState> {
    private final TStateable stateable;
    private final Class<TCState> stateClass;
    private final BiFunction<TStateable, IState, TCState> factoryFunction;
    private final BiPredicate<TStateable, IState> validator;

    public ComplexStateFactory(TStateable stateable, Class<TCState> stateClass, BiFunction<TStateable, IState, TCState> factoryFunction, BiPredicate<TStateable, IState> validator) {
        this.stateable = stateable;
        this.stateClass = stateClass;
        this.factoryFunction = factoryFunction;
        this.validator = validator;
    }

    public TCState create(IState baseState) {
        return factoryFunction.apply(stateable, baseState);
    }

    public boolean applicable(IState baseState) {
        return validator.test(stateable, baseState);
    }

    public Class<TCState> getStateClass() {
        return stateClass;
    }
}
