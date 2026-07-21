package uk.co.graph;

import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph();

        // ---- Test addVertex ----
        System.out.println("=== addVertex ===");
        System.out.println("Add 'Ana' (expect true): " + graph.addVertex("Ana"));
        System.out.println("Add 'Bruno' (expect true): " + graph.addVertex("Bruno"));
        System.out.println("Add 'Carlos' (expect true): " + graph.addVertex("Carlos"));
        System.out.println("Add 'Ana' again (expect false): " + graph.addVertex("Ana"));

        try {
            graph.addVertex(null);
            System.out.println("Add null: NO exception thrown (BUG!)");
        } catch (IllegalArgumentException e) {
            System.out.println("Add null (expect exception): OK, exception thrown");
        }

        // ---- Test addEdge ----
        System.out.println("\n=== addEdge ===");
        System.out.println("Edge Ana-Bruno (expect true): " + graph.addEdge("Ana", "Bruno"));
        System.out.println("Edge Ana-Carlos (expect true): " + graph.addEdge("Ana", "Carlos"));
        System.out.println("Edge Ana-Fantasma (expect false, vertex doesn't exist): " + graph.addEdge("Ana", "Fantasma"));

        // ---- Check connections after addEdge ----
        System.out.println("\n=== Connections after addEdge ===");
        printConnections(graph);
        // Expected: Ana -> [Bruno, Carlos]
        //           Bruno -> [Ana]
        //           Carlos -> [Ana]

        // ---- Test removeEdge ----
        System.out.println("\n=== removeEdge ===");
        System.out.println("Remove edge Ana-Bruno (expect true): " + graph.removeEdge("Ana", "Bruno"));
        System.out.println("Remove edge Ana-Fantasma (expect false): " + graph.removeEdge("Ana", "Fantasma"));

        System.out.println("\n=== Connections after removeEdge ===");
        printConnections(graph);
        // Expected: Ana -> [Carlos]
        //           Bruno -> []
        //           Carlos -> [Ana]

        // ---- Test removeVertex ----
        System.out.println("\n=== removeVertex ===");
        System.out.println("Remove vertex 'Carlos' (expect true): " + graph.removeVertex("Carlos"));
        System.out.println("Remove vertex 'Fantasma' (expect false): " + graph.removeVertex("Fantasma"));

        System.out.println("\n=== Connections after removeVertex ===");
        printConnections(graph);
        // Expected: Ana -> [] (Carlos reference removed)
        //           Bruno -> []
        //           (Carlos key gone entirely)
    }

    private static void printConnections(Graph graph) {
        for (String vertex : graph.connectionFriendsList.keySet()) {
            System.out.println(vertex + " -> " + graph.connectionFriendsList.get(vertex));
        }
    }
}
