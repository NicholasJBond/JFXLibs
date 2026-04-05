package network.repository.jfxlibs;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import network.repository.jfxlibs.modules.ticklist.TickListItem;

class TestItem implements TickListItem {
    boolean status;
    @Override
    public String getText() {
        return "Item";
    }

    @Override
    public BooleanProperty activeProperty() {
        return new SimpleBooleanProperty();
    }
}
