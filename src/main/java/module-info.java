module network.repository.jfxlibs {
    requires javafx.controls;
    requires org.json;
    requires java.desktop;
    requires ejml.core;
    requires ejml.simple;


    opens network.repository.jfxlibs to javafx.fxml;
    exports network.repository.jfxlibs;
    exports network.repository.jfxlibs.modules.ribbon;
    exports network.repository.jfxlibs.modules.ticklist;
    exports network.repository.jfxlibs.modules.cadpane;
    opens network.repository.jfxlibs.ribbon;
    opens network.repository.jfxlibs.images;
    opens network.repository.jfxlibs.styles;

}