package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.Node;

public interface LayoutItem {
    Node toNode();
    String getLayoutId();
}
