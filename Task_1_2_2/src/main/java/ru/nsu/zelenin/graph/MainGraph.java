package ru.nsu.zelenin.graph;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


/**
 * Main abstract class for graph representations.
 * It implements Graph interface, so all inherited classes will implement that too.
 * Abstract because of possibility of not implementing every interface method
 *
 * @param <V> - vertex type
 * @param <E> - edge weight type
 */
public abstract class MainGraph<V, E extends Number> implements Graph<V, E> {
    protected final Map<V, Vertex<V>> verticesMap;

    /**
     * Class constructor.
     */
    public MainGraph() {
        this.verticesMap = new HashMap<>();
    }

    /**
     * Method returns number of vertices in graph.
     *
     * @return vertices count
     */
    public int getVerticesCount() {
        return verticesMap.size();
    }

    /**
     * Method reads a graph from file.
     * THE ONLY types it can read are - Integer vertex and Double edge weight.
     *
     * @param graph - graph we read to
     * @param path - file path
     */
    public static void graphFromFile(MainGraph<Integer, Double> graph, String path) {
        try (Scanner scan = new Scanner(
                Objects.requireNonNull(MainGraph.class.getClassLoader().getResourceAsStream(path)))
                .useLocale(Locale.US)
        ) {
            int verticesCount = scan.nextInt();
            int edgesCount = scan.nextInt();

            for (int i = 1; i <= verticesCount; ++i) {
                graph.addVertex(i);
            }

            for (int i = 0; i < edgesCount; ++i) {
                int start = scan.nextInt();
                int end = scan.nextInt();
                double weight = scan.nextDouble();
                graph.addEdge(start, end, weight);
            }
        }
    }
}
