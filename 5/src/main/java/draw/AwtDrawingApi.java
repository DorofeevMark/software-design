package draw;

import java.awt.*;

import draw.shape.Circle;
import draw.shape.Line;

public class AwtDrawingApi extends DrawingApiBase {
    private final Graphics2D graphics2D;

    public AwtDrawingApi(Graphics2D graphics2D, long width, long height) {
        super(width, height);
        this.graphics2D = graphics2D;
    }

    @Override
    public void drawCircle(Circle circle) {
        graphics2D.fillOval((int) (circle.getPoint().getX() - circle.getRadius()),
                (int) (circle.getPoint().getY() - circle.getRadius()),
                2 * (int) circle.getRadius(), 2 * (int) circle.getRadius());
    }

    @Override
    public void drawLine(Line line) {
        graphics2D.drawLine((int) line.getFrom().getX(), (int) line.getFrom().getY(),
                (int) line.getTo().getX(), (int) line.getTo().getY());
    }

    @Override
    public void show() {
    }
}