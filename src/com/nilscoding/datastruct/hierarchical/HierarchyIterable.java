package com.nilscoding.datastruct.hierarchical;

import java.util.Iterator;

/**
 * Iterable over a hierarchical object
 * @author NilsCoding
 * @param <T>   type of elements
 */
public class HierarchyIterable<T> implements Iterable<T> {

    protected T rootObj;
    protected IHierarchyDataAccessor<?,T> dataAccessor;

    public HierarchyIterable(T rootObj, IHierarchyDataAccessor<?, T> dataAccessor) {
        this.rootObj = rootObj;
        this.dataAccessor = dataAccessor;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new HierarchyIterator<>(this.rootObj, this.dataAccessor);
    }
    
}
