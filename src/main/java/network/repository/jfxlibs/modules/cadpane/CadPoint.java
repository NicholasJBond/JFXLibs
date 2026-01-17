package network.repository.jfxlibs.modules.cadpane;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;


public class CadPoint implements CadFeature{
    private final String name;
    private final double x;
    private final double z;
    private final double y;
    private double radius = 2;
    public CadPoint(String name, double x, double y, double z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void draw(GraphicsContext gc, double value) {
        radius = value;
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
    public boolean drawHover(MouseEvent e, GraphicsContext gc, Affine transformation) {
        Point2D scenePoint = new Point2D(e.getX(), e.getY());

        try {
            Point2D worldPoint = transformation.inverseTransform(scenePoint);
            if (worldPoint.getX() > minX()-20 && worldPoint.getX() < maxX() + 20 && worldPoint.getY() > minY()-20 && worldPoint.getY() < maxY()+20){
                gc.setFill(Color.YELLOW);
                double newRadius = radius * 3;
                Point2D point = transformation.transform(x, y);
                gc.fillOval(point.getX(), point.getY() , newRadius * 2, newRadius * 2);
                return true;
            }else{
                return false;

            }
        } catch (NonInvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public boolean isClicked(double x, double y) {

        return false;
    }
}
