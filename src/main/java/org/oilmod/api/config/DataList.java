package org.oilmod.api.config;

public interface DataList<Type> extends Iterable<DataIndexedEntry<Type>>{
    void append(Type type);
    void set(int index, Type type);
    Type get(int index);
    Type remove(int index);
	int size();
    DataType getType();
    Class<Type> getJavaType();
}
