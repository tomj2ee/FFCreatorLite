package org.springbus.ff.conf;

import lombok.Data;

@Data
public class FFBaseConf {

    private ConfType type;

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
    /**
     * @default false
     */
    private boolean preload;
}
