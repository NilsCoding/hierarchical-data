package com.nilscoding.datastruct.hierarchical;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Accessor using functions.
 * @param <K> key type
 * @param <T> element type
 * @author NilsCoding
 */
public final class FunctionBasedHierarchicalDataAccessor<K, T> implements IHierarchyDataAccessor<K, T> {

    /**
     * Function to get the key.
     */
    private Function<T, K> keyFnc;
    /**
     * Function to get the parent key.
     */
    private Function<T, K> parentKeyFnc;
    /**
     * Function to read the child elements.
     */
    private Function<T, List<T>> childReadFnc;
    /**
     * Function to write the child elements.
     */
    private BiConsumer<T, List<T>> childWriteFnc;

    private FunctionBasedHierarchicalDataAccessor() {
        // prevent object creation
    }

    /**
     * Creates a new accessor using given functions.
     * @param <K>           key type
     * @param <T>           element type
     * @param keyFnc        function to access key
     * @param parentKeyFnc  function to access parent key
     * @param childReadFnc  function to read children
     * @param childWriteFnc function to write children (optional if childReadFnc always returns a list)
     * @return instance of accessor
     */
    public static <K, T> FunctionBasedHierarchicalDataAccessor<K, T> of(Function<T, K> keyFnc,
                                                                        Function<T, K> parentKeyFnc,
                                                                        Function<T, List<T>> childReadFnc,
                                                                        BiConsumer<T, List<T>> childWriteFnc) {
        if ((keyFnc == null) || (parentKeyFnc == null) || (childReadFnc == null)) {
            return null;
        }
        FunctionBasedHierarchicalDataAccessor<K, T> fbhda = new FunctionBasedHierarchicalDataAccessor<>();
        fbhda.keyFnc = keyFnc;
        fbhda.parentKeyFnc = parentKeyFnc;
        fbhda.childReadFnc = childReadFnc;
        fbhda.childWriteFnc = childWriteFnc;
        return fbhda;
    }

    @Override
    public K accessKey(T obj) {
        return this.keyFnc.apply(obj);
    }

    @Override
    public K accessParentKey(T obj) {
        return this.parentKeyFnc.apply(obj);
    }

    @Override
    public List<T> accessReadChildElements(T obj) {
        return this.childReadFnc.apply(obj);
    }

    @Override
    public void accessWriteChildElements(T obj, List<T> childList) {
        if (this.childWriteFnc != null) {
            this.childWriteFnc.accept(obj, childList);
//        } else {
            // this should not happen because a write access will only be used
            // if the read method for child elements does not always return
            // a list
//        }
        }

    }
}
