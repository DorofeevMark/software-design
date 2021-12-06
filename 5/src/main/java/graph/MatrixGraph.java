package graph;

import draw.DrawingApi;

import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

public class MatrixGraph extends Graph {
    private final boolean[][] matrix;

    public MatrixGraph(boolean[][] matrix, DrawingApi drawingApi) {
        super(matrix.length, drawingApi);
        if (matrix.length == 0 || matrix.length != matrix[0].length) {
            throw new IllegalArgumentException();
        }
        this.matrix = matrix;
    }

    public static Function<DrawingApi, Graph> readGraphFromFile(String filename) throws IOException {
        return readGraphFromInputStream(new FileInputStream(filename));
    }

    public static Function<DrawingApi, Graph> readGraphFromInputStream(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            int n = Integer.parseInt(scanner.nextLine());
            boolean[][] matrix = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                String[] s = scanner.nextLine().split(" ");
                if (s.length != n) {
                    throw new IllegalArgumentException();
                }
                for (int j = 0; j < s.length; j++) {
                    matrix[i][j] = "1".equals(s[j]);
                }
            }
            return drawingApi -> new MatrixGraph(matrix, drawingApi);
        }
    }

    public void drawGraph() {
        drawVertexes();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j]) {
                    drawEdge(i, j);
                }
            }
        }
        showGraph();
    }
}
