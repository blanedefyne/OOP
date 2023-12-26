package ru.nsu.zelenin.graph;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Class for representation graphs using adjacency list.
 *
 * @param <V> - vertex type
 * @param <E> - edge weight numeric type
 */
public class AdjacencyListGraph<V, E extends Number>
        extends MainGraph<V, E> {

    private int edgesCount = 0;
    private final Map<Vertex<V>, List<Edge<V, E>>> lists = new HashMap<>();

    /**
     * Class constructor.
     *
     * @param vertices - list of vertices
     * @param edges - list of edges
     */
    public AdjacencyListGraph(List<Vertex<V>> vertices, List<Edge<V, E>> edges) {
        super();
        for (var v : vertices) {
            lists.put(v, new ArrayList<>());
            verticesMap.put(v.getValue(), v);
        }

        for (var e : edges) {
            edgesCount++;
            var st = e.getStart();
            lists.get(st).add(e);
        }
    }

    /**
     * Methods adds a vertex to graph.
     *
     * @param value - value of a vertex
     */
    @Override
    public void addVertex(V value) {
        lists.put(getVertex(value), new ArrayList<>());
        verticesMap.put(value, new Vertex<>(value));
    }

    /**
     * Method removes a vertex from graph.
     *
     * @param value - value of a vertex
     */
    @Override
    public void removeVertex(V value) {
        var temp = getVertex(value);
        int deletedEdges = (int) lists.get(temp).stream()
                        .filter(Objects::nonNull)
                        .count();
        edgesCount -= deletedEdges;
        lists.remove(temp);
        verticesMap.remove(value);
        for (var vertex : lists.keySet()) {
            List<Edge<V, E>> edgesToRemove = lists.get(vertex)
                    .stream()
                    .filter(edge -> edge.getEnd().equals(temp)
                    || edge.getStart().equals(temp))
                    .toList();

            edgesCount -= edgesToRemove.size();
            lists.get(vertex).removeAll(edgesToRemove);
        }
    }

    /**
     * Method adds an edge to graph.
     *
     * @param start - start vertex
     * @param end - end vertex
     * @param weight - weight value
     */
    @Override
    public void addEdge(V start, V end, E weight) {
        if (!verticesMap.containsKey(start)) {
            verticesMap.put(start, new Vertex<>(start));
        }
        if (!verticesMap.containsKey(end)) {
            verticesMap.put(end, new Vertex<>(end));
        }
        var startVertex = getVertex(start);
        var endVertex = getVertex(end);
        if (!lists.containsKey(startVertex)) {
            addVertex(startVertex.getValue());
        }
        lists.get(startVertex).add(new Edge<>(startVertex, endVertex, weight));
        edgesCount++;
    }

    /**
     * Method removes an edge from graph.
     *
     * @param start - start vertex
     * @param end - end vertex
     */
    @Override
    public void removeEdge(V start, V end) {
        var stVertex = getVertex(start);
        lists.get(stVertex).removeIf(edge -> edge.getEnd().getValue() == end);
        edgesCount--;
    }

    /**
     * Method returns an edge considering start and end vertices.
     *
     * @param start - start vertex
     * @param end - end vertex
     * @return - an edge
     */
    @Override
    public Edge<V, E> getEdge(V start, V end) {
        if (lists.get(getVertex(start)) == null) {
            return null;
        }
        return lists.get(getVertex(start))
                .stream()
                .filter(edge -> edge.getEnd().getValue().equals(end))
                .findFirst()
                .orElse(null);
    }

    /**
     * Method returns a vertex of given graph.
     *
     * @param value - vertex value
     * @return - vertex
     */
    @Override
    public Vertex<V> getVertex(V value) {
        return verticesMap.get(value);
    }

    /**
     * Method changes the value of an edge.
     *
     * @param start - start vertex
     * @param end - end vertex
     * @param newValue - new value
     */
    @Override
    public void setEdge(V start, V end, E newValue) {
        var stVertex = getVertex(start);
        var endVertex = getVertex(end);
        for (var edge : lists.get(stVertex)) {
            if (edge.getEnd() == endVertex) {
                edge.setValue(newValue);
                return;
            }
        }
    }

    /**
     * Method changes a vertex value
     *
     * @param value - old value
     * @param newValue - new value
     */
    @Override
    public void setVertex(V value, V newValue) {
        var vertex = getVertex(value);
        List<Edge<V, E>> temp = lists.get(vertex);
        lists.remove(vertex);
        lists.put(new Vertex<>(newValue), temp);

        lists.values().stream()
                .flatMap(List::stream)
                .filter(edge -> edge.getEnd().getValue() == value)
                .forEach(edge -> edge.getEnd().setValue(newValue));

        vertex.setValue(newValue);
        verticesMap.remove(value);
        verticesMap.put(newValue, vertex);
    }

    /**
     * Method returns number of edges.
     *
     * @return - number of edges.
     */
    @Override
    public int getEdgesCount() {
        return edgesCount;
    }

    /**
     * Method finds all shortest paths from given vertex.
     *
     * @param start - given vertex
     * @return - sorted list of vertices - in order of increasing the length.
     */
    @Override
    public List<Vertex<V>> dijkstra(V start) {
        final double INF = 1000000;

        int verticesCount = verticesMap.size();
        Vertex<V> startVertex = getVertex(start);
        Map<Vertex<V>, Integer> verticesIdx = new HashMap<>();
        Map<Integer, Vertex<V>> idxVertices = new HashMap<>();
        int k = 0;
        for (var elem : verticesMap.keySet()) {
            verticesIdx.put(verticesMap.get(elem), k);
            idxVertices.put(k++, verticesMap.get(elem));
        }
        int stIdx = verticesIdx.get(startVertex);
        double [] distances = new double[verticesCount];
        boolean[] used = new boolean[verticesCount];

        for (int i = 0; i < verticesCount; ++i) {
            used[i] = false;
            if (i == stIdx) {
                distances[i] = 0d;
            } else {
                distances[i] = INF;
            }
        }

        for (int i = 0; i < verticesCount; ++i) {
            int v = -1;
            for (int j = 0; j < verticesCount; ++j) {
                if (!used[j] && (v == -1 || distances[j] < distances[v])) {
                    v = j;
                }
            }
            if (distances[v] == INF) {
                break;
            }
            used[v] = true;

            int size = lists.get(idxVertices.get(v)).size();
            for (int j = 0; j < size; ++j) {
                var temp = lists.get(idxVertices.get(v)).get(j);
                int to = verticesIdx.get(temp.getEnd());
                double len = temp.getWeight().doubleValue();
                if (distances[v] + len < distances[to]) {
                    distances[to] = distances[v] + len;
                }
            }
        }

        Integer[] idXs = IntStream.range(0, distances.length)
                .boxed()
                .toArray(Integer[]::new);

        Arrays.sort(idXs, Comparator.comparingDouble(i -> distances[i]));

        List<Vertex<V>> sortedVertices = new ArrayList<>();
        for (var idx : idXs) {
            sortedVertices.add(idxVertices.get(idx));
        }

        return sortedVertices;
    }
}
