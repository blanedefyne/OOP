package ru.nsu.zelenin.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Class for representation graph using adjacency matrix.
 *
 * @param <V> - vertex type
 * @param <E> - edge weight type
 */
public class AdjacencyMatrixGraph<V, E extends Number>
        extends MainGraph<V, E> {

    private int edgesCount = 0;
    private final List<List<E>> matrix = new ArrayList<>();
    private final List<Vertex<V>> vertices;

    /**
     * Class constructor.
     *
     * @param vertices - list of vertices
     * @param edges - list of edges
     */
    public AdjacencyMatrixGraph(List<Vertex<V>> vertices, List<Edge<V, E>> edges) {
        super();
        int verticesCount = vertices.size();
        this.vertices = vertices;

        for (int i = 0; i < verticesCount; ++i) {
            verticesMap.put(vertices.get(i).getValue(), vertices.get(i));
            matrix.add(new ArrayList<>());
            for (int j = 0; j < verticesCount; ++j) {
                matrix.get(i).add(null);
            }
        }

        for (var edge : edges) {
            int idx1 = vertices.indexOf(edge.getStart());
            int idx2 = vertices.indexOf(edge.getEnd());
            matrix.get(idx1).set(idx2, edge.getWeight());
            edgesCount++;
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
        int lstIdx = matrix.size() - 1;
        matrix.get(lstIdx).add(null);
        for (int i = 0; i < lstIdx; ++i) {
            matrix.get(i).add(null);
            matrix.get(lstIdx).add(null);
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
        int deletedEdges = (int) matrix.get(idx).stream()
                .filter(Objects::nonNull)
                .count();
        matrix.remove(idx);
        int verticesCount = vertices.size();
        for (int i = 0; i < verticesCount - 1; ++i) {
            if (matrix.get(i).get(idx) != null) {
                deletedEdges++;
            }
            matrix.get(i).remove(idx);
        }
        vertices.remove(idx);
        edgesCount -= deletedEdges;
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
        var stVertex = this.getVertex(start);
        var endVertex = this.getVertex(end);
        int idxS = vertices.indexOf(stVertex);
        int idxE = vertices.indexOf(endVertex);
        if (idxS == -1) {
            this.addVertex(start);
            idxS = vertices.size() - 1;
        }
        if (idxE == -1) {
            this.addVertex(end);
            idxE = vertices.size() - 1;
        }

        matrix.get(idxS).set(idxE, weight);
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
        int idxS = vertices.indexOf(this.getVertex(start));
        int idxE = vertices.indexOf(this.getVertex(end));
        if (idxE == -1 || idxS == -1) {
            return;
        }
        matrix.get(idxS).set(idxE, null);
        edgesCount--;
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
     * Method returns an edge considering start and end vertices.
     *
     * @param start - start vertex
     * @param end - end vertex
     * @return - an edge
     */
    @Override
    public Edge<V, E> getEdge(V start, V end) {
        int idx1 = vertices.indexOf(this.getVertex(start));
        int idx2 = vertices.indexOf(this.getVertex(end));
        if (idx1 != -1 && idx2 != -1) {
            E edgeValue = matrix.get(idx1).get(idx2);
            if (matrix.get(idx1).get(idx2) != null) {
                return new Edge<>(this.getVertex(start), this.getVertex(end), edgeValue);
            }
        }
        return null;
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
        int idx1 = vertices.indexOf(this.getVertex(start));
        int idx2 = vertices.indexOf(this.getVertex(end));
        matrix.get(idx1).set(idx2, newValue);
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

            for (int j = 0; j < verticesCount; ++j) {
                if (matrix.get(v).get(j) != null) {
                    double len = matrix.get(v).get(j).doubleValue();
                    if (distances[v] + len < distances[j]) {
                        distances[j] = distances[v] + len;
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