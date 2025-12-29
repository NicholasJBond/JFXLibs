package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.ribbon.Ribbon;
import network.repository.jfxlibs.modules.ticklist.TickList;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox main = new VBox();
        HBox content = new HBox();
        Ribbon ribbon = new Ribbon("network/repository/jfxlibs/ribbon",
                "network/repository/jfxlibs/images", integer -> {
            String colour;
            switch (integer){
                case 1 -> colour = "#980000";
                case 2 -> colour = "#8e4800";
                case 3 -> colour = "green";
                case 4 -> colour = "#186aff";
                case 5 -> colour = "#001395";
                case 6 -> colour = "#dc0026";
                case 7 -> colour = "#5408ac";
                default -> colour = "black";
            }

            main.setStyle("-fx-accent:"+ colour +"; -fx-focus-color:transparent; -fx-faint-focus-color:transparent;");
        });



        main.getChildren().add(ribbon);
        main.getChildren().add(content);
        main.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/ribbon.css")).toExternalForm());

        ribbon.loadProfile("exampleRibbon");

        TickList list = new TickList("Setups");
        list.add(new TestItem());
        list.add(new TestItem());
        list.add(new TestItem());



        content.getChildren().add(list);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        content.getChildren().add(region);




        ribbon.commands.get(3).disable();

        Scene scene = new Scene(main, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}