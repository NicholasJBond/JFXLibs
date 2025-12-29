package network.repository.jfxlibs.modules.ticklist;

public interface TickListItem {
    String getText();
    void update(boolean ticked);
    boolean getStatus();
}
