package org.oilmod.api.stateable.complex;

import org.oilmod.api.stateable.IState;
import org.oilmod.api.stateable.IStateable;
import org.oilmod.util.LamdbaCastUtils;

import java.util.Set;
import java.util.function.Function;

public interface ICStateable<TSelf extends ICStateable<TSelf>> extends IStateable {
    default <TCS extends IComplexState> Function<IState, TCS> getDefaultComplexState(IState state) {
        for (ComplexStateFactory<TSelf, ?> factory:getComplexStateFactories()) {
            if (factory.applicable(state)) {
                return LamdbaCastUtils.<ComplexStateFactory<TSelf, TCS>,ComplexStateFactory<TSelf, ?>>cast(factory)::create;
            }
        }
        throw new IllegalArgumentException(String.format("There is no complex state for %s", state));
    }

    ComplexStateFactoryStore<TSelf> getComplexStateFactoryStore();
    default Set<ComplexStateFactory<TSelf, ?>> getComplexStateFactories() {
        return getComplexStateFactoryStore().getRegisteredFactories();
    }

    default void init() {
        getComplexStateFactoryStore().init();
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
