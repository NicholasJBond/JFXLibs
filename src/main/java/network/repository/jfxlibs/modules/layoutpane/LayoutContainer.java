package network.repository.jfxlibs.modules.layoutpane;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class LayoutContainer extends SplitPane implements LayoutItem{
    private Consumer<LayoutItem> onCollapse;
    private ArrayList<LayoutItem> items = new ArrayList<>();
    private String id = "container@"+System.identityHashCode(this);
    public LayoutContainer(Orientation orientation){
        super.setOrientation(orientation);
        super.getStyleClass().add("container");

    }

    public LayoutContainer(Orientation orientation, String id){
        super.setOrientation(orientation);
        super.getStyleClass().add("container");
        this.id = id;
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
        items.add(item);
    }

    public void removeItem(LayoutItem item){
        super.getItems().remove(item.toNode());
        items.remove(item);
        if (getItems().size() == 1){
            onCollapse.accept((LayoutItem) super.getItems().getFirst());
        }
    }

    public String getLayoutId(){
        return id;
    }

    public boolean contains(LayoutItem layoutItem){
        for (LayoutItem item: items){
            if (layoutItem.getLayoutId().equals(item.getLayoutId())){
                return true;
            }
        }
        return false;
    }

}
