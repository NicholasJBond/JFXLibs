package network.repository.jfxlibs.modules.ticklist;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class ListSingleSelection extends VBox {
    public ArrayList<String> options = new ArrayList<>();
    public String selected = "";
    private VBox content = new VBox();
    private Runnable onUpdate;
    public ListSingleSelection(Runnable onUpdate){
        super.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/listsingleselection.css")).toExternalForm());
        super.getStyleClass().add("thebox");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(content);
        content.getStyleClass().add("content");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        VBox.setVgrow(content, Priority.ALWAYS);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        super.getChildren().add(scrollPane);
        this.onUpdate = onUpdate;

    }

    public void update(){
        content.getChildren().clear();
        for (String option:options){
            HBox hBox = new HBox();
            hBox.getStyleClass().add("option");
            hBox.getChildren().add(new Text(option));
            hBox.setOnMouseClicked((e)->{
                selected = option;
                for (Node n: content.getChildren()){
                    if (n instanceof HBox hBox1){
                        if (selected.equals(((Text) hBox1.getChildren().getFirst()).getText())){
                            hBox1.getStyleClass().add("option_selected");
                        }else{
                            hBox1.getStyleClass().clear();
                            hBox1.getStyleClass().add("option");
                        }
                    }
                }

                onUpdate.run();

            });


            content.getChildren().add(hBox);
        }
        Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        content.getChildren().add(region);
    }
}
