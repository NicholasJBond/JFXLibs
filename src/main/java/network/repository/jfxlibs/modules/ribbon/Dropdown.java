package network.repository.jfxlibs.modules.ribbon;

import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.layout.VBox;

import java.util.List;

public class Dropdown extends PopupControl {
    private final VBox content;

    public Dropdown(List<Option> options) {
        setAutoHide(true);
        setAutoFix(true);
        setHideOnEscape(true);

        content = new VBox();
        content.getStyleClass().add("dropdown");

        for (Option option : options) {
            option.setOnMouseReleased(event -> {
                option.ribbon.consumer.accept(option.command);
                this.hide();
            });


            content.getChildren().add(option);
        }



    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new Skin<>() {
            @Override public Dropdown getSkinnable() { return Dropdown.this; }
            @Override public Node getNode() { return content; }
            @Override public void dispose() {}
        };
    }
}
