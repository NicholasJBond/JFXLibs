package network.repository.jfxlibs;

import network.repository.jfxlibs.modules.ticklist.TickListItem;

public class TestItem implements TickListItem {
    boolean status;
    @Override
    public String getText() {
        return "Item";
    }

    @Override
    public void update(boolean ticked) {
        status = ticked;
    }

    @Override
    public boolean getStatus() {
        return status;
    }
}
