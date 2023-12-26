package ru.nsu.zelenin.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Testing class.
 */
public class GraphTest {

    @Test
    public void amAddVerticesTest() {
        AdjacencyMatrixGraph<String, Double> amGraph = new AdjacencyMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        amGraph.addVertex("A");
        amGraph.addVertex("AB");
        amGraph.addVertex("B");
        assertEquals(3, amGraph.getVerticesCount());
    }

    @Test
    public void amRemoveVerticesTest() {
        AdjacencyMatrixGraph<String, Double> amGraph = new AdjacencyMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        amGraph.addVertex("devil");
        amGraph.addVertex("ABC");
        amGraph.addVertex("B");
        amGraph.removeVertex("devil");
        amGraph.addVertex("DD");
        assertEquals(3, amGraph.getVerticesCount());
    }

    @Test
    public void amSetGetEdgeTest() {
        var a = new Vertex<>("A");
        var b = new Vertex<>("B");
        var c = new Vertex<>("C");
        List<Vertex<String>> vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        List<Edge<String, Double>> edges = new ArrayList<>();
        edges.add(new Edge<>(a, b, 10d));
        edges.add(new Edge<>(b, c, 9d));
        edges.add(new Edge<>(a, c, 3d));
        edges.add(new Edge<>(c, a, 27d));
        AdjacencyMatrixGraph<String, Double> amGraph = new AdjacencyMatrixGraph<>(vertices, edges);
        amGraph.addVertex("devil");
        amGraph.addVertex("ABC");
        amGraph.setEdge("A", "B", 90d);
        assertEquals(90d, amGraph.getEdge("A", "B").getWeight());
    }

    @Test
    public void amRemoveVertexAndEdgesTest() {
        AdjacencyMatrixGraph<String, Double> amGraph = new AdjacencyMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        amGraph.addVertex("A");
        amGraph.addVertex("B");
        amGraph.addVertex("B1");
        amGraph.removeVertex("throne");
        amGraph.addVertex("carousel");
        amGraph.addEdge("A", "B", 10d);
        amGraph.addEdge("A", "I", 66.12d);
        amGraph.removeVertex("A");
        assertNull(amGraph.getEdge("A", "I"));
    }

    @Test
    public void amSetVertexTest() {
        AdjacencyMatrixGraph<String, Double> amGraph = new AdjacencyMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        amGraph.addVertex("A");
        amGraph.addVertex("B");
        amGraph.addVertex("B1");
        amGraph.setVertex("B1", "C");
        assertNull(amGraph.getVertex("B1"));
    }

    @Test
    public void amRemoveEdgeTest() {
        AdjacencyMatrixGraph<Double, Float> amGraph = new AdjacencyMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        amGraph.addEdge(2.5, 3.5, 10f);
        amGraph.addEdge(1.0, 2.5, 9.1f);
        amGraph.addEdge(0.9, 3.5, 8f);
        amGraph.removeEdge(0.9, 3.5);
        assertEquals(2, amGraph.getEdgesCount());
    }

    @Test
    public void amDijkstraFromFile() {
        AdjacencyMatrixGraph<Integer, Double> amGraph = new AdjacencyMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        MainGraph.graphFromFile(amGraph, "amTestGraph.txt");
        List<Vertex<Integer>> answer = new ArrayList<>();
        answer.add(new Vertex<>(2));
        answer.add(new Vertex<>(5));
        answer.add(new Vertex<>(4));
        answer.add(new Vertex<>(1));
        answer.add(new Vertex<>(3));

        List<Vertex<Integer>> res = amGraph.dijkstra(2);
        assertEquals(answer, res);

    }

    @Test
    public void amDijkstraFromExample() {
        AdjacencyMatrixGraph<String, Double> amGraph = new AdjacencyMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        amGraph.addEdge("A", "B", 5d);
        amGraph.addEdge("B", "A", 5d);
        amGraph.addEdge("B", "D", 8d);
        amGraph.addEdge("D", "B", 8d);
        amGraph.addEdge("D", "A", 12d);
        amGraph.addEdge("A", "D", 12d);
        amGraph.addEdge("C", "E", 4d);
        amGraph.addEdge("E", "C", 4d);
        amGraph.addEdge("C", "D", 2d);
        amGraph.addEdge("D", "C", 2d);
        amGraph.addEdge("A", "G", 25d);
        amGraph.addEdge("G", "A", 25d);
        amGraph.addEdge("F", "G", 10d);
        amGraph.addEdge("G", "F", 10d);
        amGraph.addEdge("C", "F", 5d);
        amGraph.addEdge("F", "C", 5d);
        amGraph.addEdge("E", "G", 5d);
        amGraph.addEdge("G", "E", 5d);
        amGraph.addEdge("C", "G", 10d);
        amGraph.addEdge("G", "C", 10d);

        List<Vertex<String>> answer = new ArrayList<>();
        answer.add(new Vertex<>("C"));
        answer.add(new Vertex<>("D"));
        answer.add(new Vertex<>("E"));
        answer.add(new Vertex<>("F"));
        answer.add(new Vertex<>("G"));
        answer.add(new Vertex<>("B"));
        answer.add(new Vertex<>("A"));

        List<Vertex<String>> res = amGraph.dijkstra("C");
        assertEquals(answer, res);
    }

    @Test
    public void alCountTest() {
        AdjacencyListGraph<Integer, Integer> alGraph = new AdjacencyListGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        alGraph.addEdge(1, 2, 10);
        alGraph.addEdge(2, 3, 90);
        alGraph.addEdge(12, 24, 100);
        alGraph.removeEdge(12, 24);
        alGraph.addEdge(3, 11, 121);
        alGraph.setEdge(3, 11, 100000);
        alGraph.setVertex(3, 33);
        alGraph.removeVertex(33);

        assertEquals(5, alGraph.getVerticesCount());
        assertEquals(1, alGraph.getEdgesCount());
    }

    @Test
    public void alOneMoreTest() {
        var a = new Vertex<>("A");
        var b = new Vertex<>("B");
        var c = new Vertex<>("C");
        var d = new Vertex<>("D");
        List<Vertex<String>> vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);
        List<Edge<String, Float>> edges = new ArrayList<>();
        edges.add(new Edge<>(a, b, 10f));
        edges.add(new Edge<>(b, c, 9f));
        edges.add(new Edge<>(a, c, 31.5f));
        edges.add(new Edge<>(c, a, 27f));
        edges.add(new Edge<>(d, a, 0.9f));
        AdjacencyListGraph<String, Float> alGraph = new AdjacencyListGraph<>(vertices, edges);

        alGraph.setVertex("A", "AAAA");
        assertNull(alGraph.getEdge("A", "B"));
        assertNull(alGraph.getEdge("C", "A"));

    }

    @Test
    public void alDijkstraFromFile() {
        AdjacencyListGraph<Integer, Double> alGraph = new AdjacencyListGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        MainGraph.graphFromFile(alGraph, "alTestGraph.txt");
        List<Vertex<Integer>> answer = new ArrayList<>();
        answer.add(new Vertex<>(3));
        answer.add(new Vertex<>(1));
        answer.add(new Vertex<>(2));
        answer.add(new Vertex<>(4));

        List<Vertex<Integer>> res = alGraph.dijkstra(3);
        assertEquals(answer, res);
    }

    @Test
    public void alDijkstraFromExample() {
        AdjacencyListGraph<String, Double> alGraph = new AdjacencyListGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        alGraph.addEdge("A", "B", 5d);
        alGraph.addEdge("B", "A", 5d);
        alGraph.addEdge("B", "D", 8d);
        alGraph.addEdge("D", "B", 8d);
        alGraph.addEdge("D", "A", 12d);
        alGraph.addEdge("A", "D", 12d);
        alGraph.addEdge("C", "E", 4d);
        alGraph.addEdge("E", "C", 4d);
        alGraph.addEdge("C", "D", 2d);
        alGraph.addEdge("D", "C", 2d);
        alGraph.addEdge("A", "G", 25d);
        alGraph.addEdge("G", "A", 25d);
        alGraph.addEdge("F", "G", 10d);
        alGraph.addEdge("G", "F", 10d);
        alGraph.addEdge("C", "F", 5d);
        alGraph.addEdge("F", "C", 5d);
        alGraph.addEdge("E", "G", 5d);
        alGraph.addEdge("G", "E", 5d);
        alGraph.addEdge("C", "G", 10d);
        alGraph.addEdge("G", "C", 10d);

        List<Vertex<String>> answer = new ArrayList<>();
        answer.add(new Vertex<>("C"));
        answer.add(new Vertex<>("D"));
        answer.add(new Vertex<>("E"));
        answer.add(new Vertex<>("F"));
        answer.add(new Vertex<>("G"));
        answer.add(new Vertex<>("B"));
        answer.add(new Vertex<>("A"));

        List<Vertex<String>> res = alGraph.dijkstra("C");
        assertEquals(answer, res);
    }

    @Test
    public void imEdgesTest() {
        IncidenceMatrixGraph<String, Long> imGraph = new IncidenceMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );

        imGraph.addEdge("a", "b", 10L);
        imGraph.addEdge("c", "d", 112L);
        imGraph.addVertex("Rebel");
        imGraph.setVertex("Rebel", "giant");
        imGraph.removeVertex("giant");
        imGraph.removeEdge("a", "d");

        assertEquals(2, imGraph.getEdgesCount());
        assertEquals(4, imGraph.getVerticesCount());
    }

    @Test
    public void imOneMoreTest() {
        var one = new Vertex<>(1);
        var one2 = new Vertex<>(11);
        var one3 = new Vertex<>(111);
        var one4 = new Vertex<>(1111);
        List<Vertex<Integer>> vertices = new ArrayList<>();
        vertices.add(one);
        vertices.add(one2);
        vertices.add(one3);
        vertices.add(one4);
        List<Edge<Integer, Float>> edges = new ArrayList<>();
        edges.add(new Edge<>(one, one4, 10f));
        edges.add(new Edge<>(one2, one3, 9f));
        edges.add(new Edge<>(one3, one4, 31.5f));
        edges.add(new Edge<>(one, one2, 27f));
        edges.add(new Edge<>(one3, one, 0.9f));
        IncidenceMatrixGraph<Integer, Float> imGraph = new IncidenceMatrixGraph<>(vertices, edges);

        imGraph.setVertex(11, 12);
        imGraph.setVertex(111, 13);
        imGraph.removeEdge(12, 13);
        imGraph.addEdge(12, 1, 105.09f);
        imGraph.addEdge(13, 14, 152.13f);
        imGraph.removeVertex(12);
        imGraph.removeVertex(1);

        assertEquals(2, imGraph.getEdgesCount());
        assertEquals(3, imGraph.getVerticesCount());
        assertNull(imGraph.getEdge(12, 13));
    }

    @Test
    public void imDijkstraFromFile() {
        IncidenceMatrixGraph<Integer, Double> imGraph = new IncidenceMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        MainGraph.graphFromFile(imGraph, "imTestGraph.txt");
        imGraph.setEdge(2, 3, 9d);
        imGraph.setEdge(3, 2, 9d);
        List<Vertex<Integer>> answer = new ArrayList<>();
        answer.add(new Vertex<>(2));
        answer.add(new Vertex<>(5));
        answer.add(new Vertex<>(6));
        answer.add(new Vertex<>(4));
        answer.add(new Vertex<>(3));
        answer.add(new Vertex<>(1));

        List<Vertex<Integer>> res = imGraph.dijkstra(2);
        assertEquals(answer, res);
    }

    @Test
    public void imDijkstraFromExample() {
        IncidenceMatrixGraph<String, Double> imGraph = new IncidenceMatrixGraph<>(
                new ArrayList<>(), new ArrayList<>()
        );
        imGraph.addEdge("A", "B", 5d);
        imGraph.addEdge("B", "A", 5d);
        imGraph.addEdge("B", "D", 8d);
        imGraph.addEdge("D", "B", 8d);
        imGraph.addEdge("D", "A", 12d);
        imGraph.addEdge("A", "D", 12d);
        imGraph.addEdge("C", "E", 4d);
        imGraph.addEdge("E", "C", 4d);
        imGraph.addEdge("C", "F", 5d);
        imGraph.addEdge("F", "C", 5d);
        imGraph.addEdge("C", "D", 2d);
        imGraph.addEdge("D", "C", 2d);
        imGraph.addEdge("A", "G", 25d);
        imGraph.addEdge("G", "A", 25d);
        imGraph.addEdge("F", "G", 10d);
        imGraph.addEdge("G", "F", 10d);
        imGraph.addEdge("E", "G", 5d);
        imGraph.addEdge("G", "E", 5d);
        imGraph.addEdge("C", "G", 10d);
        imGraph.addEdge("G", "C", 10d);

        List<Vertex<String>> answer = new ArrayList<>();
        answer.add(new Vertex<>("C"));
        answer.add(new Vertex<>("D"));
        answer.add(new Vertex<>("E"));
        answer.add(new Vertex<>("F"));
        answer.add(new Vertex<>("G"));
        answer.add(new Vertex<>("B"));
        answer.add(new Vertex<>("A"));

        List<Vertex<String>> res = imGraph.dijkstra("C");
        assertEquals(answer, res);
    }
}
