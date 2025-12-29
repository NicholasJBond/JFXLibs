package network.repository.jfxlibs.modules.ribbon;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import network.repository.jfxlibs.Configuration;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Item extends HBox implements Command{

    public enum Type{
        LARGE, LARGE_DROPDOWN, LARGE_WITH_DROPDOWN, SMALL, SMALL_WITH_DROPDOWN, SMALL_DROPDOWN
    }


    public final Label label;
    public final Image image;
    public final String tab;
    public final String group;
    public final Type type;
    public final Integer command;
    public final ArrayList<Option> options = new ArrayList<Option>();
    public final Ribbon ribbon;

    public Item(Ribbon ribbon, JSONObject jsonItem) {
        this.ribbon = ribbon;
        this.label = new Label(jsonItem.getString("Label"));

        this.image = Configuration.toImage(ribbon.imageDirectory + "/" + jsonItem.getString("Image").toLowerCase() + ".png");

        this.tab = jsonItem.getString("Tab");
        this.group = jsonItem.getString("Group");
        this.type = Type.valueOf(jsonItem.getString("Type").toUpperCase());
        if ((type.equals(Type.LARGE_DROPDOWN) || type.equals(Type.SMALL_DROPDOWN))){
            this.command = null;
        }else{
            this.command = jsonItem.getInt("Command");
        }

        ribbon.commands.put(command, this);


        switch (type){
            case LARGE_DROPDOWN, SMALL_DROPDOWN ->{
                JSONObject joptions = jsonItem.getJSONObject("Options");
                if(joptions.get("Option") instanceof JSONObject J){
                    options.add(new Option(ribbon, J));
                }else{
                    for (Object o: joptions.getJSONArray("Option")){
                        if (o instanceof JSONObject jsonObject){
                            options.add(new Option(ribbon, jsonObject));
                        }
                    }
                }
            }
            case  LARGE_WITH_DROPDOWN, SMALL_WITH_DROPDOWN ->{
                JSONObject object = new JSONObject();
                object.put("Label", this.label.getText());
                object.put("Image", jsonItem.getString("Image"));
                object.put("Command", this.command);
                options.add(new Option(ribbon, object));
                JSONObject joptions = jsonItem.getJSONObject("Options");
                if(joptions.get("Option") instanceof JSONObject J){
                    options.add(new Option(ribbon, J));
                }else{
                    for (Object o: joptions.getJSONArray("Option")){
                        if (o instanceof JSONObject jsonObject){
                            options.add(new Option(ribbon, jsonObject));
                        }
                    }
                }


            }
            default -> {

            }
        }


        this.getStyleClass().add("item");


        ImageView imageView = new ImageView(this.image);
        switch (type){
            case LARGE -> {
                VBox casing = new VBox();

                VBox button = new VBox();
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                button.getChildren().addAll(imageView, label);
                button.getStyleClass().add("button");
                button.setStyle("-fx-alignment: center;-fx-padding: 1 10 1 10;-fx-effect: null;");
                button.setOnMouseReleased(event -> {ribbon.consumer.accept(command);});

                VBox.setVgrow(button, Priority.ALWAYS);
                casing.setMaxHeight(Double.MAX_VALUE);
                casing.getChildren().add(button);
                this.getChildren().add(casing);
                this.setMaxWidth(Double.MAX_VALUE);
                VBox.setVgrow(this, Priority.ALWAYS);

            }

            case LARGE_WITH_DROPDOWN -> {
                VBox casing = new VBox();

                VBox button = new VBox();
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                button.getChildren().addAll(imageView);
                button.getStyleClass().add("button");
                button.setStyle("-fx-alignment: center;-fx-padding: 5 10 1 10;-fx-effect: null;");
                button.setOnMouseReleased(event -> {ribbon.consumer.accept(command);});

                label.setText(label.getText() + " ▼");
                HBox dropdown = new HBox();
                dropdown.getChildren().add(label);
                dropdown.getStyleClass().add("button");
                dropdown.setOnMouseReleased(event -> {launchPopup();});

                VBox.setVgrow(button, Priority.ALWAYS);
                casing.setMaxHeight(Double.MAX_VALUE);
                casing.getChildren().addAll(button, dropdown);
                this.getChildren().add(casing);
                this.setMaxWidth(Double.MAX_VALUE);
                VBox.setVgrow(this, Priority.ALWAYS);

            }

            case LARGE_DROPDOWN -> {
                VBox casing = new VBox();

                VBox button = new VBox();
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);

                button.getStyleClass().add("button");
                button.setStyle("-fx-alignment: center;-fx-padding: 5 10 1 10;-fx-effect: null;");
                label.setText(label.getText() + " ▼");
                HBox dropdown = new HBox();
                dropdown.getChildren().add(label);
                button.getChildren().addAll(imageView, label);
                button.setOnMouseReleased(event -> {launchPopup();});

                VBox.setVgrow(button, Priority.ALWAYS);
                casing.setMaxHeight(Double.MAX_VALUE);
                casing.getChildren().addAll(button, dropdown);
                this.getChildren().add(casing);
                this.setMaxWidth(Double.MAX_VALUE);
                VBox.setVgrow(this, Priority.ALWAYS);
            }
            case SMALL -> {
                HBox casing = new HBox();

                HBox button = new HBox();
                imageView.setFitHeight(12);
                imageView.setFitWidth(12);
                button.getChildren().addAll(imageView, label);
                button.getStyleClass().add("button");
                button.setOnMouseReleased(event -> {ribbon.consumer.accept(command);});
                button.setStyle("-fx-spacing: 5;-fx-alignment: center-left;");


                HBox.setHgrow(button, Priority.ALWAYS);
                HBox.setHgrow(casing, Priority.ALWAYS);

                casing.getChildren().add(button);
                this.getChildren().add(casing);
                this.setMaxWidth(Double.MAX_VALUE);

            }
            case SMALL_WITH_DROPDOWN-> {
                HBox casing = new HBox();

                HBox button = new HBox();
                imageView.setFitHeight(12);
                imageView.setFitWidth(12);
                button.getChildren().addAll(imageView, label);
                button.getStyleClass().add("button");
                button.setOnMouseReleased(event -> {ribbon.consumer.accept(command);});
                button.setStyle("-fx-spacing: 5;-fx-alignment: center-left;");



                HBox dropdown = new HBox();
                dropdown.getChildren().add(new Label("▼"));
                dropdown.getStyleClass().add("button");
                dropdown.setOnMouseReleased(event -> {launchPopup();});


                HBox.setHgrow(button, Priority.ALWAYS);
                HBox.setHgrow(casing, Priority.ALWAYS);

                casing.getChildren().addAll(button, dropdown);
                this.getChildren().add(casing);
                this.setMaxWidth(Double.MAX_VALUE);
            }

            case SMALL_DROPDOWN -> {
                HBox casing = new HBox();

                HBox button = new HBox();
                imageView.setFitHeight(12);
                imageView.setFitWidth(12);
                Region region = new Region();
                HBox.setHgrow(region, Priority.ALWAYS);
                button.getChildren().addAll(imageView, label, region,new Label("▼"));
                button.getStyleClass().add("button");
                button.setStyle("-fx-spacing: 5;-fx-alignment: center-left;");
                button.setOnMouseReleased(event -> {launchPopup();});


                HBox.setHgrow(button, Priority.ALWAYS);
                HBox.setHgrow(casing, Priority.ALWAYS);

                casing.getChildren().addAll(button);
                this.getChildren().add(casing);
                this.setMaxWidth(Double.MAX_VALUE);

            }
            default -> {
                this.getChildren().add(new Label("Unknown"));
            }
        }

    }

    public String getGroup() {
        return group;
    }

    public String getTab() {
        return tab;
    }

    public void launchPopup(){
        if (type.equals(Type.SMALL) || type.equals(Type.LARGE)){
            return;
        }

        PopupControl popup = new Dropdown(options);
        popup.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/ribbon.css")).toExternalForm());

        Bounds bounds = this.localToScreen(this.getBoundsInLocal());
        popup.show(this, bounds.getMinX(), bounds.getMaxY());

    }

    @Override
    public void setLabel(String string) {
        switch (type) {
            case LARGE_DROPDOWN, LARGE_WITH_DROPDOWN -> {
                label.setText(string + " ▼");
            }
            default -> {
                label.setText(string);
            }
        }
    }

    @Override
    public void disable() {
        super.setDisable(true);
    }

    @Override
    public void enable() {
        super.setDisable(false);
    }

    @Override
    public int getCommand(){
        return command;
    }


}
