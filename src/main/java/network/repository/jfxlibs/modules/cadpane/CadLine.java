package network.repository.jfxlibs.modules.cadpane;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import network.repository.jfxlibs.modules.vectors.V;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.simple.SimpleMatrix;

import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class CadLine implements CadFeature{
    private final int id;
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private boolean selected = false;
    private double width = 0.5;


    public CadLine(int id, double x11, double y11, double x21, double y21) {
        this.id = id;
        this.x1 = x11;
        this.y1 = y11;
        this.x2 = x21;
        this.y2 = y21;


    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void draw(GraphicsContext gc, double value) {
        width = value/4;
        gc.setLineWidth(width);
        gc.setStroke(Color.WHITE);
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
    public boolean mouseOver(Point2D cursor, double mouseSize) {

        double cx = cursor.getX(), cy = cursor.getY();

        // 1. Get length of the line segment squared
        double dx = x2 - x1;
        double dy = y2 - y1;
        double lenSq = dx * dx + dy * dy;

        // 2. Handle the case where the segment is actually a single point
        if (lenSq == 0) return new Point2D(x1, y1).distance(cursor) <= mouseSize;

        // 3. Find the projection of the center onto the line (parameterized t)
        // t is the distance along the line from A to B, scaled 0 to 1
        double t = ((cx - x1) * dx + (cy - y1) * dy) / lenSq;

        // 4. Clamp t to the range [0, 1] to stay on the line segment
        t = Math.max(0, Math.min(1, t));

        // 5. Find the closest point on the segment
        double closestX = x1 + t * dx;
        double closestY = y1 + t * dy;

        // 6. Check distance from center to this closest point
        double distSq = Math.pow(cx - closestX, 2) + Math.pow(cy - closestY, 2);
        return distSq <= mouseSize * mouseSize;
    }

    @Override
    public boolean inSelection(SmartPolygon selectionArea) {
        boolean selectAllMode = selectionArea.getPoints().size() > 2 && selectionArea.getPoints().get(2) <= selectionArea.getPoints().get(0);
        if (selectionArea.contains(new Point2D(x1, y1)) && selectAllMode){
            return true;
        }
        ArrayList<Double> p = new ArrayList<>(selectionArea.getPoints());
        p.add(selectionArea.getPoints().getFirst());
        p.add(selectionArea.getPoints().get(1));
        for (int i = 1; i < p.size()/2; i++){

            Line2D line = new Line2D.Double(
                    p.get((i-1)*2),
                    p.get((i-1)*2+1),
                    p.get(i*2),
                    p.get((i*2)+1)
            );

            if (line.intersectsLine(new Line2D.Double(x1, y1, x2, y2))){
                return selectAllMode;
            }

        }
        return !selectAllMode && selectionArea.contains(new Point2D(x2, y2));
    }

    @Override
    public void drawHover(GraphicsContext gc,Affine transformation, Affine inverse, Color color) {
        gc.setLineWidth(width*3);
        gc.setStroke(color);
        gc.transform(transformation);
        gc.strokeLine(x1, y1, x2, y2);
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

    public boolean segmentsIntersect(Point2D a, Point2D b, Point2D c, Point2D d) {
        // Standard CCW (Counter-Clockwise) orientation test
        return ccw(a, c, d) != ccw(b, c, d) && ccw(a, b, c) != ccw(a, b, d);
    }

    private boolean ccw(Point2D p1, Point2D p2, Point2D p3) {
        return (p3.getY() - p1.getY()) * (p2.getX() - p1.getX()) >
                (p2.getY() - p1.getY()) * (p3.getX() - p1.getX());
    }

    public String toString(){
        V v = new V(x2-x1, y2-y1);
        DecimalFormat df = new DecimalFormat("#.###");
        return "Line: "+df.format(v.magnitude());
    }




}
