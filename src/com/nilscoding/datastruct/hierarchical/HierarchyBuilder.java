package com.nilscoding.datastruct.hierarchical;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Hierarchy Builder
 * @author NilsCoding
 */
public class HierarchyBuilder {
    
    private HierarchyBuilder() {
        // prevent object creation
    }
    
    /**
     * Builds a hierarchy
     * @param <K>   key type
     * @param <T>   element type
     * @param objList   list with hierarchy-enabled elements
     * @param dataAccessor data accessor
     * @return  root list
     */
    public static <K,T> List<T> build(List<T> objList, IHierarchyDataAccessor<K,T> dataAccessor) {
        if ((objList == null) || (objList.isEmpty() == true) || (dataAccessor == null)) {
            return null;
        }
        
        Map<K,T> entryMap = new HashMap<>();
        
        Map<K,T> notAssignedToParent = new LinkedHashMap<>();
        List<T> toAssign = new LinkedList<>();
        for (T oneObj : objList) {
            K key = dataAccessor.accessKey(oneObj);
            entryMap.put(key, oneObj);
            toAssign.add(oneObj);
            notAssignedToParent.put(key, oneObj);
        }
        
        boolean atLeastOneElementAssigned;
        
        do {
            atLeastOneElementAssigned = false;
            for (ListIterator<T> itr = toAssign.listIterator(); itr.hasNext(); ) {
                T oneObj = itr.next();
                K key = dataAccessor.accessKey(oneObj);
                // store element to find it later by key
                entryMap.put(key, oneObj);
                // extract parent key
                K parentKey = dataAccessor.accessParentKey(oneObj);
                // find parent
                T parentObj = entryMap.get(parentKey);
                // parent must exist and it must not be a self-reference
                if ((parentObj != null) && (parentObj.equals(oneObj) == false)) {
                    // if found, then use its child method to insert element
                    List<T> childList = dataAccessor.accessReadChildElements(parentObj);
                    if (childList == null) {
                        childList = new LinkedList<>();
                        dataAccessor.accessWriteChildElements(parentObj, childList);
                    }
                    childList.add(oneObj);
                    // object was assigned
                    atLeastOneElementAssigned = true;
                    // remove element
                    itr.remove();
                    // remove from possible roots
                    notAssignedToParent.remove(key);
                }
            }
        } while ((atLeastOneElementAssigned == true) && (toAssign.isEmpty() == false));
        
        return new LinkedList<>(notAssignedToParent.values());        
    }
    
    /**
     * Builds a hierarchy
     * @param <K>   key type
     * @param <T>   element type
     * @param keyMethod method to access key
     * @param parentKeyMethod   method to access parent key
     * @param childReadMethod   method to read child elements
     * @param childWriteMethod  method to write child element list (mostly an empty one), optional if childReadMethod always returns a list
     * @param objList   list with elements
     * @return  root list
     */
    public static <K,T> List<T> build(Function<T,K> keyMethod, Function<T,K> parentKeyMethod, Function<T,List<T>> childReadMethod, BiConsumer<T,List<T>> childWriteMethod, List<T> objList) {
        if ((objList == null) || (objList.isEmpty() == true)) {
            return null;
        }
        IHierarchyDataAccessor<K,T> accessor = FunctionBasedHierarchicalDataAccessor.of(keyMethod, parentKeyMethod, childReadMethod, childWriteMethod);
        return build(objList, accessor);
    }
    
    /**
     * Builds a hierarchy
     * @param <K>   key type
     * @param <T>   element type
     * @param objList   list with hierarchy-enabled elements
     * @return  root list
     */
    public static <K,T extends IHierarchicalData<K,T>> List<T > build(List<T> objList) {
        if ((objList == null) || (objList.isEmpty() == true)) {
            return null;
        }
        IHierarchyDataAccessor<K,T> accessor = new ImplBasedHierarchicalDataAccessor<>();
        return build(objList, accessor);
    }
    
    
}
