package ru.nsu.zelenin.graph;


import java.util.List;

/**
 * Graph interface.
 *
 * @param <V> - vertex type
 * @param <E> - edge weight type
 */
public interface Graph<V, E extends Number> {

    void addVertex(V value);

    void removeVertex(V value);

    void addEdge(V start, V end, E weight);

    void removeEdge(V start, V end);

    Edge<V, E> getEdge(V start, V end);

    Vertex<V> getVertex(V value);

    void setEdge(V start, V end, E newValue);

    void setVertex(V value, V newValue);

    int getEdgesCount();

    List<Vertex<V>> dijkstra(V start);

}