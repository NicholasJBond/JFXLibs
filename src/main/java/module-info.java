module network.repository.jfxlibs {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;


    opens network.repository.jfxlibs to javafx.fxml;
    exports network.repository.jfxlibs;
}