package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.layout.Pane;

import java.util.Objects;

public class LayoutPane extends Pane {
    Slottable view;
    public LayoutPane(Slottable slottable){
        view = slottable;
        getChildren().add(view.toNode());
        super.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/layoutpane.css")).toExternalForm());

    }

    @Override
    public void layoutChildren(){
        this.view.toNode().resizeRelocate(0,0, getWidth(), getHeight());
    }
}
