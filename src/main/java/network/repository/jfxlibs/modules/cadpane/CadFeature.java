package network.repository.jfxlibs.modules.cadpane;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;


public interface CadFeature {
    void draw(GraphicsContext gc, double value);
    double maxX();
    double minX();
    double maxY();
    double minY();
    boolean drawHover(MouseEvent e, GraphicsContext gc, Affine transformation);
    boolean isClicked(double x, double y);
}
