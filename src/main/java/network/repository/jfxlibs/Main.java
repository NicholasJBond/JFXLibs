package network.repository.jfxlibs;

import com.techsenger.tabpanepro.core.TabPanePro;
import com.techsenger.tabpanepro.core.skin.TabHeaderAreaPolicy;
import com.techsenger.tabpanepro.core.skin.TabPaneProSkin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.cadpane.CadFeature;
import network.repository.jfxlibs.modules.cadpane.CadLine;
import network.repository.jfxlibs.modules.cadpane.CadPane;
import network.repository.jfxlibs.modules.cadpane.CadPoint;
import network.repository.jfxlibs.modules.layoutpane.HorizontalGroup;
import network.repository.jfxlibs.modules.layoutpane.LayoutPane;
import network.repository.jfxlibs.modules.layoutpane.LayoutSlot;
import network.repository.jfxlibs.modules.layoutpane.Panel;

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

        Panel panel = new Panel("CAD");
        panel.setContent(cad);
        cad.setPrefSize(500, 500);


        Panel panel2 = new Panel("Something else");
        panel2.setContent(cad2);
        cad2.setPrefSize(500, 500);

        Panel panel3 = new Panel("Nahahahahha");
        panel3.setContent(cad3);
        cad3.setPrefSize(500, 500);

        cad3.setFeatures(data);

        HorizontalGroup group = new HorizontalGroup();
        group.addSlot(0, new LayoutSlot(panel));
        group.addSlot(1, new LayoutSlot(panel2));
        group.addSlot(2, new LayoutSlot(panel3));

        LayoutPane layoutPane = new LayoutPane(group);
        vBox.getChildren().add(layoutPane);
        VBox.setVgrow(layoutPane, Priority.ALWAYS);

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