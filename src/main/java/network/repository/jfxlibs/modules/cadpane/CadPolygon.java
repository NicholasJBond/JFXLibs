package network.repository.jfxlibs.modules.cadpane;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;

import java.util.Arrays;

public class CadPolygon implements CadFeature{
    double[] xCoords;
    double[] yCoords;
    double maxy;
    double maxx;
    double miny;
    double minx;

    public CadPolygon(double[] x, double[] y) {
        xCoords = x;
        yCoords = y;

        maxx = Arrays.stream(xCoords)
                .max()
                .orElse(Double.NaN);
        maxy = Arrays.stream(yCoords)
                .max()
                .orElse(Double.NaN);

        minx = Arrays.stream(xCoords)
                .min()
                .orElse(Double.NaN);

        miny = Arrays.stream(yCoords)
                .min()
                .orElse(Double.NaN);
    }

    @Override
    public void draw(GraphicsContext gc, double value) {
        gc.strokePolygon(xCoords, yCoords, xCoords.length);
    }

    @Override
    public double maxX() {
        return maxx;
    }

    @Override
    public double minX() {
        return minx;
    }

    @Override
    public double maxY() {
        return maxy;
    }

    @Override
    public double minY() {
        return miny;
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
