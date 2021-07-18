package com.nilscoding.datastruct.hierarchical;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class to create a Stream on 
 * @author NilsCoding
 */
public class HierarchyStream {
    
    private HierarchyStream() { }
    
    /**
     * Creates a Stream of the iterable elements, starting at the given rootObj 
     * and iterating over all child elements, using the given dataAccessor
     * @param <T>   type of elements
     * @param rootObj   root object
     * @param dataAccessor  data accessor
     * @return  Stream to iterate over elements
     */
    public static <T> Stream<T> of(T rootObj, IHierarchyDataAccessor<?,T> dataAccessor) {
        Iterator<T> itr = new HierarchyIterator<>(rootObj, dataAccessor);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(itr, Spliterator.ORDERED), false);
    }
    
}
