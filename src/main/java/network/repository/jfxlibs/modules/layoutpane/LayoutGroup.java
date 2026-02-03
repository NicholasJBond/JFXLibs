package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import java.util.ArrayList;

public class LayoutGroup extends SplitPane implements Slottable{
    private ArrayList<Slottable> slots = new ArrayList<>();

    public void add(Slottable s){
        slots.add(s);
        getChildren().add(s.toNode());
    }

    @Override
    public Node toNode() {
        return this;
    }

    @Override
    public void setText(String s) {

    }
}
