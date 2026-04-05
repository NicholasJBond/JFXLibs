package network.repository.jfxlibs.modules.ticklist;

import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class TickList extends VBox {
    private final Text text = new Text();
    private final ListView<TickListItem> list = new ListView<>();
    public TickList(String title){
        text.setText(title);
        text.getStyleClass().add("title");
        list.setCellFactory(lv -> new TickListCell());
        list.setSelectionModel(null);
        list.setFocusTraversable(false);
        this.setPrefWidth(400);


        this.getChildren().add(text);
        this.getChildren().add(list);
        VBox.setVgrow(list, Priority.ALWAYS);


        this.getStyleClass().add("ticklist");
        super.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/ticklist.css")).toExternalForm());

    }

    public void add(TickListItem item){
        list.getItems().add(item);
    }
    public void add(TickListItem... item){
        list.getItems().addAll(item);
    }
    public void add(ArrayList<TickListItem> items){
        list.getItems().addAll(items);
    }
    public void clear(){
        this.list.getItems().clear();
    }
}
