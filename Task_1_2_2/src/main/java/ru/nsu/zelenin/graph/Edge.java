package ru.nsu.zelenin.graph;

/**
 * Class for edge representation.
 *
 * @param <V> - vertex type
 * @param <E> - edge type
 */
public class Edge<V, E extends Number> {
    private final Vertex<V> start;
    private final Vertex<V> end;
    private E weight;

    /**
     * Class constructor.
     *
     * @param start - start vertex
     * @param end - end vertex
     * @param weight - edge weight
     */
    public Edge(Vertex<V> start, Vertex<V> end, E weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    /**
     * Simple setter.
     *
     * @param value - new weight
     */
    public void setValue(E value) {
        this.weight = value;
    }

    /**
     * Simple getter.
     *
     * @return weight
     */
    public E getWeight() {
        return weight;
    }

    /**
     * Method returns negative E type weight considering which instance it is.
     *
     * @return negative E type weight
     */
    public E getNegWeight() {
        if (weight instanceof Integer) {
            return (E) Integer.valueOf(-weight.intValue());
        } else if (weight instanceof Double) {
            return (E) Double.valueOf(-weight.doubleValue());
        } else if (weight instanceof Float) {
            return (E) Float.valueOf(-weight.floatValue());
        } else if (weight instanceof Long) {
            return (E) Long.valueOf(-weight.longValue());
        }
        throw new IllegalArgumentException("Unknown type!");
    }

    /**
     * Simple getter.
     *
     * @return start vertex
     */
    public Vertex<V> getStart() {
        return start;
    }

    /**
     * Simple getter.
     *
     * @return end vertex
     */
    public Vertex<V> getEnd() {
        return end;
    }
}

