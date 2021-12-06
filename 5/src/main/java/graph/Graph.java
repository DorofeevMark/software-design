package graph;

import draw.shape.Circle;
import draw.DrawingApi;
import draw.shape.Line;
import draw.shape.Point;

public abstract class Graph {
    private final int vertexCount;
    private final DrawingApi drawingApi;
    private final Point centerPoint;
    private final long pointSize;
    private final long circleRadius;

    public Graph(int vertexCount, DrawingApi drawingApi) {
        this.vertexCount = vertexCount;
        this.drawingApi = drawingApi;
        this.centerPoint = new Point(drawingApi.getDrawingAreaWidth() / 2, drawingApi.getDrawingAreaHeight() / 2);
        this.pointSize = drawingApi.getDrawingAreaHeight() / 300;
        this.circleRadius = drawingApi.getDrawingAreaWidth() / 4;
    }

    public abstract void drawGraph();

    protected Point getPointCoordinate(int num) {
        double deg = 2 * Math.PI * num / vertexCount;
        return new Point((long) (centerPoint.getX() + circleRadius * Math.cos(deg)),
                (long) (centerPoint.getY() + circleRadius * Math.sin(deg)));
    }

    protected void drawVertexes() {
        for (int i = 0; i < vertexCount; i++) {
            drawingApi.drawCircle(new Circle(getPointCoordinate(i), pointSize));
        }
    }

    protected void drawEdge(int from, int to) {
        drawingApi.drawLine(new Line(getPointCoordinate(from), getPointCoordinate(to)));
    }

    protected void showGraph() {
        drawingApi.show();
    }
}