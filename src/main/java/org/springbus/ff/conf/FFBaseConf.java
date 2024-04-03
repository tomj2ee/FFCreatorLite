package org.springbus.ff.conf;

import lombok.Data;
import org.springbus.ff.filter.Pos;

import java.nio.channels.FileLock;
import java.security.SecureRandom;

@Data
public class FFBaseConf {
    private String type;
    /**
     * @default 0
     */
    private int x = 0;
    /**
     * @default 0
     */
    private int y;
    /**
     * @default 1
     */
    private float scale;
    /**
     * @default 0
     */
    private float rotate;
    /**
     * @default 1
     */
    private float opacity;
    /**
     * @default 0
     */
    private int duration;
    private float tansDuration;
    /**
     * @default false
     */
    private boolean preload;
    private int width;
    private int height;
    private String path;
    private String url;
    private String image;
    private String name;
    private String color;

    private int fps = 25;
    private boolean debug;
    private String cacheDir;
    private String cacheFormat;
    private int threads = 2;
    private boolean upStreaming;
    private String output;
    private String audio;
    private boolean audioLoop;

    private int fontSize = 24;

    private String backgroundColor;

    private String text = "";
    private String font ;
    private String fontfile;
    private String fontFamily;


    //
    private Double time;
    private Double from;
    private Double to=null;
    private String showType;
    private Double add;
    private boolean ing;
    private Double delay;
    private String elsestr;

    private  String ease;

    private Pos moveFrom;
    private Pos moveTo;
    private Pos moveAdd;

    private Float alpha;
    private  boolean pad;
    private String size;
}
