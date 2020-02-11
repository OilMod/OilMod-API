package org.oilmod.api.stateable.complex;

public interface NMSComplexStateType<TComplexState extends IComplexState> {
    TComplexState onCreate(TComplexState created);
}
