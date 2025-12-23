package network.repository.jfxlibs.modules.ribbon;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import network.repository.jfxlibs.exceptions.BadTypeException;
import network.repository.jfxlibs.exceptions.ContainerOverflowException;

import java.util.ArrayList;

public class Section extends VBox {
    public enum Type{
        LARGE, SMALL
    }

    public final Type type;
    public Section(Type type) {
        this.type = type;
        getStyleClass().add("section");
        this.setMaxHeight(Double.MAX_VALUE);

    }

    public void add(Item item){
        switch (this.type){
            case LARGE -> {
                if (!this.getChildren().isEmpty()){
                    throw new ContainerOverflowException("Max items in section is 1");
                }
                if (!(item.type == Item.Type.LARGE || item.type == Item.Type.LARGE_DROPDOWN || item.type == Item.Type.LARGE_WITH_DROPDOWN)){
                    throw new BadTypeException("Type must be LARGE or LARGE_DROPDOWN");
                }
            }
            case SMALL -> {
                if (this.getChildren().size() > 2){
                    throw new ContainerOverflowException("Max items in section is 3");
                }
                if (!(item.type == Item.Type.SMALL || item.type == Item.Type.SMALL_DROPDOWN || item.type == Item.Type.SMALL_WITH_DROPDOWN)){
                    throw new BadTypeException("Type must be SMALL or SMALL_DROPDOWN");
                }
            }
        }
        this.getChildren().add(item);
    }

    public boolean isFull() {
        switch (this.type) {
            case LARGE -> {
                return !getChildren().isEmpty();
            }
            case SMALL -> {
                return getChildren().size() > 2;
            }
            case null, default -> {
                return false;
            }
        }

    }
}
