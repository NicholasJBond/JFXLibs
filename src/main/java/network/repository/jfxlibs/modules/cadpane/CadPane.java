package network.repository.jfxlibs.modules.cadpane;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CadPane extends StackPane {
    private Canvas canvas = new Canvas();
    private List<CadFeature> data = new ArrayList<>();
    private Affine transformation = new Affine();
    private double lastMouseX;
    private double lastMouseY;
    private ArrayList<CadFeature> selection = new ArrayList<>();

    private SmartPolygon lassoPolygon = new SmartPolygon();
    private final ArrayList<CadFeature> selectionHover = new ArrayList<>();
    private double[] rectSelect = new double[4];

    private Canvas cursorCanvas = new Canvas();
    private final ArrayList<CadFeature> viewElements = new ArrayList<>();

    private final Consumer<String> out;
    private final Consumer<ArrayList<CadFeature>> selectionUpdate;

    private final ContextMenu contextMenu = new ContextMenu();


    public CadPane(Consumer<String> out, Consumer<ArrayList<CadFeature>> selectionUpdate) {
        this.out = out;
        this.selectionUpdate = selectionUpdate;

        getChildren().add(canvas);
        getChildren().add(cursorCanvas);

        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);  // The radius
        clip.setArcHeight(20); // The radius


        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());


        setClip(clip);

        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        canvas.widthProperty().addListener(e -> redraw());
        canvas.heightProperty().addListener(e -> redraw());

        cursorCanvas.widthProperty().bind(widthProperty());
        cursorCanvas.heightProperty().bind(heightProperty());

        setMinHeight(0);
        setMinWidth(0);

        VBox.setVgrow(this, Priority.ALWAYS);

        setupEvents();




    }

    public ArrayList<CadFeature> getSelection(){
        if(this.data.isEmpty()){return new ArrayList<>();}
        ArrayList<CadFeature> features = new ArrayList<>();
        for (CadFeature feature: data){
            if (feature.selected()){
                features.add(feature);
            }
        }
        return features;
    }

    private void setupEvents() {
        this.setOnMousePressed(e -> {
            contextMenu.hide();

            if (e.getButton().equals(MouseButton.MIDDLE)){
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }


            if (e.getButton().equals(MouseButton.PRIMARY)){
                Affine inverse;
                try {
                    inverse = transformation.createInverse();
                } catch (NonInvertibleTransformException ex) {
                    throw new RuntimeException(ex);
                }

                Point2D cursor = inverse.transform(e.getX(), e.getY());
                double scale = transformation.getMxx();
                double mouseSize = 10 / scale;

                if (!e.isControlDown()){
                    for (CadFeature feature:data){
                        feature.clearSelection();
                    }
                }
                contextMenu.getItems().clear();
                ObservableList<MenuItem> items = contextMenu.getItems();
                for (CadFeature feature: viewElements){
                    if (feature.mouseOver(cursor, mouseSize)){
                        MenuItem item = new MenuItem(feature.toString());
                        item.setOnAction((d)->{
                            feature.select();
                            selectionUpdate.accept(getSelection());
                        });
                        items.add(item);
                    }
                }
                if (contextMenu.getItems().size() >= 2){
                    contextMenu.show(this, e.getScreenX(), e.getScreenY());
                }else if (contextMenu.getItems().size() == 1){
                    contextMenu.getItems().getFirst().fire();
                }

                rectSelect[0] = cursor.getX();
                rectSelect[1] = cursor.getY();
            }
            redrawCursor(e.getX(), e.getY());


        });

        this.setOnMouseDragged(e -> {
            Affine inverse;
            try {
                inverse = transformation.createInverse();
            } catch (NonInvertibleTransformException ex) {
                throw new RuntimeException(ex);
            }

            if (e.getButton().equals(MouseButton.MIDDLE)) {
                transformation.prependTranslation(e.getX() - lastMouseX, e.getY() - lastMouseY);
            }
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                Point2D point = inverse.transform(e.getX(), e.getY());
                lassoPolygon.getPoints().addAll(point.getX(), point.getY());
            }
            if (e.getButton().equals(MouseButton.PRIMARY)){
                Point2D cursor = inverse.transform(e.getX(), e.getY());
                rectSelect[2] = cursor.getX();
                rectSelect[3] = cursor.getY();
            }

            lastMouseX = e.getX();
            lastMouseY = e.getY();
            redraw();
            redrawCursor(e.getX(), e.getY());
        });


        this.setOnScroll(e -> {
            lassoPolygon.getPoints().clear();
            selectionHover.clear();
            double delta = e.getDeltaY();
            double zoomFactor = (delta > 0) ? 1.1 : 1 / 1.1;

            // The key: We must scale relative to the 'Local' coordinates
            // of the node, but append it to the existing transform.
            Point2D pivot = new Point2D(e.getX(), e.getY());

            // Create a temporary scale transform centered at the mouse
            Scale scale = new Scale(zoomFactor, zoomFactor, pivot.getX(), pivot.getY());

            // Prepend this to our existing transform
            transformation.prepend(scale);

            redraw();
            redrawCursor(e.getX(), e.getY());
        });

        this.setOnMouseMoved(e->{
            this.setCursor(Cursor.NONE);
            this.lastMouseX = e.getX();
            this.lastMouseY = e.getY();
            redrawCursor(e.getX(), e.getY());
        });

        this.setOnMouseExited(e ->{
            this.setCursor(Cursor.DEFAULT);
            GraphicsContext gc = cursorCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            redrawCursor(e.getX(), e.getY(), false);

        });

        this.setOnMouseReleased(e->{
            if (!e.isControlDown() && !lassoPolygon.getPoints().isEmpty()){
                for (CadFeature feature:data){
                    feature.clearSelection();
                }
            }
            for (CadFeature feature: selectionHover) {
                feature.select();
            }
            rectSelect = new double[4];
            lassoPolygon.getPoints().clear();
            selectionHover.clear();
            redrawCursor(e.getX(), e.getY());

            selectionUpdate.accept(getSelection());
        });
        Platform.runLater(()->{
            // Inside your View or Controller initialization
            getScene().getWindow().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    // The user switched to Finder or another app
                    this.setCursor(Cursor.DEFAULT);
                } else {
                    // The user clicked back into your assembly program
                    this.setCursor(Cursor.NONE);
                }
            });
            getScene().getWindow().focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                if (!isFocused) {
                    getScene().setCursor(Cursor.DEFAULT);
                }
            });
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
        if(this.data.isEmpty()){return;}

        gc.save();
        gc.setTransform(transformation);

        double currentZoom = transformation.getMxx();

        double desiredScreenSize = 2;
        double adjustedSize = desiredScreenSize / currentZoom;


        try {
            // Get the inverse of your current transform
            Affine inverse = transformation.createInverse();

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

    private void redrawCursor(double x, double y){
        redrawCursor(x, y, true);
    }

    private void redrawCursor(double x, double y, boolean drawCursor){
        StringBuilder s = new StringBuilder();
        for (CadFeature feat: selectionHover){
            s.append("\nLasso Feature: ").append(feat.toString());
        }

        selectionHover.clear();
        GraphicsContext gc = cursorCanvas.getGraphicsContext2D();
        gc.setTransform(new Affine());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (drawCursor){
            gc.setLineDashes(0);
            gc.setLineWidth(1);
            gc.setStroke(Color.WHITE);
            gc.strokeLine(lastMouseX+10, lastMouseY,lastMouseX-10, lastMouseY);
            gc.strokeLine(lastMouseX, lastMouseY+10,lastMouseX, lastMouseY-10);

        }

        Affine inverse;
        try {
            inverse = transformation.createInverse();
        } catch (NonInvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        Point2D cursor = inverse.transform(x, y);
        double scale = transformation.getMxx();
        double mouseSize = 10 / scale;
        for (CadFeature feature: viewElements){
            if (!lassoPolygon.getPoints().isEmpty()){
                if (feature.inSelection(lassoPolygon)){
                    feature.drawHover(gc, transformation, inverse, Color.WHITE);
                    selectionHover.add(feature);
                }
            }
            else if (rectSelect[0] != 0 && rectSelect[2] != 0){
                if (feature.inSelection(rectSelect)){
                    feature.drawHover(gc, transformation, inverse, Color.WHITE);
                    selectionHover.add(feature);
                }
            }else{
                if (feature.mouseOver(cursor, mouseSize)){
                    feature.drawHover(gc, transformation, inverse, Color.WHITE);
                }
            }
       }

        for (CadFeature feature: data){
            if(feature.selected()){
                feature.drawHover(gc, transformation, inverse, Color.YELLOW);
            }
        }

        if (rectSelect[0] != 0 && rectSelect[2] != 0){
            if (rectSelect[0] < rectSelect[2]){
                gc.setLineDashes(0);
                gc.setFill(Color.rgb(0, 120, 215, 0.3));
                gc.setLineWidth(1);
            }else{
                gc.setFill(Color.rgb(40, 200, 0, 0.3));
                gc.setLineWidth(1);
                gc.setLineDashes(5.0);
            }

            Point2D rectStart = transformation.transform(rectSelect[0], rectSelect[1]);
            Point2D rectEnd = transformation.transform(rectSelect[2], rectSelect[3]);
            double[] xvalsrect = new double[] {rectStart.getX(), rectEnd.getX(), rectEnd.getX(), rectStart.getX()};
            double[] yvalsrect = new double[] {rectStart.getY(), rectStart.getY(), rectEnd.getY(), rectEnd.getY()};
            gc.strokePolygon(xvalsrect, yvalsrect, 4);
            gc.fillPolygon(xvalsrect, yvalsrect, 4);
        }



        if (lassoPolygon.getPoints().isEmpty()){
            return;
        }
        gc.setStroke(Color.WHITE);
        if (lassoPolygon.getPoints().size() > 2){
            if (lassoPolygon.getPoints().get(2) > lassoPolygon.getPoints().get(0)){
                gc.setFill(Color.rgb(0, 120, 215, 0.3));
                gc.setLineDashes(0);
                gc.setLineWidth(1);
            }else{
                gc.setFill(Color.rgb(40, 200, 0, 0.3));
                gc.setLineWidth(1);
                gc.setLineDashes(5.0);
            }
        }



        int n = lassoPolygon.getPoints().size()/2;
        double[] xvals = new double[n];
        double[] yvals = new double[n];

        for (int i = 0; i < n; i++){
            Point2D p = transformation.transform(lassoPolygon.getPoints().get(i*2),lassoPolygon.getPoints().get(i*2+1));
            xvals[i] = p.getX();
            yvals[i] = p.getY();
        }

        gc.setFillRule(FillRule.EVEN_ODD);
        gc.strokePolygon(xvals, yvals, n);
        gc.fillPolygon(xvals, yvals, n);




    }


}
