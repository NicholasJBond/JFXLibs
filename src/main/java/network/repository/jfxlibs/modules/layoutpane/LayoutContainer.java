package network.repository.jfxlibs.modules.layoutpane;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import java.util.function.Consumer;

public class LayoutContainer extends SplitPane implements LayoutItem{
    private Consumer<LayoutItem> onCollapse;
    public LayoutContainer(Orientation orientation){
        super.setOrientation(orientation);
        super.getStyleClass().add("container");

    }

    public void setOnCollapse(Consumer<LayoutItem> onCollapse) {
        this.onCollapse = onCollapse;
    }

    @Override
    public Node toNode() {
        return this;
    }


    public void addItem(int location, LayoutItem item){

        if (!(item.toNode() instanceof LayoutContainer container)){
            item.toNode().getStyleClass().add("item");
        }else{
            container.setOnCollapse(layoutItem->{
                int i = getItems().indexOf(item.toNode());
                getItems().remove(i);
                getItems().add(i, layoutItem.toNode());
            });
        }
        super.getItems().add(location, item.toNode());
    }

    public void removeItem(LayoutItem item){
        super.getItems().remove(item.toNode());
        if (getItems().size() == 1){
            onCollapse.accept((LayoutItem) super.getItems().getFirst());
        }
    }

}
