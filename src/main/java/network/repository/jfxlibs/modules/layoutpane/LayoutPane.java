package network.repository.jfxlibs.modules.layoutpane;

import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class LayoutPane extends VBox {
    private final LayoutContainer content = new LayoutContainer(Orientation.HORIZONTAL);
    public LayoutPane(){
        super.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/layoutpane.css")).toExternalForm());

        setStyle("-fx-padding: 3; ");

        content.toNode().getStyleClass().add("content");

        content.setOnCollapse((i) -> {
        });

        super.getChildren().add(content.toNode());
        VBox.setVgrow(content.toNode(), Priority.ALWAYS);
    }

    public LayoutContainer getContent() {
        return content;
    }
}
