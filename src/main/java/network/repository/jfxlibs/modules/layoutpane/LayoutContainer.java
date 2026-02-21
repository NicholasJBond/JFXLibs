package network.repository.jfxlibs.modules.layoutpane;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

public class LayoutContainer extends SplitPane implements LayoutItem{
    public LayoutContainer(Orientation orientation){
        super.setOrientation(orientation);
        super.getStyleClass().add("container");
    }

    @Override
    public Node toNode() {
        return this;
    }

    public void addContainer(int location, LayoutItem item){
        super.getChildren().add(location, item.toNode());
    }
}
