package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.layout.VBox;

import java.util.Objects;

public class LayoutPane extends VBox {
    private LayoutItem content;
    public LayoutPane(LayoutItem content){
        this.content = content;
        super.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/layoutpane.css")).toExternalForm());

    }



}
