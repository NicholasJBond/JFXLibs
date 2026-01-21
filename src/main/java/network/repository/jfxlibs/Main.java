package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.cadpane.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {



    private Text text = new Text();
    @Override
    public void start(Stage stage) throws IOException {
        VBox vBox = new VBox();

        CadPane cad = new CadPane((o)->{}, (r)->{});
        vBox.getChildren().add(cad);

        ArrayList<CadFeature> data = new ArrayList<>();
        data.add(new CadLine(10, 0,0,100,500));
        data.add(new CadPoint(10, "Name",250,100,500));
        cad.setFeatures(data);

        cad.setPrefWidth(900);
        cad.setPrefHeight(900);
        VBox.setVgrow(cad, Priority.ALWAYS);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public void out(String s){
        this.text.setText(s);
    }

    public static void main(String[] args) {
        launch(args);
    }


}