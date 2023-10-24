package ru.nsu.zelenin;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


/** Tree class implementing Iterable interface.
 *
 * @param <T> - generic
 */
public class Tree<T> implements Iterable<Tree<T>> {
    private T value;
    private Tree<T> parent;
    private final List<Tree<T>> children = new ArrayList<>();
    private int modifiedCount = 0;

    /** Tree constructor - only sets a value.
     *
     * @param obj - some instance of T class
     */
    public Tree(T obj) {
        this.value = obj;
    }

    /** Method adds a T class object to a tree.
     * if any subtree of a "main" tree is modified
     * then the "main" tree itself is modified
     *
     * @param child - some instance of T class
     * @return resulting Tree with added node
     */
    public Tree<T> addChild(T child) {
        this.modifiedCount++;
        wholeTreeIsModified();
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
        this.modifiedCount++;
        wholeTreeIsModified();
        subtree.parent = this;
        this.children.add(subtree);
        return subtree;
    }

    /** Method removes a subtree or a node from the tree.
     *  (depending on what it was called from)
     */
    public void remove() {
        this.value = null;
        this.modifiedCount++;
        wholeTreeIsModified();
        if (this.parent != null) {
            this.parent.children.remove(this);
            this.parent = null;
        }
        this.children.clear();

    }

    /** Method helps checking if the tree is modified.
     * if some subtree of a tree is modified
     * method goes up to its parent and set parent.modifiedCount to parent.modifiedCount + 1
     * and over again until the parent is null (what means we are in root)
     */
    private void wholeTreeIsModified() {
        Tree<?> temp = this.parent;
        while (temp != null) {
            temp.modifiedCount++;
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
     * using HashSet containsAll - O(n) complexity
     *
     * @param o - (our second tree)
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
        return Objects.equals(this.value, tree.value)
                && new HashSet<>(tree.children).containsAll(this.children)
                && new HashSet<>(this.children).containsAll(tree.children);
    }

    /** hashCode() method override.
     *
     * @return int - hashCode of a tree
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, new HashSet<>(children));
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
        private final int modifiedSnapshot;
        private final List<Tree<T>> order = new ArrayList<>();

        /** TreeIterator constructor.
         * only sets modifiedSnapshot to our trees' modifiedCount
         * and starts dfs method
         */
        public TreeIterator() {
            modifiedSnapshot = Tree.this.modifiedCount;
            dfs(Tree.this, 0);
        }

        /** dfs() method adds out tree subtrees in the right order.
         * just put every node in "order" ArrayList
         * 1) if value is empty - tree is empty - otherwise, we run from first child
         * 2) if children list IS empty - we run from next children of our nodes' parent
         *
         * @param node - tree we run DFS on
         * @param i - just a helpful index
         */
        private void dfs(Tree<T> node, int i) {
            if (node.value == null) {
                return;
            }
            order.add(node);
            if (!node.children.isEmpty()) {
                dfs(node.children.get(0), 0);
            }
            if (node.parent != null && node.parent.children.size() - 1 > i) {
                dfs(node.parent.children.get(++i), i);
            }

        }

        /** hasNext() method override.
         *
         * @return true if we have next element to iterate; false otherwise
         */
        @Override
        public boolean hasNext() {
            if (modifiedSnapshot != modifiedCount) {
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
            if (modifiedSnapshot != modifiedCount) {
                throw new ConcurrentModificationException();
            }
            return order.get(idx++);
        }

    }

    /** iteratorBfs() - iteration is in BFS-search order.
     *
     * @return Iterator of our tree
     */
    public Iterator<Tree<T>> iteratorBfs() {
        return new TreeIteratorBfs();
    }

    /**
     * private TreeIteratorBfs class - so we can iterate using BFS.
     */
    private class TreeIteratorBfs implements Iterator<Tree<T>> {
        private final List<Tree<T>> order = new ArrayList<>();
        private final int modifiedSnapshot;
        private int idx = 0;

        /** TreeIteratorBfs constructor.
         * only sets modifiedSnapshot to our trees' modifiedCount
         * and starts bfs method
         */
        public TreeIteratorBfs() {
            modifiedSnapshot = Tree.this.modifiedCount;
            bfs(Tree.this);
        }

        /** bfs() method adds the subtrees in the right order.
         * adding nodes in order of BFS-search without queue
         * using "order" arrayList as queue itself, only moving the "idx" cursor
         *
         * @param node - tree we want to iterate
         */
        private void bfs(Tree<T> node) {
            if (node.value == null) {
                return;
            }
            order.add(node);
            while (idx < order.size()) {
                order.addAll(order.get(idx++).children);
            }
            idx = 0;

        }

        /** hasNext() method override.
         *
         * @return true if there is next element
         */
        @Override
        public boolean hasNext() {
            if (modifiedSnapshot != modifiedCount) {
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
            if (modifiedSnapshot != modifiedCount) {
                throw new ConcurrentModificationException();
            }
            return order.get(idx++);
        }

    }
}

