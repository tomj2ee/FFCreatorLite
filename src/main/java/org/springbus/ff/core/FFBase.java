package org.springbus.ff.core;


import org.springbus.ff.animate.AniFilter;
import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.node.FFCon;
import org.springbus.ff.node.FFNode;

import java.io.File;
import java.util.UUID;

public class FFBase {
    public FFBaseConf conf;
    public String type;
    protected String showType;
    protected String id;

    public   boolean isFFAni;
    protected AniFilter filter;

   public FFBase parent;

    public FFBase(FFBaseConf conf) {
        this.conf = conf;
        this.type ="base";
        this.parent = null;
        generateId();
    }

    // Method to generate unique id
    private void generateId() {
        this.id = UUID.randomUUID().toString();
    }

    // Method to get the logical root node of the instance
    public FFBase root() {
        return this.parent != null ? this.parent.root() : this;
    }

    public static double floor(int n, int s) {
        double k = Math.pow(10, s);
        return Math.floor(n * k) / k;
    }

    public static double floor(int n) {
        return floor(n, 2);
    }

}