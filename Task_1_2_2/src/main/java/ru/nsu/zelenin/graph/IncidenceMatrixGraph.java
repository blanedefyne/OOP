package ru.nsu.zelenin.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Class for representation graph using incidence matrix.
 *
 * @param <V> - vertex type
 * @param <E> - edge weight type
 */
public class IncidenceMatrixGraph<V, E extends Number>
        extends MainGraph<V, E> {

    private final List<Edge<V, E>> edges;
    private final List<Vertex<V>> vertices;
    private final List<List<E>> matrix = new ArrayList<>();

    /**
     * Class constructor.
     *
     * @param vertices - list of vertices
     * @param edges - list of edges
     */
    public IncidenceMatrixGraph(List<Vertex<V>> vertices, List<Edge<V, E>> edges) {
        super();
        this.vertices = vertices;
        this.edges = edges;
        int edgesCount = edges.size();
        int verticesCount = vertices.size();

        for (int i = 0; i < verticesCount; ++i) {
            verticesMap.put(vertices.get(i).getValue(), vertices.get(i));
            matrix.add(new ArrayList<>());
            for (int j = 0; j < edgesCount; ++j) {
                var curVer = vertices.get(i);
                var curEdge = edges.get(j);

                if (curVer == curEdge.getStart()) {
                    matrix.get(i).add(curEdge.getNegWeight());
                } else if (curVer == curEdge.getEnd()) {
                    matrix.get(i).add(curEdge.getWeight());
                } else {
                    matrix.get(i).add(null);
                }
            }
        }
    }

    /**
     * Methods adds a vertex to graph.
     *
     * @param value - value of a vertex
     */
    @Override
    public void addVertex(V value) {
        var vert = new Vertex<>(value);
        vertices.add(vert);
        matrix.add(new ArrayList<>());
        verticesMap.put(value, vert);

        int edgesCount = edges.size();
        int verticesCount = vertices.size();
        for (int i = 0; i < edgesCount; ++i) {
            matrix.get(verticesCount - 1).add(null);
        }
    }

    /**
     * Method removes a vertex from graph.
     *
     * @param value - value of a vertex
     */
    @Override
    public void removeVertex(V value) {
        int idx = vertices.indexOf(this.getVertex(value));
        if (idx == -1) {
            return;
        }
        verticesMap.remove(value);
        vertices.remove(idx);
        matrix.remove(idx);

        int verticesCount = vertices.size();
        int edgesCount = edges.size();
        for (int i = edgesCount - 1; i >= 0; --i) {
            if ((edges.get(i).getStart().getValue() == value)
                    || (edges.get(i).getEnd().getValue() == value)) {
                for (int j = 0; j < verticesCount; ++j) {
                    matrix.get(j).remove(i);
                }
                edges.remove(i);
            }
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
        var stVertex = getVertex(start);
        var endVertex = getVertex(end);
        if (stVertex == null) {
            this.addVertex(start);
            stVertex = getVertex(start);
        }
        if (endVertex == null) {
            this.addVertex(end);
            endVertex = getVertex(end);
        }
        var newEdge = new Edge<>(stVertex, endVertex, weight);
        edges.add(newEdge);

        int stIdx = vertices.indexOf(stVertex);
        int endIdx = vertices.indexOf(endVertex);
        int verticesCount = vertices.size();
        for (int i = 0; i < verticesCount; ++i) {
            if (i == stIdx) {
                matrix.get(i).add(newEdge.getNegWeight());
            } else if (i == endIdx) {
                matrix.get(i).add(weight);
            } else {
                matrix.get(i).add(null);
            }
        }
    }

    /**
     * Method removes an edge from graph.
     *
     * @param start - start vertex
     * @param end - end vertex
     */
    @Override
    public void removeEdge(V start, V end) {
        var edgeToDelete = edges.stream()
                .filter(edge -> edge.getStart().getValue() == start
                && edge.getEnd().getValue() == end)
                .findFirst()
                .orElse(null);
        if (edgeToDelete == null) {
            return;
        }
        int edgeIdx = edges.indexOf(edgeToDelete);
        edges.remove(edgeToDelete);

        int verticesCount = vertices.size();
        for (int i = 0; i < verticesCount; ++i) {
            matrix.get(i).remove(edgeIdx);
        }
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
        return edges.stream()
                .filter(edge -> edge.getStart().getValue() == start
                && edge.getEnd().getValue() == end)
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
        var edgeToSet = edges.stream()
                .filter(edge -> edge.getStart().getValue() == start
                && edge.getEnd().getValue() == end)
                .findFirst()
                .orElse(null);
        if (edgeToSet == null) {
            return;
        }
        edgeToSet.setValue(newValue);
        int edgeIdx = edges.indexOf(edgeToSet);
        int idxSt = vertices.indexOf(edgeToSet.getStart());
        int idxEnd = vertices.indexOf(edgeToSet.getEnd());
        matrix.get(idxSt).set(edgeIdx, edgeToSet.getNegWeight());
        matrix.get(idxEnd).set(edgeIdx, newValue);
    }

    /**
     * Method changes a vertex value.
     *
     * @param value - old value
     * @param newValue - new value
     */
    @Override
    public void setVertex(V value, V newValue) {
        var temp = this.getVertex(value);
        temp.setValue(newValue);
        verticesMap.remove(value);
        verticesMap.put(newValue, temp);

        var vert = vertices.stream().
                filter(v -> v.getValue() == value).
                findFirst()
                .orElse(null);
        if (vert == null) {
            return;
        }
        vert.setValue(newValue);
        for (var edge : edges) {
            if (edge.getStart().getValue() == value) {
                edge.getStart().setValue(newValue);
            }
            if (edge.getEnd().getValue() == value) {
                edge.getEnd().setValue(newValue);
            }
        }
    }

    /**
     * Method returns number of edges.
     *
     * @return - number of edges.
     */
    @Override
    public int getEdgesCount() {
        return edges.size();
    }

    /**
     * Method finds all shortest paths from given vertex.
     *
     * @param start - given vertex
     * @return - sorted list of vertices - in order of increasing the length.
     */
    @Override
    public List<Vertex<V>> dijkstra(V start) {
        final double infinity = 10000000;

        int verticesCount = verticesMap.size();
        Vertex<V> startVertex = getVertex(start);
        Map<Vertex<V>, Integer> verticesIdx = new HashMap<>();
        Map<Integer, Vertex<V>> idxVertices = new HashMap<>();
        for (int i = 0; i < verticesCount; ++i) {
            verticesIdx.put(vertices.get(i), i);
            idxVertices.put(i, vertices.get(i));
        }
        int stIdx = verticesIdx.get(startVertex);
        double [] distances = new double[verticesCount];
        boolean[] used = new boolean[verticesCount];

        for (int i = 0; i < verticesCount; ++i) {
            used[i] = false;
            if (i == stIdx) {
                distances[i] = 0d;
            } else {
                distances[i] = infinity;
            }
        }

        for (int i = 0; i < verticesCount; ++i) {
            int v = -1;
            for (int j = 0; j < verticesCount; ++j) {
                if (!used[j] && (v == -1 || distances[j] < distances[v])) {
                    v = j;
                }
            }
            if (distances[v] == infinity) {
                break;
            }
            used[v] = true;

            for (int j = 0; j < edges.size(); ++j) {
                if (matrix.get(v).get(j) != null
                        && matrix.get(v).get(j).doubleValue() < 0) {
                    int to = verticesIdx.get(edges.get(j).getEnd());
                    double len = -matrix.get(v).get(j).doubleValue();
                    if (distances[v] + len < distances[to]) {
                        distances[to] = distances[v] + len;
                    }
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
