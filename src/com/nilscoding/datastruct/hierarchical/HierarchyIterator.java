package com.nilscoding.datastruct.hierarchical;

import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator over a hierarchical object
 * @author NilsCoding
 * @param <T>   type of elements
 */
public class HierarchyIterator<T> implements Iterator<T> {

    protected Deque<Deque<T>> objStack = new LinkedList<>();
    protected IHierarchyDataAccessor<?,T> dataAccessor;
    
    public HierarchyIterator(T rootObj, IHierarchyDataAccessor<?,T> dataAccessor) {
        this.dataAccessor = dataAccessor;
        this.objStack.add(new LinkedList<>(Arrays.asList(rootObj)));
    }
    
    @Override
    public boolean hasNext() {
        Deque<T> lastStackElem = this.objStack.peekLast();
        if ((lastStackElem != null) && (lastStackElem.isEmpty() == false)) {
            return true;
        }
        return false;
    }

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
