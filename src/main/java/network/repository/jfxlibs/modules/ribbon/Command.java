package network.repository.jfxlibs.modules.ribbon;

public interface Command {
    void setLabel(String string);
    void disable();
    void enable();
    int getCommand();
}
