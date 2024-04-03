package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFBase;
import org.springbus.ff.filter.Pos;

public class FFAnimation extends FFBase {
    private Double time = 2d;
    private Double delay = 0d;

    public FFAnimation(FFBaseConf conf) {
        super(conf);
        this.filter = null;
        this.isFFAni = true;
        this.type = "fade";
        this.showType = "in";
        if (conf.getTime() != null) {
            this.time = conf.getTime();
        }
        if (conf.getDelay() != null) {
            this.delay = conf.getDelay();
        }
        if (conf.getType() != null) {
            this.type = conf.getType();
        }
    }

    /**
     * Converted to ffmpeg command line parameters
     *
     * @private
     */
    public AniFilter toFilter() {

        switch (this.conf.getType()) {
            case "move":
                filter = MoveRepFilter.toMoveFilter(conf);
                break;

            case "fade":
            case "show":
                filter = FadeRepFilter.toFadeFilter(conf);
                break;

            case "rotate":
                filter = RotateRepFilter.toRotateFilter(conf);
                break;

            case "zoom":
            case "zoompan":
                filter = ZoomRepFilter.toRotateFilter(conf);
                break;

            case "alpha":
                filter = AlphaReqFilter.toAlphaFilter(conf);
                break;
        }
        return this.filter;
    }


    /**
     * Get from and to value from conf
     *
     * @private
     */
    public Pos getFromTo() {
        Pos p = new Pos();
        if (type.equals("move")) {
            Pos from = conf.getMoveFrom();
            Pos to = conf.getMoveTo();
            if (null != to) {
                to = new Pos();
                Pos add = conf.getMoveAdd();
                to.x = from.x + add.x * time;
                to.y = from.y + add.y * time;

            }
            p.posFrom = from;
            p.posTo = to;
        } else if (type.equals("zoom")) {
            Double from = conf.getFrom();
            Double to = conf.getTo();
            if (to != null) {
                int fps = conf.getFps();
                int frames = (int) (fps * time);
                Double add = conf.getAdd();
                to = from + add * frames;
            }
            p.from = from;
            p.to = to;
        } else {
            Double to = conf.getTo();
            Double from = conf.getFrom();
            if (to != null) {

                Double add = conf.getAdd();
                to = from + add * time;
            }
            p.from = from;
            p.to = to;
        }
        return p;
    }
}
