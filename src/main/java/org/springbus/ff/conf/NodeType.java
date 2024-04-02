package org.springbus.ff.conf;

public enum NodeType {
    //'album' | 'image' | 'node' | 'scene' | 'subtitle' | 'text' | 'video' | 'vtuber';
    album(1, "album"),
    image(2, "image"),
    node(3, "node"),
    scene(4, "scene"),
    subtitle(5, "subtitle"),
    text(6, "text"),
    vtuber(7, "vtuber");
    private Integer value;
    private String desc;

    NodeType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
