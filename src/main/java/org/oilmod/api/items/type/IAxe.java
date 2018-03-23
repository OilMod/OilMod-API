package org.oilmod.api.items.type;

public interface IAxe extends IToolBlockBreaking {
    @Override
    default TBBType<? extends IAxe> getTbbType() {return TBBType.AXE;}
}
