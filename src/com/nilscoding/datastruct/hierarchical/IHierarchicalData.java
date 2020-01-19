package com.nilscoding.datastruct.hierarchical;

import java.util.List;

/**
 * Interface for hierarchical data objects
 * @author NilsCoding
 * @param <K> key type
 * @param <T> own type
 */
public interface IHierarchicalData<K,T extends IHierarchicalData> {
    
    /**
     * Returns the ID of the object
     * @return  ID of object
     */
    public K getId();
    
    /**
     * Returns the parent ID of the object
     * @return  parent ID of the object
     */
    public K getParentId();
    
    /**
     * Returns all child elements for the object<br>
     * might return an empty list if the child list was null before
     * @return list with child elements
     */
    public List<T> getChildElements();
    
    /**
     * Sets the child elements, mainly used to set a newly created list with one element
     * @param childElements     child elements
     */
    public void setChildElements(List<T> childElements);
    
}
