package network.repository.jfxlibs.modules.ticklist;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TickListCell extends ListCell<TickListItem> {


        private final Label nameLabel = new Label();
        private final Region region = new Region();
        private final CheckBox checkBox = new CheckBox();
        private final HBox hbox = new HBox(10, nameLabel,region,checkBox);

    public TickListCell() {
        hbox.getStyleClass().add("item");
        HBox.setHgrow(region, Priority.ALWAYS);
        this.setOnMouseClicked((event)->{
            switch (event.getButton()){
                case MIDDLE -> {}
                case PRIMARY -> {
                    if (isEmpty()){return;}
                    getItem().activeProperty().setValue(!getItem().activeProperty().get());

                    updateItem(getItem(), false);
                }
                case SECONDARY -> {}
            }
        });
    }

    @Override
        protected void updateItem(TickListItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            nameLabel.setText(item.getText());
            checkBox.selectedProperty().bind(item.activeProperty());
            checkBox.setOnAction(Event::consume);
            setGraphic(hbox);
        }
    }

}
