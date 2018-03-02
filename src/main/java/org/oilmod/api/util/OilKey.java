package org.oilmod.api.util;

import org.oilmod.api.OilMod;

public class OilKey {
    private OilMod mod;
    private String keyString;
    private NMSMinecraftKey nmsMinecraftKey;

    private OilKey(OilMod mod, String keyString) {
        this.mod = mod;
        this.keyString = keyString;
    }

    /**
     *
     * @param mod Mod this key is associated to.
     * @param keyString Short unique string. Restricted to alphanumerical and underscores. Do not change it later, otherwise your plugin becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     * @return Returns an unique key.
     */
    public static OilKey create(OilMod mod, String keyString) {
        OilKey result = new OilKey(mod, keyString);
        result.nmsMinecraftKey = OilUtil.UtilImplBase.getInstance().registerOilKey(result);
        return result;
    }

    public OilMod getMod() {
        return mod;
    }

    public NMSMinecraftKey getNmsMinecraftKey() {
        return nmsMinecraftKey;
    }

    public String getKeyString() {
        return keyString;
    }

    @Override
    public String toString() {
        return mod.toString() + ":" + keyString;
    }
}
