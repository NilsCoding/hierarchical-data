# Hierarchical Data

**Hierarchical Data** can build a hierarchical data structure on supporting objects.

If objects support access to a key, parent key and child objects of same type, and you provide an unsorted list of those objects, then **Hierarchical Data** can build a hierarchy by assigning objects to their parent.

## Prerequisites

* Java 8 or above
* no other libraries required

## Getting started

Let's assume you have a category object that has a key, parent key and a list of child categories, and also a name, something like this:

```java
import java.util.List;

/**
 * a simple category object
 */
public class Category {

    private int key;
    private int parentKey;
    private List<Category> children;
    private String name;

    public Category() {
    }

    public Category(int key, int parentKey, String name) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
    }

    @Override
    public String toString() {
        return "category{" + "key=" + key + ", parentKey=" + parentKey + ", name=" + name + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.key;
    }

    @Override
    public Integer getParentId() {
        return this.parentKey;
    }

    @Override
    public List<Category> getChildElements() {
        return this.children;
    }

    @Override
    public void setChildElements(List<Category> childElements) {
        this.children = childElements;
    }

}
```

Those objects might be selected from a database and to further use it in your program, you'd like to add each object to its parent object.

Using **Hierarchical Data** you can do this:

```java
List<Category> sourceCategoryList = new ArrayList<>();
// TODO add categories to that list, from a database or any other source

List<Category> categoryList = HierarchyBuilder.build(Category::getId, Category::getParentId, Category::getChildElements, Category::setChildElements, sourceCategoryList);
```

The resulting list will contain all root items, which will contain the assigned child categories.
The source category list does not need to provide the categories in a specific order because the implementation will process them in a way that handles the parent relations accordingly.

However, some things should be considered:

### Which elements become root elements?
Each element that has not been assigned to a parent element automatically becomes a root element. There might be more than one root element, e.g. if you provide data like this (pseudo data):
```json
[
  {"id" : 1, "parentId" : 1},
  {"id" : 2, "parentId" : 1},
  {"id" : 5, "parentId" : 5},
  {"id" : 7, "parentId" : 7}
]
```
In this case, the elements 1, 5 and 7 will become root elements because they cannot be assigned to a parent elements (only 2 will be assigned to 1).
To specify a root element, you can either return a parentId equal to its id or a non-existing parentId like -1

### How are id, parent id and child elements accessed?

Internally, implementations of `IHierarchyDataAccessor<K,T>` will be used. **Hierarchical Data** provides two ready-to-use implementations:
* `FunctionBasedHierarchicalDataAccessor<K,T>`
* `ImplBasedHierarchicalDataAccessor<K, T extends IHierarchicalData<K,T>>`

The first one has a static method `of(...)` which is given the `Function`s and a `BiConsumer` to access id, parent id and child elements, like in the example above.
If the method for retrieving the list of child elements always returns a writable list instance, then you don't need to provide the `BiConsumer` for setting a list.

The second one only works if the hierarchical objects implements `IHierarchicalData<K,T extends IHierarchicalData>` and therefore provide the methods to access all needed data itself.
If your object returns a writable list instance in `getChildElements()` then you don't need to really implement `setChildElements(List<T> l)` although you might do so for other reasons.

## Limitations

There are currently some limitations:

### No loop detection

Consider a list of categories like this:
```json
[
  {"id" : 1, "parentId" : 3},
  {"id" : 2, "parentId" : 1},
  {"id" : 3, "parentId" : 2}
]
```

While this data will not result in an endless loop while building the hierarchy, the result list will be empty. This is because the root element could not be determined.

After the first round of object assignments, the elements will chain up under 1 (1 <- 2 <- 3) and the next internal processing will then put 1 under 3, so a loop was built.

Currently, this cannot be easily detected and therefore no check has been implemented. In this case, no root can be determined because all elements have been assigned to a parent (see above: all non-assigned elements become root).
Maybe a fix to this will be added in the future, but this first implementation will not be able to process such data properly.

### No annotations to specify how an object is "hierarchical"

To specify how an object provides key, parent key and child element access, you either need to implement the `IHierarchicalData<K,T extends IHierarchicalData>` interface or provide the method references.

I would like to implement an Annotation-based solution to specify the access methods on a class, but unfortunately annotations cannot contain generics, so it is hard to find a proper way defining the annotation.

## License

**Hierarchical Data** is licensed unter the MIT license as stated in LICENSE.txt
