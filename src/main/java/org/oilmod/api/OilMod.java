package org.oilmod.api;

import org.oilmod.api.util.OilKey;

public class OilMod {
    private final String internalName;
    private final String displayName;


    /**
     * Creates new instance of OilMod
     * @param internalName Short unique name. Restricted to alphanumerical and underscores. Do not change it later, otherwise your plugin becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     */
    public OilMod(String internalName) {
        this(internalName, internalName);
    }

    /**
     * Creates new instance of OilMod
     * @param internalName Short unique name. Restricted to alphanumerical and underscores. Do not change it later, otherwise your plugin becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     * @param displayName This is used rarely and only where things are presented pretty.
     */
    public OilMod(String internalName, String displayName) {
        this.internalName = internalName;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getInternalName() {
        return internalName;
    }

    @Override
    public String toString() {
        return internalName;
    }

    /**
     *
     * @param keyString Short unique string. Restricted to alphanumerical and underscores. Do not change it later, otherwise your plugin becomes incompatible with older versions of you mod. This will be used to identify content of this mod and will be visible to admins and players with cheat perms
     * @return Returns an unique key.
     */
    public OilKey createKey(String keyString) {
        return OilKey.create(this, keyString);
    }
}
