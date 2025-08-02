module network.repository.jfxlibs {
    requires javafx.controls;
    requires javafx.fxml;


    opens network.repository.jfxlibs to javafx.fxml;
    exports network.repository.jfxlibs;
}