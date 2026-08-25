package network.repository.jfxlibs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import network.repository.jfxlibs.modules.cadpane.*;
import network.repository.jfxlibs.modules.layoutpane.LayoutContainer;
import network.repository.jfxlibs.modules.layoutpane.LayoutItem;
import network.repository.jfxlibs.modules.layoutpane.LayoutPane;
import network.repository.jfxlibs.modules.ticklist.TickList;
import network.repository.jfxlibs.modules.ticklist.TickListItem;

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

        LayoutItem pane = new LayoutItem(){
            private CadPane cadPane = new CadPane(label::setText, cadFeatures -> {});
            {
                ArrayList<CadFeature> features = new ArrayList<>();
                features.add(new CadPoint(0,"Point",0,0,0));

                CadPoint point = new CadPoint(0, "Point", 1000, 5000, 0);
                point.setLocked(true);
                CadLine line = new CadLine(0, 0,0,1000,5000);
                //line.setLocked(true);
                features.add(line);
                features.add(point);

                cadPane.setFeatures(features);
                Platform.runLater(cadPane::zoomToExtents);
            }
            @Override
            public Node toNode() {
                return cadPane;
            }

            @Override
            public String getLayoutId() {
                return "cad";
            }
        };

        LayoutItem layoutItem = new LayoutItem() {
            private final VBox vbox = new VBox();
            private BooleanProperty bool = new SimpleBooleanProperty();
            {
                TickList tickList = new TickList("List test");
                vbox.getChildren().add(tickList);
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());
                tickList.add(new TestItem());

            }
            @Override
            public Node toNode() {
                return vbox;
            }

            @Override
            public String getLayoutId() {
                return "Thing";
            }
        };

        LayoutPane layoutPane = new LayoutPane();
        layoutPane.getContent().addItem(0, layoutItem);
        layoutPane.getContent().addItem(1, pane);





        root.getChildren().add(layoutPane);
        stage.show();
        stage.centerOnScreen();


    }

    public static void main(String[] args) {
        launch(args);
    }


}