package network.repository.jfxlibs.modules.ribbon;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Objects;

public class Dropdown extends PopupControl {
    private final VBox content;

    public Dropdown(List<Option> options) {
        setAutoHide(true);
        setAutoFix(true);
        setHideOnEscape(true);

        content = new VBox();
        content.getStyleClass().add("dropdown");

        for (Option option : options) {
            ImageView imageView = new ImageView(option.image);
            imageView.setFitHeight(12);
            imageView.setFitWidth(12);

            HBox button = new HBox(imageView, new Label(option.label));
            button.getStyleClass().add("option");
            button.setOnMouseReleased(event -> {
                option.ribbon.consumer.accept(option.command);
                this.hide();
            });


            content.getChildren().add(button);
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
