package org.springbus.ff.core;


import org.springbus.ff.conf.FFBaseConf;

import java.util.UUID;

public class FFBase {
    protected FFBaseConf conf;
    protected String type;
    protected FFBase parent;
    protected String id;

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