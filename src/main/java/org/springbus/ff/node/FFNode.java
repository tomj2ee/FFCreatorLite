package org.springbus.ff.node;

import org.springbus.ff.animate.FFAnimations;
import org.springbus.ff.core.FFBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FFNode extends FFBase {

    private int index;
    private int fIndex;
    private float duration;
    private float appearTime;
    private List<String> filters;
    private List<String> preFilters;
    private List<String> customFilters;
    private Object parent;
    private boolean hasInput;
    private double scale;
    private double rotate;
    private double x;
    private double y;
    private  int w;
    private int h;

    private FFAnimations animations;


    public FFNode(Map<String, Object> conf ) {
        super(conf);
        this.index = 0;
        this.fIndex = 0;
        this.duration = 0;
        this.appearTime = 0;
        this.filters = new ArrayList<>();
        this.preFilters = new ArrayList<>();
        this.customFilters = new ArrayList<>();
        this.parent = null;
        this.hasInput = false;

        this.setXY(x, y);
        this.setWH(w, h);
        this.setScale(scale);
        this.setRotate(rotate);
        this.animations = new FFAnimations(animations);
        this.animations.setTarget(this);
    }

    private void setWH(int w, int h) {
        setSize(w,h);
    }

    public String getFId(boolean k) {
        String vid = this.index + ":v";
        return k ? "[" + vid + "]" : vid;
    }

    public String getInId(boolean k) {
        if (this.fIndex == 0) {
            return this.getFId(k);
        } else {
            return this.getOutId(k);
        }
    }

    public String getOutId(boolean k) {
        String id = this.id + "-" + this.fIndex;
        return k ? "[" + id + "]" : id;
    }


    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setAppearTime(float appearTime) {
        this.appearTime = appearTime;
    }

   public void  setSize(int w, int  h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Get display object width and height
     * @return {string} 1000*120
     * @public
     */
    public String getSize() {
        return this.w+"*"+this.h;
    }

}