package ru.nsu.zelenin.graph;

import java.util.Objects;

/**
 * Class for vertex representation.
 *
 * @param <V> - vertex type
 */
public class Vertex<V>{
    private V value;

    /**
     * Class constructor.
     *
     * @param value - vertex value
     */
    public Vertex(V value) {
        this.value = value;
    }

    /**
     * Simple setter.
     *
     * @param value - new value
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Simple getter.
     *
     * @return value of vertex
     */
    public V getValue() {
        return value;
    }

    /**
     * Override of equals method.
     *
     * @param o - given object
     * @return boolean - are equals or are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(value, vertex.value);
    }

    /**
     * hashCode() method override.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
