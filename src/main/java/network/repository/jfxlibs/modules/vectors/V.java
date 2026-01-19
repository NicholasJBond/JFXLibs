package network.repository.jfxlibs.modules.vectors;

public class V {

    private double x;
    private double y;

    public V(double x, double y){
        this.x = x;
        this.y = y;
    }

    public V unitVector(){
        return scale(1/magnitude());
    }

    public double magnitude(){
        return Math.sqrt(x*x+y*y);
    }

    public V add(V v){
        return new V(x+v.getX(),y+v.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public V scale(double scale){
        return new V(x*scale, y*scale);
    }

    public double gradiant(){
        return y/x;
    }

    public String toString(){
        return "x: "+x+" y:"+y;
    }
}
