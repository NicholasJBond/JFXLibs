package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.Ribbon;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane main = new AnchorPane();

        Ribbon ribbon = new Ribbon();
        main.getChildren().add(ribbon);
        AnchorPane.setTopAnchor(ribbon, 200.0);
        AnchorPane.setLeftAnchor(ribbon, 50.0);
        main.setStyle("-fx-background-color: lightgray");

        Scene scene = new Scene(main, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}