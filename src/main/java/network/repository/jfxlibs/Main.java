package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.ribbon.Ribbon;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox main = new VBox();

        Ribbon ribbon = new Ribbon( "/network/repository/jfxlibs/configuration/ribbon/",
                "/network/repository/jfxlibs/images/", integer -> {
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

        ribbon.loadProfile("exampleRibbon");

        Scene scene = new Scene(main, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
    }
}