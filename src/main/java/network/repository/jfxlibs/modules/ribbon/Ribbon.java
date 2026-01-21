package network.repository.jfxlibs.modules.ribbon;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import network.repository.jfxlibs.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

public class Ribbon extends TabPane {
    public final String profileDirectory;
    public final String imageDirectory;

    private ArrayList<String> tabOrder = new ArrayList<>();
    private HashMap<String, ArrayList<Item>> tabs = new HashMap<>();
    public final Consumer<Integer> consumer;
    public final HashMap<Integer, Command> commands = new HashMap<>();

    public Ribbon(String profileDirectory, String imageDirectory, Consumer<Integer> consumer){
        super();
        super.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        super.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/ribbon.css")).toExternalForm());
        super.getStyleClass().add("ribbon");
        this.profileDirectory = profileDirectory;
        this.imageDirectory = imageDirectory;
        this.consumer = consumer;
    }



    public void loadProfile(String name) {

        JSONObject profile = null;

        try {
            profile = Configuration.convertXMLtoJSONObject(Paths
                    .get(Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResource(profileDirectory + "/" + name + ".xml").toURI()).toString());

        } catch (NullPointerException | URISyntaxException i) {
            throw new RuntimeException(i);
        }

        JSONObject itemsObject = profile.getJSONObject("Items");

        JSONArray jsonItems;
        if (itemsObject.get("Item") instanceof JSONArray){
            jsonItems = itemsObject.getJSONArray("Item");
        }else{
            jsonItems = new JSONArray();
            jsonItems.put(itemsObject.getJSONObject("Item"));
        }

        for (Object o : jsonItems){
            if (!(o instanceof JSONObject jsonItem)){
                throw new RuntimeException("Failed to Parse Item " + o.toString());
            }
            Item item = new Item(this, jsonItem);
            if (tabs.get(item.tab) == null) {
                tabs.put(item.tab, new ArrayList<>());
                tabOrder.add(item.tab);
            }

            tabs.get(item.tab).add(item);
        }


        super.getTabs().clear();


        for (String tabName: tabOrder){
            Tab tab = new Tab(tabName);
            HBox hBox = new HBox();
            ArrayList<Group> groups = new ArrayList<>();
            outerloop:
            for (Item item : tabs.get(tabName)){
                for (Group group : groups){
                    if (group.title.equals(item.group)){
                        group.add(item);
                        continue outerloop;
                    }
                }
                Group group = new Group(item.group);
                group.add(item);
                groups.add(group);
                hBox.getChildren().add(group);
            }

            hBox.setPadding(new Insets(7));
            hBox.getStyleClass().add("contents");
            tab.setContent(hBox);
            tab.getStyleClass().add("ribbon-tab-header");
            super.getTabs().add(tab);
        }

    }

}
