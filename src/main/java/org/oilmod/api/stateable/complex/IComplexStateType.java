package org.oilmod.api.stateable.complex;

public interface IComplexStateType<TComplexState extends IComplexState> {
    TComplexState create();
    Class<TComplexState> getStateClass();
    NMSComplexStateType<TComplexState> getNms();
    void setNms(NMSComplexStateType<TComplexState> nms);
}
