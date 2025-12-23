package network.repository.jfxlibs;

import javafx.scene.image.Image;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Configuration {
    public static JSONObject convertXMLtoJSONObject(String path){
        File file = new File(Objects.requireNonNull(Configuration.class.getResource((path))).getFile());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = br.readLine()) != null) {
                content.append(line);
            }

            return XML.toJSONObject(content.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image toImage(String path){
        return new Image(String.valueOf(Configuration.class
                .getResource(path)));
    }
}
