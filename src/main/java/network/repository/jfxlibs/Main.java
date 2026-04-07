package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.ticklist.ListSingleSelection;

import java.io.IOException;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1600, 900);
        stage.setScene(scene);

        ListSingleSelection list = new ListSingleSelection((()->{}));
        list.options.add("Option 1");
        list.options.add("Option 2");
        list.options.add("Option 3");
        list.options.add("Option 4");
        list.update();

        root.getChildren().add(list);
        stage.show();
        stage.centerOnScreen();


    }

    public static void main(String[] args) {
        launch(args);
    }


}