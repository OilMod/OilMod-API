package org.oilmod.api.util;

import org.oilmod.api.OilMod;
import org.oilmod.api.rep.world.WorldRep;

public interface ITicker {
    boolean isRemote();
    WorldRep getMainWorld();
    void add(Tickable tickable);
    void remove(Tickable tickable);
    void resume();
    void pause();
    void stopAndDispose();
    int getTickRate();
    int getSimulationSpeed();
    OilMod getMod();
}
