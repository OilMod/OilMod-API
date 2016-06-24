package de.sirati97.oilmod.api.config;

public interface CompoundCreator {
	/**
	 * Creates a new Compound and adds it at the gives key. Keep in mind that you need to set the compound again after you changed something.
	 * @param key
	 * @return
     */
	Compound createCompound(String key);

	/**
	 * Creates a new CompoundList
	 * @return
     */
	<Type> List<Type> createList(DataType type);
}
