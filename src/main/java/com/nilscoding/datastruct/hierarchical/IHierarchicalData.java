package com.nilscoding.datastruct.hierarchical;

import java.util.List;

/**
 * Interface for hierarchical data objects.
 * @param <K> key type
 * @param <T> own type
 * @author NilsCoding
 */
public interface IHierarchicalData<K, T extends IHierarchicalData> {

    /**
     * Returns the ID of the object.
     * @return ID of object
     */
    K getId();

    /**
     * Returns the parent ID of the object.
     * @return parent ID of the object
     */
    K getParentId();

    /**
     * Returns all child elements for the object<br>
     * might return an empty list if the child list was null before.
     * @return list with child elements
     */
    List<T> getChildElements();

    /**
     * Sets the child elements, mainly used to set a newly created list with one element.
     * @param childElements child elements
     */
    void setChildElements(List<T> childElements);

}
