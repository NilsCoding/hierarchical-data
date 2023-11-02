package com.nilscoding.datastruct.hierarchical;

import java.util.List;

/**
 * Implementation-based accessor, where the element itself is a hierarchical data object.
 * @param <K> key type
 * @param <T> element type
 * @author NilsCoding
 */
public final class ImplBasedHierarchicalDataAccessor<K, T extends IHierarchicalData<K, T>>
        implements IHierarchyDataAccessor<K, T> {

    /**
     * Creates a new instance.
     */
    public ImplBasedHierarchicalDataAccessor() {
    }

    @Override
    public K accessKey(T obj) {
        return obj.getId();
    }

    @Override
    public K accessParentKey(T obj) {
        return obj.getParentId();
    }

    @Override
    public List<T> accessReadChildElements(T obj) {
        return obj.getChildElements();
    }

    @Override
    public void accessWriteChildElements(T obj, List<T> childList) {
        obj.setChildElements(childList);
    }

}
