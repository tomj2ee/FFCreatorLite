package org.springbus.ff.node;

import org.apache.commons.lang3.StringUtils;
import org.springbus.ff.animate.FFAnimations;
import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFBase;
import org.springbus.ff.core.FFCommand;
import org.springbus.ff.core.FFContext;
import org.springbus.ff.util.FilterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FFNode extends FFBase {

    protected int index;
    protected int fIndex;
    protected int  duration;
    protected float appearTime;
    protected List<Object> filters;
    protected List<Object> preFilters;
    protected List<String> customFilters;
    protected FFCon parent;
    protected boolean hasInput;
    protected double scale;
    protected double rotate;
    protected double x;
    protected double y;
    protected int w;
    protected int h;

    protected FFAnimations animations;


    public FFNode(FFBaseConf conf) {
        super(conf);
        this.index = 0;
        this.fIndex = 0;
        this.duration = conf.getDuration();
        this.appearTime = 0;
        this.filters = new ArrayList<>();
        this.preFilters = new ArrayList<>();
        this.customFilters = new ArrayList<>();
        this.parent = null;
        this.hasInput = false;
        this.x=conf.getX();
        this.y=conf.getY();
        this.setXY(conf.getX(), conf.getY());
        this.setWH(conf.getWidth(), conf.getHeight());
        this.setScale(conf.getScale());
        this.setRotate(conf.getRotate());
        this.animations = new FFAnimations(animations);
        this.animations.setTarget(this);
    }

    private void setWH(int w, int h) {
        setSize(w, h);
    }

    public String getFId(boolean k) {
        String vid = this.index + ":v";
        return k ? "[" + vid + "]" : vid;
    }

    public String getInId(boolean k) {
        if (this.fIndex == 0) {
            return this.getFId(k);
        } else {
            return this.getOutId(k);
        }
    }

    public String getOutId(boolean k) {
        String id = this.id + "-" + this.fIndex;
        return k ? "[" + id + "]" : id;
    }


    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setAppearTime(float appearTime) {
        this.appearTime = appearTime;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Get display object width and height
     *
     * @return {string} 1000*120
     * @public
     */
    public String getSize() {
        return this.w + "*" + this.h;
    }
    public String getSize(String dot) {
        return this.w + dot + this.h;
    }


    /**
     * concatFilters - Core algorithm: processed into ffmpeg filter syntax
     * 1. add preset filters    -> pre filter
     * 2. scale+rotate          -> pre filter
     * 3. other filters
     * 4. fade/zoompan
     * 5. x/y                   -> last overlay
     *
     * @param {object} context - context
     * @return
     * @private
     */
    public List<Object> concatFilters(FFContext context) {
        // 1. correct position
        this.animations.replaceEffectConfVal();
        this.recorrectPosition();

        // 2. add preset filters
        // this.filters = this.preFilters.addAll(this.filters);
        this.filters.addAll(this.preFilters);

        // 3. add scale rotate filters
        Map<String, Double> options = new HashMap<>();
        options.put("scale", this.scale);
        options.put("rotate", this.rotate);
        String srFilter = FilterUtil.assembleSRFilter(options);
        if (StringUtils.isNotBlank(srFilter)) {
            this.filters.add(srFilter);
        }

        // 4. add others custom filters
        //this.filters = this.filters.concat(this.customFilters);
        this.filters.addAll(this.customFilters);

        // 5. add animations filters

        //this.appearTime = this.appearTime || this.animations.getAppearTime();
        if (this.appearTime <= 0) {
            this.appearTime = this.animations.getAppearTime();
        }
        // Because overlay enable is used, remove this
        // this.animations.modifyDelayTime(this.appearTime);
        Object aniFilters = this.animations.concatFilters();
        this.filters.add(aniFilters);

        // 6. set overlay filter x/y
        if (null != FilterUtil.getOverlayFromFilters(this.filters)) {
            Object xyFilter = FilterUtil.createOverlayFilter(this.x, this.y);
            this.filters.add(xyFilter);
        }

        // 7. add appearTime setpts
        // Because overlay enable is used, remove this
        // this.filters.push(FilterUtil.createSetptsFilter(this.appearTime));

        // 8. add this duration time
        this.addDurationToOverlay();

        // 9. add inputs and outputs
        this.addInputsAndOutputs(context);
        return this.filters;
    }

    public void addInputsAndOutputs(FFContext context) {

    }

    private void addDurationToOverlay() {
        if(this.appearTime<=0) {
            this.appearTime =  this.animations.getAppearTime();
        }
        if(this.duration<=0) {
            this.duration =  this.animations.getDuration();
        }
        FilterUtil.addDurationToOverlay(this.filters,this.appearTime,this.duration);
    }

    private void recorrectPosition() {


    }
    public void addInput(FFCommand command) {

    }

    public void setDuration(int duration) {
        this.duration=duration;
    }

    public void addOutput(FFCommand command) {

    }

    public void addOptions(FFCommand command) {

    }
}