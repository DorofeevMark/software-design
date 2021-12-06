import graph.ListGraph;
import graph.MatrixGraph;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String api = args[0];
        String graphType = args[1];

        switch (graphType) {
            case "matrix":
                GlobalContext.readGraph = MatrixGraph.readGraphFromFile("src/main/resources/matrix.txt");
                break;
            case "list":
                GlobalContext.readGraph = ListGraph.readGraphFromFile("src/main/resources/list.txt");
                break;
        }

        switch (api) {
            case "awt":
                new JavaFxApplication().run();
                break;
            case "javafx":
                new JavaFxApplication().run();
                break;
        }
    }
}
