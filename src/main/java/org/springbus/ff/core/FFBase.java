package org.springbus.ff.core;


import java.util.Map;
import java.util.UUID;

public class FFBase {
    protected Map<String, Object> conf;
    protected String type;
    protected FFBase parent;
    protected String id;

    public FFBase(Map<String, Object> conf) {
        this.conf = conf;
        this.type = (String) conf.getOrDefault("type", "null");
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