package network.repository.jfxlibs.modules.ticklist;

import javafx.beans.property.BooleanProperty;

public interface TickListItem {
    String getText();
    BooleanProperty activeProperty();
}
