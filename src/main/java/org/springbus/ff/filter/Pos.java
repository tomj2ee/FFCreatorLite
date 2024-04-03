package org.springbus.ff.filter;

public class Pos {
    public double x;
    public double y;

    public String sx;
    public String sy;

    public Double from;
    public Double to;

    public double rotate;

    public Pos posFrom;
    public Pos posTo;

    public  String size;

    public double  zoom;

    public Pos() {
    }

    public Pos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Pos(double rotate) {
        this.rotate = rotate;
    }
}
