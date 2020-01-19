package com.nilscoding.datastruct.hierarchical;

import java.util.List;

/**
 * Interface for accessing a hierarchical data element
 * @author NilsCoding
 * @param <K>   key type
 * @param <T>   element type
 */
public interface IHierarchyDataAccessor<K,T> {
    
    /**
     * Returns the key from an object
     * @param obj   object to return key from
     * @return  key
     */
    public K accessKey(T obj);
    
    /**
     * Returns the parent key from an object
     * @param obj   object to return parent key from
     * @return  parent key
     */
    public K accessParentKey(T obj);
    
    /**
     * Returns the child object list from an object
     * @param obj   object to return child object list from
     * @return  list with child objects, may be null
     */
    public List<T> accessReadChildElements(T obj);
    
    /**
     * Sets the child object list to an object
     * @param obj   object to set child object list to
     * @param childList     list with child objects
     */
    public void accessWriteChildElements(T obj, List<T> childList);
    
}
