package network.repository.jfxlibs.modules.cadpane;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class SmartPolygon extends Polygon{
    private final Polygon internalPoints = new Polygon();

    public SmartPolygon() {
    }

    @Override
    public boolean contains(Point2D p) {
        boolean inside = false;
        int n = this.getPoints().size() / 2;
        if (n < 3) return false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = this.getPoints().get(i * 2),     yi = this.getPoints().get(i * 2 + 1);
            double xj = this.getPoints().get(j * 2),     yj = this.getPoints().get(j * 2 + 1);

            // Even-Odd Ray Casting Math
            boolean intersect = ((yi > p.getY()) != (yj > p.getY()))
                    && (p.getX() < (xj - xi) * (p.getY() - yi) / (yj - yi) + xi);

            if (intersect) {
                inside = !inside;
            }
        }
        return inside;
    }
}