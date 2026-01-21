package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.cadpane.*;
import network.repository.jfxlibs.modules.ribbon.Ribbon;
import network.repository.jfxlibs.modules.ticklist.TickList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Main extends Application {



    private Text text = new Text();
    @Override
    public void start(Stage stage) throws IOException {

    }

    public void out(String s){
        this.text.setText(s);
    }

    public static void main(String[] args) {


        System.exit(0);
    }


}