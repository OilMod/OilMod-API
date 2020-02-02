package org.oilmod.api.stateable.complex;

import org.oilmod.api.stateable.IState;
import org.oilmod.api.stateable.IStateable;

import java.util.Set;
import java.util.function.Function;

public interface ICStateable<TSelf extends ICStateable<TSelf>> extends IStateable {
    Function<IState, IComplexState> getDefaultComplexState(IState state);
    ComplexStateFactoryStore<TSelf> getComplexStateFactoryStore();
    default Set<ComplexStateFactory<TSelf, ?>> getComplexStateFactories() {
        return getComplexStateFactoryStore().getRegisteredFactories();
    }

    default boolean hasComplexState(Class<? extends IComplexState> clazz) {
        for (ComplexStateFactory<TSelf, ?> factory:getComplexStateFactories()) {
            if (factory.getStateClass() == clazz)return true;
        }
        return false;
    }

    @Override
    default boolean hasComplexState(IState state) {
        return true;
    }

}
