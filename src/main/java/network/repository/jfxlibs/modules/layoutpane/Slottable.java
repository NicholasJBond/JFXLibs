package network.repository.jfxlibs.modules.layoutpane;

import javafx.scene.Node;

public interface Slottable {
    public Node toNode();
    public void setText(String s);
}
