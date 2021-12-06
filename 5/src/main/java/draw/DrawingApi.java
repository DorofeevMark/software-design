package draw;

import draw.shape.Circle;
import draw.shape.Line;

public interface DrawingApi {
    long getDrawingAreaWidth();
    long getDrawingAreaHeight();
    void drawCircle(Circle circle);
    void drawLine(Line line);
    void show();
}
