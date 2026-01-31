package network.repository.jfxlibs.modules.layoutpane;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.*;

public class Panel extends VBox implements Slottable{
    VBox content = new VBox();

    public Panel(String title){
        HBox top = new HBox();
        top.getChildren().add(new Label(title));
        top.setBackground(Background.fill(Color.LIGHTSTEELBLUE));
        getChildren().addAll(top, content);
        getStyleClass().add("panel");

    }

    @Override
    public Node toNode() {
        return this;
    }

    @Override
    public void setText(String s) {
        content.getChildren().add(new Label(s));

    }

    public void setContent(Node content){
        this.getChildren().add(content);
    }


}
