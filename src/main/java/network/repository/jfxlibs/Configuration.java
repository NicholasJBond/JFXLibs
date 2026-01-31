package network.repository.jfxlibs;

import javafx.scene.image.Image;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Configuration {
    public static JSONObject convertXMLtoJSONObject(InputStream is) {
        if (is == null) {
            throw new RuntimeException("Input stream is null. Check if the resource path is correct.");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = br.readLine()) != null) {
                content.append(line);
            }

            return XML.toJSONObject(content.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read XML content", e);
        }
    }

    public static Image toImage(String path){


        try {
            URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource(path);
            if (resourceUrl == null) {
                throw new IllegalArgumentException("File not found: " + path);
            }
            return new Image(resourceUrl.toExternalForm());

        } catch (NullPointerException i) {
            throw new RuntimeException(i);
        }

    }
}
