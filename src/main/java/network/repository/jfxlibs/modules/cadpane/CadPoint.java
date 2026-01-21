package network.repository.jfxlibs.modules.cadpane;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.text.DecimalFormat;


public class CadPoint implements CadFeature{
    private final int id;
    private final String name;
    private final double x;
    private final double z;
    private final double y;
    private double radius = 5;
    private boolean selected = false;
    public CadPoint(int id, String name, double x, double y, double z) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void draw(GraphicsContext gc, double value) {
        radius = value*1.5;
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public double maxX() {
        return x;
    }

    @Override
    public double minX() {
        return x;
    }

    @Override
    public double maxY() {
        return y;
    }

    @Override
    public double minY() {
        return y;
    }

    @Override
    public boolean mouseOver(Point2D cursor, double mouseSize) {
        double a = x-cursor.getX();
        double b = y-cursor.getY();
        return !((a * a) + (b * b) > mouseSize*mouseSize);
    }

    @Override
    public boolean inSelection(SmartPolygon selectionArea) {
        return selectionArea.contains(new Point2D(x,y));
    }



    @Override
    public void drawHover(GraphicsContext gc,Affine transformation, Affine inverse, Color color) {
        gc.setFill(color);
        gc.transform(transformation);
        gc.fillOval(x-radius, y-radius , radius*2, radius*2);
        gc.transform(inverse);
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
    public boolean isClicked(double x, double y) {

        return false;
    }

    @Override
    public void clearSelection() {
        selected = false;
    }

    public String toString(){
        DecimalFormat df = new DecimalFormat("#.###");
        return "Point: "+df.format(x)+"  "+df.format(y);
    }
}
