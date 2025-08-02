package network.repository.jfxlibs.modules;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Ribbon extends TabPane {
    public Ribbon(){
        super();
        super.getTabs().add(new Tab("Observations"));
    }
}
