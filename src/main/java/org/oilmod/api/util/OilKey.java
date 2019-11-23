package org.oilmod.api.util;

import org.oilmod.api.OilMod;
import org.oilmod.api.rep.IKey;

import static org.oilmod.api.util.Util.checkName;

public class OilKey implements IKey, Cloneable {
    private OilMod mod;
    private String keyString;
    private String context;
    private NMSKey nmsKey;

    private OilKey(OilMod mod, String keyString) {
        this.mod = mod;
        this.keyString = keyString;
    }

    /**
     *
     * @param mod Mod this key is associated to.
     * @param keyString Short unique string. Restricted to lowercase alphanumerical characters and underscores. Do not change it later, otherwise your mod becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     * @return Returns an unique key.
     */
    public static OilKey create(OilMod mod, String keyString) {
        checkName(keyString);
        OilKey result = new OilKey(mod, keyString);
        result.nmsKey = OilUtil.UtilImpl.getInstance().registerOilKey(result);
        return result;
    }

    public OilMod getMod() {
        return mod;
    }

    public NMSKey getNmsKey() {
        return nmsKey;
    }

    @Override
    public String getNamespace() {
        return getMod().getInternalName();
    }

    public String getKeyString() {
        return keyString;
    }

    public String getContext() {
        return context;
    }

    public boolean isContextual() {
        return context!=null;
    }

    @Override
    public String toString() {
        return isContextual() ? (context + ".") + mod.toString() + ":" + keyString : "";
    }

    @Override
    public int hashCode() {
        return nmsKey.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OilKey && nmsKey.equals(((OilKey) obj).nmsKey);
    }

    public OilKey clone() {
        try {
            return (OilKey) super.clone();
        } catch (CloneNotSupportedException e) {throw new IllegalArgumentException(e);}
    }

    public OilKey makeContextual(String context) {
        if (context.equalsIgnoreCase(this.context))return this;
        checkName(context);
        OilKey result = this.clone();
        result.context = context;
        return result;
    }
}
