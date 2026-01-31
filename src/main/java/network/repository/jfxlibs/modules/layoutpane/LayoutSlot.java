package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.Cursor;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.function.Consumer;

public class LayoutSlot extends Pane {
    private Slottable content;

    private boolean north;
    private boolean east;
    private boolean south;
    private boolean west;

    private double percent = 1;

    private DragManager dragManager = new DragManager();
    private Consumer<DragManager> dm;


    public LayoutSlot(Slottable content){
        super.getStyleClass().add("slot");

        this.content = content;
        getChildren().add(content.toNode());

        setOnMouseMoved(e->{
            setCursor(Cursor.DEFAULT);
            double selectRange = 10;

            if (north) {
                if (e.getY() > (-selectRange) && e.getY() < (selectRange)){
                    setCursor(Cursor.E_RESIZE);
                }
            }
            if (east){
                if (e.getX() > (getWidth()-selectRange) && e.getX() < (getWidth() + selectRange)){
                    setCursor(Cursor.E_RESIZE);
                }
            }
            if (south){
                if (e.getY() > (getHeight()-selectRange) && e.getY() < (getHeight() + selectRange)){
                    setCursor(Cursor.E_RESIZE);
                }
            }
            if (west){
                if (e.getX() > (-selectRange) && e.getX() < (selectRange)){
                    setCursor(Cursor.W_RESIZE);
                }
            }
        });

        setOnMousePressed(e->{
            double selectRange = 10;

            if (north) {
                if (e.getY() > (-selectRange) && e.getY() < (selectRange)){
                    dragManager.x = e.getX();
                    dragManager.y = e.getY();
                    dragManager.eastWest = false;
                }
            }
            if (east){
                if (e.getX() > (getWidth()-selectRange) && e.getX() < (getWidth() + selectRange)){
                    dragManager.x = e.getX();
                    dragManager.y = e.getY();
                    dragManager.eastWest = true;

                }
            }
            if (south){
                if (e.getY() > (getHeight()-selectRange) && e.getY() < (getHeight() + selectRange)){
                    dragManager.x = e.getX();
                    dragManager.y = e.getY();
                    dragManager.eastWest = false;

                }
            }
            if (west){
                if (e.getX() > (-selectRange) && e.getX() < (selectRange)){
                    dragManager.x = e.getX();
                    dragManager.y = e.getY();
                    dragManager.eastWest = true;

                }
            }

        });





    }

    void dragDetected(MouseEvent e){
        dragManager.xEnd = e.getX();
        dragManager.yEnd = e.getY();
        dragManager.slot = this;
        dm.accept(dragManager);
    }

    void setDragConsumer(Consumer<DragManager> dm){
        this.dm = dm;
        setOnDragDetected(this::dragDetected);

    }

    @Override
    public void layoutChildren(){
        double width = getWidth();
        double height = getHeight();
        double x = 0;
        double y = 0;

        double borderSize = 2;
        if (east && west){
            width -= 2* borderSize;
            x += borderSize;
        }else if (east){
            width -= borderSize;
        }else if (west){
            width -= borderSize;
            x += borderSize;
        }

        if (north && south){
            height -= 2* borderSize;
            y += borderSize;
        }else if (north){
            height -= borderSize;
        }else if (south){
            height -= borderSize;
            y += borderSize;
        }

        this.content.toNode().resizeRelocate(x,y, width, height);
    }

    public void updateResizingRules(boolean north, boolean east, boolean south, boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isEast() {
        return east;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

    public Slottable getContent() {
        return content;
    }
}
