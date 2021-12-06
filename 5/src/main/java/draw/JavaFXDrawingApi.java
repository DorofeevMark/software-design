package draw;

import draw.shape.Circle;
import draw.shape.Line;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class JavaFXDrawingApi extends DrawingApiBase {
    private final Canvas canvas;
    private final Stage stage;
    private final GraphicsContext graphicsContext;

    public JavaFXDrawingApi(Stage stage, long width, long height) {
        super(width, height);
        this.stage = stage;
        canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();
    }

    @Override
    public void drawCircle(Circle circle) {
        graphicsContext.fillOval(circle.getPoint().getX() - circle.getRadius(),
                circle.getPoint().getY() - circle.getRadius(),
                2 * circle.getRadius(), 2 * circle.getRadius());
    }

    @Override
    public void drawLine(Line line) {
        graphicsContext.strokeLine(line.getFrom().getX(), line.getFrom().getY(), line.getTo().getX(), line.getTo().getY());
    }

    @Override
    public void show() {
        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }
}