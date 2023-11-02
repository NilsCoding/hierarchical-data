package com.nilscoding.datastruct.hierarchical;

import java.util.*;

/**
 * Iterator over a hierarchical object.
 * @param <T> type of elements
 * @author NilsCoding
 */
public class HierarchyIterator<T> implements Iterator<T> {

    /**
     * Object stack.
     */
    protected Deque<Deque<T>> objStack = new LinkedList<>();
    /**
     * Data accessor.
     */
    protected IHierarchyDataAccessor<?, T> dataAccessor;

    /**
     * Creates a new Iterator instance.
     * @param rootObj      root object
     * @param dataAccessor data accessor
     */
    public HierarchyIterator(T rootObj, IHierarchyDataAccessor<?, T> dataAccessor) {
        this.dataAccessor = dataAccessor;
        this.objStack.add(new LinkedList<>(Arrays.asList(rootObj)));
    }

    /**
     * Checks if the Iterator has a next element or not.
     * @return true if next elements exists, false if not
     */
    @Override
    public boolean hasNext() {
        Deque<T> lastStackElem = this.objStack.peekLast();
        if ((lastStackElem != null) && (lastStackElem.isEmpty() == false)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the next element, if available.
     * @return next element
     */
    @Override
    public T next() {
        if (this.hasNext() == false) {
            throw new NoSuchElementException();
        }
        // get last element stack
        Deque<T> lastStack = this.objStack.peekLast();
        if ((lastStack == null) || (lastStack.isEmpty())) {
            throw new NoSuchElementException();
        }
        T resultElem = lastStack.pollFirst();
        // append child elements of that element at the end of the main stack
        if (resultElem != null) {
            List<T> childElems = this.dataAccessor.accessReadChildElements(resultElem);
            if ((childElems != null) && (childElems.isEmpty() == false)) {
                this.objStack.offerLast(new LinkedList<>(childElems));
            }
        }
        // peek into last stack and remove it if its empty
        Deque<T> tmpLastStack = this.objStack.peekLast();
        if ((tmpLastStack == null) || (tmpLastStack.isEmpty() == true)) {
            this.objStack.pollLast();
        }

        return resultElem;
    }

}
