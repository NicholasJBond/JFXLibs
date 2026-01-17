package network.repository.jfxlibs.modules.cadpane;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.List;

public class CadPane extends StackPane {
    private Canvas canvas = new Canvas();
    private List<CadFeature> data;
    private Affine transform = new Affine();
    private double lastMouseX;
    private double lastMouseY;

    private Canvas cursorCanvas = new Canvas();
    private final ArrayList<CadFeature> viewElements = new ArrayList<>();


    public CadPane() {

        getChildren().add(canvas);
        getChildren().add(cursorCanvas);
        cursorCanvas.setCursor(Cursor.NONE);

        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        canvas.widthProperty().addListener(e -> redraw());
        canvas.heightProperty().addListener(e -> redraw());

        cursorCanvas.widthProperty().bind(widthProperty());
        cursorCanvas.heightProperty().bind(heightProperty());

        setupEvents();
    }

    public void setupEvents() {
        this.setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.MIDDLE)){
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
            redrawCursor(e);


        });

        this.setOnMouseDragged(e -> {

            if (e.getButton().equals(MouseButton.MIDDLE)) {
                transform.prependTranslation(e.getX() - lastMouseX, e.getY() - lastMouseY);


            }
            lastMouseX = e.getX();
            lastMouseY = e.getY();
            redraw();
            redrawCursor(e);
        });

        // --- ZOOMING (Zoom to Mouse) ---
        this.setOnScroll(e -> {
            double delta = e.getDeltaY();
            double zoomFactor = (delta > 0) ? 1.1 : 1 / 1.1;

            // The key: We must scale relative to the 'Local' coordinates
            // of the node, but append it to the existing transform.
            Point2D pivot = new Point2D(e.getX(), e.getY());

            // Create a temporary scale transform centered at the mouse
            Scale scale = new Scale(zoomFactor, zoomFactor, pivot.getX(), pivot.getY());

            // Prepend this to our existing transform
            transform.prepend(scale);

            redraw();
        });

        this.setOnMouseMoved(e->{
            this.lastMouseX = e.getX();
            this.lastMouseY = e.getY();
            redrawCursor(e);
        });

        this.setOnMouseExited(e ->{
            GraphicsContext gc = cursorCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        this.setOnMouseReleased(e->{

        });
    }

    public void setFeatures(List<CadFeature> data){
        this.data = data;
        redraw();
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setTransform(new Affine());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.RED);
        gc.setStroke(Color.GREEN);

        gc.save();
        gc.setTransform(transform);

        double currentZoom = transform.getMxx();

        // The size you want the point to look like on the screen (e.g., 10px)
        double desiredScreenSize = 1;
        double adjustedSize = desiredScreenSize / currentZoom;


        try {
            // Get the inverse of your current transform
            Affine inverse = transform.createInverse();

            // Transform the top-left (0,0) and bottom-right (width, height)
            // of the canvas to get the world coordinates currently in view.
            Point2D topLeft = inverse.transform(0, 0);
            Point2D bottomRight = inverse.transform(canvas.getWidth(), canvas.getHeight());

            double minX = topLeft.getX();
            double minY = topLeft.getY();
            double maxX = bottomRight.getX();
            double maxY = bottomRight.getY();

            viewElements.clear();
            for (CadFeature feature : data) {
                if (feature.maxX() >= minX && feature.minX() <= maxX && feature.maxY() >= minY && feature.minY() <= maxY) {
                    viewElements.add(feature);
                    feature.draw(gc, adjustedSize);
                }
            }

        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException(e);
        }

        gc.restore();
    }

    private void redrawCursor(MouseEvent e){
        GraphicsContext gc = cursorCanvas.getGraphicsContext2D();
        gc.setTransform(new Affine());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.WHITE);
        gc.strokeLine(lastMouseX+10, lastMouseY,lastMouseX-10, lastMouseY);
        gc.strokeLine(lastMouseX, lastMouseY+10,lastMouseX, lastMouseY-10);

        for (CadFeature feature: viewElements){
            feature.drawHover(e, gc, transform);
        }


    }
}
