package org.oilmod.api.items.type;

public interface IAxe extends IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.AXE.getValue();}
}
