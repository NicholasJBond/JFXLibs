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
    private CadPane cadPane = new CadPane(this::out, this::selectionUpdate);

    private void selectionUpdate(ArrayList<CadFeature> cadFeatures) {
        System.out.println("--------------");
        for (CadFeature feature: cadFeatures){
            System.out.println(feature);
        }
    }

    private Text text = new Text();
    @Override
    public void start(Stage stage) throws IOException {
        VBox main = new VBox();
        HBox content = new HBox();
        Ribbon ribbon = new Ribbon("network/repository/jfxlibs/ribbon",
                "network/repository/jfxlibs/images", this::processActions);



        main.getChildren().add(ribbon);
        main.getChildren().add(content);
        main.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/network/repository/jfxlibs/styles/ribbon.css")).toExternalForm());

        ribbon.loadProfile("exampleRibbon");

        TickList list = new TickList("Ticklist");
        list.add(new TestItem());
        list.add(new TestItem());
        list.add(new TestItem());


        Tab tab = new Tab("Main");
        Tab tab2 = new Tab("Not main");
        TabPane cads = new TabPane();
        cads.getTabs().addAll(tab, tab2);

        ArrayList<CadFeature> features = new ArrayList<>();
        features.add(new CadPoint("A", 50, 50, 0));
        features.add(new CadPoint("A", 800, 125, 0));
        features.add(new CadPoint("A", 870, 590, 0));
        features.add(new CadPoint("A", 20, 700, 0));
        features.add(new CadLine(50, 50, 800,125));
        features.add(new CadLine(800,125, 870,590));
        features.add(new CadLine(870,590, 20,700));
        features.add(new CadLine(20,700, 50, 50));


        Random random = new Random();
        CadPoint lastPoint = new CadPoint("A", 10000, 10000,0);
        for (int i = 0; i < 10000; i++){

            features.add(new CadPoint("A",random.nextDouble()*1_000_0000, random.nextDouble()*1_000_0000, 0));
            features.add(new CadLine(features.getLast().maxX(), features.getLast().maxY(),lastPoint.maxX(), lastPoint.maxY()));
            lastPoint = (CadPoint) features.get(features.size()-2);

        }

        cadPane.setFeatures(features);

        tab.setContent(cadPane);

        content.getChildren().add(list);
        content.getChildren().add(cads);
        content.getChildren().add(text);


        HBox.setHgrow(cads, Priority.ALWAYS);

        VBox.setVgrow(content, Priority.ALWAYS);




        Scene scene = new Scene(main, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

    public void out(String s){
        this.text.setText(s);
    }

    public static void main(String[] args) {
        System.exit(0);
//        String fileName = "random_points.csv";
//        int totalRows = 1_000_000;
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            // Write Header
//            writer.write("id,x_coordinate,y_coordinate");
//            writer.newLine();
//
//            for (int i = 1; i <= totalRows; i++) {
//                double x = ThreadLocalRandom.current().nextDouble(-1000.0, 1000.0);
//                double y = ThreadLocalRandom.current().nextDouble(-1000.0, 1000.0);
//
//                writer.write(i + "," + x + "," + y);
//                writer.newLine();
//
//                // Optional: Print progress every 250k rows
//                if (i % 250_000 == 0) {
//                    System.out.println(i + " rows written...");
//                }
//            }
//            System.out.println("Successfully generated " + fileName);
//        } catch (IOException e) {
//            System.err.println("Error writing to file: " + e.getMessage());
//        }
//        launch();
    }

    private void processActions(Integer i){
        switch (i){
            case 0 -> cadPane.setFeatures(CadDataGenerator.createSampleData());
        }
    }
}