package org.springbus.ff.conf;

public enum ConfType {
    //  type ConfType = 'base' | 'con' | 'node' | NodeType;
    base(1, "base"),
    con(2, "con"),
    node(3, "node");

    private Integer value;
    private String desc;
    ConfType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
