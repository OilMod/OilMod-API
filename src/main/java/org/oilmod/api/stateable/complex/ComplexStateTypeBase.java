package org.oilmod.api.stateable.complex;

public abstract class ComplexStateTypeBase<TComplexState extends IComplexState> implements IComplexStateType<TComplexState> {
    private NMSComplexStateType<TComplexState> nms;
    private final Class<TComplexState> stateClass;

    protected ComplexStateTypeBase(Class<TComplexState> stateClass) {
        this.stateClass = stateClass;
    }

    @Override
    public Class<TComplexState> getStateClass() {
        return stateClass;
    }

    @Override
    public NMSComplexStateType<TComplexState> getNms() {
        return nms;
    }

    @Override
    public void setNms(NMSComplexStateType<TComplexState> nms) {
        this.nms = nms;
    }
}
