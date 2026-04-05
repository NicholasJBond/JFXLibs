package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.cadpane.CadFeature;
import network.repository.jfxlibs.modules.cadpane.CadLine;
import network.repository.jfxlibs.modules.cadpane.CadPane;
import network.repository.jfxlibs.modules.cadpane.CadPoint;
import network.repository.jfxlibs.modules.layoutpane.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {



    private Text text = new Text();
    @Override
    public void start(Stage stage) throws IOException {


    }

    public void out(String s){
        this.text.setText(s);
    }

    public static void main(String[] args) {
        //launch(args);
    }


}