package graph;

import draw.DrawingApi;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListGraph extends Graph {
    private final int vertexCount;
    private final List<Edge> edges;

    public ListGraph(int vertexCount, List<Edge> edges, DrawingApi drawingApi) {
        super(vertexCount, drawingApi);
        this.vertexCount = vertexCount;
        this.edges = edges;
    }

    public static Function<DrawingApi, Graph> readGraphFromFile(String filename) throws IOException {
        return readGraphFromInputStream(new FileInputStream(filename));
    }

    public static Function<DrawingApi, Graph> readGraphFromInputStream(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            int n = Integer.parseInt(scanner.nextLine());
            int m = Integer.parseInt(scanner.nextLine());
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                List<Integer> edge = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
                if (edge.size() != 2) {
                    throw new IllegalArgumentException();
                }
                edges.add(new Edge(edge.get(0), edge.get(1)));
            }
            return drawingApi -> new ListGraph(n, edges, drawingApi);
        }
    }

    public void drawGraph() {
        drawVertexes();
        for (Edge edge : edges) {
            drawEdge(edge.getFrom(), edge.getTo());
        }
        showGraph();
    }

    @Getter
    @AllArgsConstructor
    private static class Edge {
        private final int from;
        private final int to;
    }
}
