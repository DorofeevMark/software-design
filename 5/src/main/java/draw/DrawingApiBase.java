package draw;

public abstract class DrawingApiBase implements DrawingApi {
    private final long width;
    private final long height;

    public DrawingApiBase(long width, long height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public long getDrawingAreaWidth() {
        return width;
    }

    @Override
    public long getDrawingAreaHeight() {
        return height;
    }
}