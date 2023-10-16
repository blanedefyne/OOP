package ru.nsu.zelenin;

import java.util.*;

/** Tree class implementing Iterable interface.
 *
 * @param <T> - generic
 */
public class Tree<T> implements Iterable<Tree<T>> {
    private T value;
    private Tree<T> parent;
    private final List<Tree<T>> children = new ArrayList<>();
    private boolean modified = false;

    /** Tree constructor - only sets a value.
     *
     * @param obj - some instance of T class
     */
    public Tree(T obj) {
        this.value = obj;
    }

    /** Method adds a T class object to a tree.
     *
     * @param child - some instance of T class
     * @return resulting Tree with added node
     */
    public Tree<T> addChild(T child) {
        this.modified = true; //if any subtree of a "main" tree is modified
        AllTreeIsModified(); //then the "main" tree itself is modified
        Tree<T> newTree = new Tree<>(child);
        newTree.parent = this;
        this.children.add(newTree);
        return newTree;
    }

    /** Method adds a subtree to a tree.
     *
     * @param subtree - some tree of the same type
     * @return resulting tree containing this subtree
     */
    public Tree<T> addChild(Tree<T> subtree) {
        this.modified = true;
        AllTreeIsModified();
        subtree.parent = this;
        this.children.add(subtree);
        return subtree;
    }

    /** Method removes a subtree or a node from the tree.
     *  (depending on what it was called from)
     */
    public void remove() {
        this.value = null;
        this.modified = true;
        AllTreeIsModified();
        if (this.parent != null) {
            this.parent.children.remove(this);
            this.parent = null;
        }
        this.children.clear();

    }

    /** Method helps checking if the tree is modified.
     * if some subtree of a tree is modified
     * method goes up to its parent and set parent.modified to true
     * and over again until the parent is null (what means we are in root)
     */
    private void AllTreeIsModified() {
        if (!this.modified) {
            return;
        }
        Tree<?> temp = this.parent;
        while (temp != null) {
            temp.modified = true;
            temp = temp.parent;
        }
    }

    /** Simple getter - gets a tree node value.
     *
     * @return value
     */
    public T getValue() {
        return value;
    }

    /** Simple setter - sets a tree node value.
     *
     * @param value - value we want to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /** equals() method override.
     * checking if first trees' list of children
     * contains all the second trees' children
     * and vice versa
     *
     * @param o -
     * @return true if trees are equal; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tree<?> tree = (Tree<?>) o;
        if (this.children.size() != tree.children.size()) {
            return false;
        }

        for (int i = 0; i < this.children.size(); ++i) {
            if (!(tree.children.contains(this.children.get(i)) &&
                    this.children.contains(tree.children.get(i)))) {
                return false;
            }
        }
        return Objects.equals(this.value, tree.value);// && children.equals(tree.children);
    }

    /** hashCode() method override.
     *
     * @return int - hashCode of a tree
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, children);
    }

    /** iterator() method override.
     * as we implemented Iterable interface
     * we need to override iterator() method
     *
     * @return Iterator of our tree
     */
    @Override
    public Iterator<Tree<T>> iterator() {
        return new TreeIterator();
    }

    /**
     * private TreeIterator class - default iteration is DFS-search.
     */
    private class TreeIterator implements Iterator<Tree<T>> {
        private int idx = 0;
        private final List<Tree<T>> order = new ArrayList<>();

        /** TreeIterator constructor.
         * only sets modified to false
         * and starts dfs method
         */
        public TreeIterator() {
            modified = false;
            dfs(Tree.this, 0);
        }

        /** dfs() method adds out tree subtrees in the right order.
         * just put every node in "order" ArrayList
         *
         * @param node - tree we run DFS on
         * @param i - just a helpful index
         */
        private void dfs(Tree<T> node, int i) {
            if (node.value == null) { // if value is empty - tree is empty
                return;
            }
            order.add(node);
            if (!node.children.isEmpty()) { // if children list is NOT empty
                dfs(node.children.get(0), 0); // we run from the first one
            }
            if (node.parent != null && node.parent.children.size() - 1 > i) {
                // if children list IS empty
                dfs(node.parent.children.get(++i), i);
                // we run from next children
            }

        }

        /** hasNext() method override.
         *
         * @return true if we have next element to iterate; false otherwise
         */
        @Override
        public boolean hasNext() {
            if (modified) {
                // if tree is modified
                throw new ConcurrentModificationException();
                // throw an exception
            }
            // just check if the index is not larger than order.size()
            return idx < order.size();
        }

        /** next() method override.
         *
         * @return next subtree
         */
        @Override
        public Tree<T> next() {
            if (!hasNext()) {
                // if it's called when hasNext() == false
                throw new NoSuchElementException();
            }
            if (modified) {
                throw new ConcurrentModificationException();
            }
            // just return next element of order list
            return order.get(idx++);
        }

    }

    /** iteratorBFS() - iteration is in BFS-search order.
     *
     * @return Iterator of our tree
     */
    public Iterator<Tree<T>> iteratorBFS() {
        return new TreeIteratorBFS();
    }

    /**
     * private TreeIteratorBFS class - so we can iterate using BFS.
     */
    private class TreeIteratorBFS implements Iterator<Tree<T>>{
        private final List<Tree<T>> order = new ArrayList<>();
        private int idx = 0;

        /** TreeIteratorBFS constructor.
         * only sets modified to false
         * and starts bfs method
         */
        public TreeIteratorBFS() {
            modified = false;
            bfs(Tree.this);
        }

        /** bfs() method adds the subtrees in the right order.
         *
         * @param node - tree we want to iterate
         */
        private void bfs(Tree<T> node) {
            if (node.value == null) {
                return;
            }
            order.add(node);
            while (idx < order.size()) {
                // i'm adding nodes in order of BFS-search without using queue
                // i use "order" arrayList as queue itself, only moving the "idx" cursor
                order.addAll(order.get(idx++).children);
            }
            idx = 0; // for next() and hasNext()

        }

        /** hasNext() method override.
         *
         * @return true if there is next element
         */
        @Override
        public boolean hasNext() {
            if (modified) {
                throw new ConcurrentModificationException();
            }
            return idx < order.size();
        }

        /** next() method override.
         *
         * @return next subtree
         */
        @Override
        public Tree<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (modified) {
                throw new ConcurrentModificationException();
            }
            return order.get(idx++);
        }

    }
}

