package org.springbus.ff.conf;

import lombok.Data;

@Data
public class FFTextConf extends  FFBaseConf{
    private String color = "black";
    private String backgroundColor;
    private int fontSize = 24;
    private String text = "";
    private String font;
    private String fontfile;
    private String fontFamily;
}
