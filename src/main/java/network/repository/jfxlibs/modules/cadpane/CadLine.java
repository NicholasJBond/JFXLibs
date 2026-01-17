package network.repository.jfxlibs.modules.cadpane;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;


public class CadLine implements CadFeature{
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;

    public CadLine(double x11, double y11, double x21, double y21) {

        this.x1 = x11;
        this.y1 = y11;
        this.x2 = x21;
        this.y2 = y21;
    }

    @Override
    public void draw(GraphicsContext gc, double value) {
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public double maxX() {
        return Math.max(x1, x2);
    }

    @Override
    public double minX() {
        return Math.min(x1, x2);
    }

    @Override
    public double maxY() {
        return Math.max(y1, y2);
    }

    @Override
    public double minY() {
        return Math.min(y1,y2);
    }

    @Override
    public boolean drawHover(MouseEvent e, GraphicsContext gc, Affine transformation) {
        return false;
    }

    @Override
    public boolean isClicked(double x, double y) {
        return false;
    }
}
