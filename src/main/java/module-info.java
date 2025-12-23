module network.repository.jfxlibs {
    requires javafx.controls;
    requires org.json;
    requires java.desktop;


    opens network.repository.jfxlibs to javafx.fxml;
    exports network.repository.jfxlibs;
    exports network.repository.jfxlibs.modules.ribbon;
    opens network.repository.jfxlibs.configuration.ribbon;
    opens network.repository.jfxlibs.images;

}