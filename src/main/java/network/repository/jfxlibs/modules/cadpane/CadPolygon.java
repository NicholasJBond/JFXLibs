package network.repository.jfxlibs.modules.cadpane;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;

import java.util.Arrays;

public class CadPolygon implements CadFeature{
    double[] xCoords;
    double[] yCoords;
    double maxy;
    double maxx;
    double miny;
    double minx;
    private boolean selected = false;
    private double width = 2;


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
        gc.setLineWidth(width);
        gc.setStroke(Color.GREEN);
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
    public boolean mouseOver(Point2D cursor, double mouseSize) {
        return false;
    }

    @Override
    public boolean inSelection(SmartPolygon selectionArea) {
        return false;
    }

    @Override
    public void drawHover(GraphicsContext gc,Affine transformation, Affine inverse, Color color) {

    }


    @Override
    public boolean isClicked(double x, double y) {
        return false;
    }

    @Override
    public void select() {
        selected = !selected;
    }

    @Override
    public boolean selected() {
        return selected;
    }

    @Override
    public void clearSelection() {
        selected = false;
    }
}
