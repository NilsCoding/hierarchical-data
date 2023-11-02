package com.nilscoding.datastruct.hierarchical;

import java.util.List;

/**
 * Interface for accessing a hierarchical data element.
 * @param <K> key type
 * @param <T> element type
 * @author NilsCoding
 */
public interface IHierarchyDataAccessor<K, T> {

    /**
     * Returns the key from an object.
     * @param obj object to return key from
     * @return key
     */
    K accessKey(T obj);

    /**
     * Returns the parent key from an object.
     * @param obj object to return parent key from
     * @return parent key
     */
    K accessParentKey(T obj);

    /**
     * Returns the child object list from an object.
     * @param obj object to return child object list from
     * @return list with child objects, may be null
     */
    List<T> accessReadChildElements(T obj);

    /**
     * Sets the child object list to an object.
     * @param obj       object to set child object list to
     * @param childList list with child objects
     */
    void accessWriteChildElements(T obj, List<T> childList);

}
