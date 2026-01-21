package network.repository.jfxlibs.modules.cadpane;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;



public interface CadFeature {
    int getId();
    void draw(GraphicsContext gc, double value);
    double maxX();
    double minX();
    double maxY();
    double minY();

    boolean mouseOver(Point2D cursor, double mouseSize);
    boolean inSelection(SmartPolygon selectionArea);

    void drawHover(GraphicsContext gc, Affine transform, Affine inverse, Color color);
    void select();
    boolean selected();

    boolean isClicked(double x, double y);

    void clearSelection();
}
