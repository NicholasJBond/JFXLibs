module network.repository.jfxlibs {
    requires javafx.controls;
    requires org.json;
    requires java.desktop;
    requires ejml.core;
    requires ejml.simple;
    requires com.techsenger.tabpanepro.core;
    requires javafx.graphics;


    exports network.repository.jfxlibs;
    exports network.repository.jfxlibs.modules.ribbon;
    exports network.repository.jfxlibs.modules.ticklist;
    exports network.repository.jfxlibs.modules.cadpane;
    exports network.repository.jfxlibs.modules.layoutpane;
    opens network.repository.jfxlibs.ribbon;
    opens network.repository.jfxlibs.images;
    opens network.repository.jfxlibs.styles;

}