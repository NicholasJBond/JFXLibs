package network.repository.jfxlibs.modules.ribbon;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import network.repository.jfxlibs.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

public class Option extends HBox implements Command{
    public final Label label;
    public final Image image;
    public final int command;
    public final Ribbon ribbon;

    public Option(Ribbon ribbon, JSONObject option) {
        label = new Label(option.getString("Label"));
        image = Configuration.toImage(ribbon.imagePath + option.getString("Image").toLowerCase() + ".png");
        command = option.getInt("Command");
        this.ribbon = ribbon;

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(12);
        imageView.setFitWidth(12);


        this.getChildren().addAll(imageView, label);
        this.getStyleClass().add("option");

        ribbon.commands.put(command, this);

    }

    @Override
    public void setLabel(String string) {
        label.setText(string);
    }

    @Override
    public void disable() {
        super.setDisable(true);
    }

    @Override
    public int getCommand() {
        return command;
    }

    @Override
    public void enable() {
        super.setDisable(false);
    }
}
