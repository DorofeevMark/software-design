import draw.AwtDrawingApi;
import graph.Graph;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AwtApplication extends Frame {
    @Override
    public void paint(Graphics g) {
        AwtDrawingApi drawingApi = new AwtDrawingApi((Graphics2D) g, GlobalContext.WIGHT, GlobalContext.HEIGHT);
        Graph graph = GlobalContext.readGraph.apply(drawingApi);
        graph.drawGraph();
    }

    public void run() {
        Frame frame = new AwtApplication();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(GlobalContext.WIGHT, GlobalContext.HEIGHT);
        frame.setVisible(true);
    }
}
