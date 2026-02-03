package network.repository.jfxlibs;

import javafx.application.Application;
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
        VBox vBox = new VBox();


        CadPane cad = new CadPane((o)->{}, (r)->{});



        ArrayList<CadFeature> data = new ArrayList<>();
        data.add(new CadLine(10, 0,0,100,500));
        data.add(new CadPoint(10, "Name",250,100,500));
        cad.setFeatures(data);



        CadPane cad2 = new CadPane((o)->{}, (r)->{});
        CadPane cad3 = new CadPane((o)->{}, (r)->{});
        data.add(new CadPoint(10, "Name2",250,450,500));

        cad2.setFeatures(data);

        LayoutPanel panel = new LayoutPanel("CAD");
        panel.setContent(cad);
        cad.setPrefSize(500, 500);


        LayoutPanel panel2 = new LayoutPanel("Something else");
        panel2.setContent(cad2);
        cad2.setPrefSize(500, 500);

        LayoutPanel panel3 = new LayoutPanel("Nahahahahha");
        panel3.setContent(cad3);
        cad3.setPrefSize(500, 500);

        cad3.setFeatures(data);

        LayoutGroup group = new LayoutGroup();
        group.add(panel);
        group.add(panel2);
        group.add(panel3);

        LayoutPane layoutPane = new LayoutPane(group);
        vBox.getChildren().add(layoutPane);
        VBox.setVgrow(layoutPane, Priority.ALWAYS);

        stage.setWidth(1000);
        stage.setHeight(1000);
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