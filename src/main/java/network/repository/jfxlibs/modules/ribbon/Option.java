package network.repository.jfxlibs.modules.ribbon;

import javafx.scene.image.Image;
import network.repository.jfxlibs.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

public class Option {
    public final String label;
    public final Image image;
    public final int command;
    public final Ribbon ribbon;

    public Option(Ribbon ribbon, JSONObject option) {
        label = option.getString("Label");
        image = Configuration.toImage(ribbon.imagePath + option.getString("Image").toLowerCase() + ".png");
        command = option.getInt("Command");
        this.ribbon = ribbon;
    }
}
