package de.sirati97.oilmod.api.config;

public interface CompoundCreator {
	/**
	 * Creates a new Compound.
	 * @return
     */
	Compound createCompound();

	/**
	 * Creates a new CompoundList
	 * @return
     */
	<Type> DataList<Type> createList(DataType type);
}
