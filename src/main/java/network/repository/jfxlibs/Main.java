package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.cadpane.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        Scene scene = new Scene(root, 1600, 900);
        stage.setScene(scene);
        javafx.scene.control.Label label = new Label();

        CadPane pane = new CadPane(label::setText, cadFeatures -> {});
        ArrayList<CadFeature> features = new ArrayList<>();
        features.add(new CadLine(21, 100, 200,500, -600));
        features.add(new CadPoint(0,"Point",0,0,0));
        pane.setFeatures(features);


        root.getChildren().addAll(pane, label);
        stage.show();
        stage.centerOnScreen();


    }

    public static void main(String[] args) {
        launch(args);
    }


}