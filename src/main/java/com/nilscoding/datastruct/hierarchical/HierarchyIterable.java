package com.nilscoding.datastruct.hierarchical;

import java.util.Iterator;

/**
 * Iterable over a hierarchical object.
 * @param <T> type of elements
 * @author NilsCoding
 */
public class HierarchyIterable<T> implements Iterable<T> {

    /**
     * Root object.
     */
    protected T rootObj;
    /**
     * Data accessor.
     */
    protected IHierarchyDataAccessor<?, T> dataAccessor;

    /**
     * Creates a new Iterable instance.
     * @param rootObj      root object
     * @param dataAccessor data accessor
     */
    public HierarchyIterable(T rootObj, IHierarchyDataAccessor<?, T> dataAccessor) {
        this.rootObj = rootObj;
        this.dataAccessor = dataAccessor;
    }

    /**
     * Returns the Iterator.
     * @return Iterator instance
     */
    @Override
    public Iterator<T> iterator() {
        return new HierarchyIterator<>(this.rootObj, this.dataAccessor);
    }

}
