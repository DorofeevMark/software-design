import draw.DrawingApi;
import graph.Graph;

import java.util.function.Function;

public class GlobalContext {
    public static Function<DrawingApi, Graph> readGraph;
    public static int WIGHT = 800;
    public static int HEIGHT = 600;
}