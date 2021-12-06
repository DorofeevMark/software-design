import draw.JavaFXDrawingApi;
import graph.Graph;
import javafx.stage.Stage;

import javafx.application.Application;

public class JavaFxApplication extends Application {
    public JavaFxApplication() {
    }

    @Override
    public void start(Stage stage) {
        JavaFXDrawingApi drawingApi = new JavaFXDrawingApi(stage, GlobalContext.WIGHT, GlobalContext.HEIGHT);
        Graph graph = GlobalContext.readGraph.apply(drawingApi);
        graph.drawGraph();
    }

    public void run() {
        launch();
    }
}
