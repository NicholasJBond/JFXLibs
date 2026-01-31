package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.function.Consumer;

public class HorizontalGroup extends Pane implements Slottable{
    ArrayList<LayoutSlot> slots = new ArrayList<>();

    public void addSlot(int index, LayoutSlot slot){
        slot.setPercent((double) 1/(slots.size()+1));
        for (LayoutSlot s:slots){
            s.setPercent(s.getPercent()*(1-slot.getPercent()));
        }
        slots.add(index, slot);

        for (LayoutSlot s:slots) {
            updateSlotResizingRules(s);
        }

        slot.setDragConsumer(dragManager -> {

            if (!dragManager.eastWest){
                return;
            }

            double scale = slot.getWidth()/slot.getPercent();
            double newWidth = slot.getWidth() + dragManager.calcDistance();
            dragManager.slot.setPercent(newWidth/scale);
            layoutChildrenGhost();
        });

        setOnMouseMoved(e->{
            setCursor(Cursor.H_RESIZE);
        });


    }

    public void updateSlotResizingRules(LayoutSlot s){
        int i = slots.indexOf(s);

        if (slots.isEmpty() || slots.size() == 1){
            s.updateResizingRules(false, false, false,false);
            return;
        }

        if (i == 0){
            s.updateResizingRules(false,true, false, false);
            return;
        }

        if (i == slots.size()-1){
            s.updateResizingRules(false,false, false, true);
            return;
        }


        s.updateResizingRules(false, true, false, true);

    }

    private void layoutChildrenGhost(){


        double cursor = 0;
        for (LayoutSlot slot:slots){
            double width = slot.getPercent()*getWidth();

            slot.resizeRelocate(cursor, 0, width, getHeight());
            cursor+= width;
        }
    }
    @Override
    public void layoutChildren(){
        getChildren().clear();
        getChildren().addAll(slots);
        layoutChildrenGhost();


    }


    @Override
    public Node toNode() {
        return this;
    }

    @Override
    public void setText(String s) {

    }
}
