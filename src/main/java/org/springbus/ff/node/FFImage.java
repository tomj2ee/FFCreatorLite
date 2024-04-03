package org.springbus.ff.node;

import org.springbus.ff.conf.FFBaseConf;

import java.util.List;

public class FFImage  extends FFNode {
    public FFImage(FFBaseConf conf) {
        super(conf);
        this.type = "image";
        this.hasInput = true;
    }

    /**
     * Add ffmpeg input
     * ex: loop 1 -t 20  -i imgs/logo.png
     *
     * @private
     */
    public void addInput(List<String> command) {
        command.add("loop");
        command.add("1");
        command.add(this.getPath());
    }

    /**
     * Get image path
     *
     * @public
     */
    public String getPath() {
        if (conf.getPath() != null) {
            return this.conf.getPath();
        }
        if (this.conf.getImage() != null) {
            return this.conf.getImage();
        } else {
            return this.conf.getUrl();
        }
    }

}
