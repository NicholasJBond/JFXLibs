package network.repository.jfxlibs.modules.layoutpane;

public class DragManager {
    double x;
    double y;
    double xEnd;
    double yEnd;
    boolean eastWest;
    LayoutSlot slot;

    double calcDistance(){
        if (eastWest){
            return xEnd - x;
        }else{
            return yEnd - y;
        }
    }
}
