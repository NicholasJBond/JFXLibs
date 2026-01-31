package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.layout.Pane;

import java.util.Objects;

public class LayoutPane extends Pane {
    LayoutSlot view;
    public LayoutPane(Slottable slottable){
        view = new LayoutSlot(slottable);
        getChildren().add(view);
        super.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/layoutpane.css")).toExternalForm());

    }

    @Override
    public void layoutChildren(){
        this.view.resizeRelocate(0,0, getWidth(), getHeight());
    }
}
