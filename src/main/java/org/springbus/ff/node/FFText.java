package org.springbus.ff.node;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFContext;
import org.springbus.ff.core.FFStyle;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.util.FilterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FFText extends FFNode {

    private String color = "black";
    private String backgroundColor;
    private int fontSize = 24;
    private String text = "";
    private String font;
    private String fontfile;
    private String fontFamily;
    private String fontcolor;
    private int fontsize;
    private String boxcolor;
    private float alpha;
    private int line_spacing;
    private String bordercolor;
    private String borderw;


    public FFText(FFBaseConf conf) {
        super(conf);
        this.text = conf.getText();
        this.fontcolor = conf.getColor();
        this.fontsize = conf.getFontSize();
        this.boxcolor = conf.getBackgroundColor();
        this.fontfile = conf.getFontfile();
        if (conf.getFontfile() != null) {
            this.fontfile = conf.getFontfile();
        } else if (conf.getFontFamily() != null) {
            this.fontfile = conf.getFontFamily();
        }

    }

    /**
     * Set text value
     *
     * @param {string} text - text value
     * @public
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Set background color
     *
     * @param {string} backgroundColor - the background color value
     * @public
     */
    public void setBackgroundColor(String backgroundColor) {
        this.boxcolor = backgroundColor;
    }

    /**
     * Set text color value
     *
     * @param {string} color - the text color value
     * @public
     */
    public void setColor(String color) {
        this.fontcolor = color;
    }

    /**
     * Set text font file path
     *
     * @param {string} file - text font file path
     * @public
     */
    public String setFontFile(String file) {
        this.fontfile = file;
        return this.fontfile;
    }

    /**
     * Set text font file path
     *
     * @param {string} file - text font file path
     * @public
     */
    public String setFont(String file) {
        return this.setFontFile(file);
    }

    /**
     * Set text style by object
     *
     * @param {object} style - style by object
     * @public
     */
    public void setStyle(FFStyle style) {
        if (style.color != null) this.fontcolor = style.color;
        if (style.opacity != null) this.alpha = style.opacity;
        if (style.border != null) this.borderw = style.border;
        if (style.borderSize != null) this.borderw = style.borderSize;
        if (style.fontSize != null) this.fontsize = style.fontSize;
        if (style.borderColor != null) this.bordercolor = style.borderColor;
        if (style.backgroundColor != null) this.boxcolor = style.backgroundColor;
        if (style.lineSpacing != null) this.line_spacing = Integer.parseInt(style.lineSpacing);
    }

    /**
     * Set text border value
     *
     * @param {number} borderSize - style border width size
     * @param {string} borderColor - style border color
     * @public
     */
    public void setBorder(String borderSize, String borderColor) {
        this.borderw = borderSize;
        this.bordercolor = borderColor;
    }

    /**
     * concatFilters - Core algorithm: processed into ffmpeg filter syntax
     *
     * @param {object} context - context
     * @private
     */
    public List<Object> concatFilters(FFContext context) {
        this.animations.replaceEffectConfVal();

        this.preFilters.addAll(this.filters);
        this.filters.addAll(this.customFilters);
        List<Filter> aniFilters = this.animations.concatFilters();
        this.resetXYByAnimations(aniFilters);
        this.resetAlphaByAnimations(aniFilters);

        Filter filter = this.toFilter();
        if (filter != null) {
            this.filters.add(filter);
            this.addInputsAndOutputs(context);
        }

        return this.filters;
    }


    public void resetXYByAnimations(List<Filter> filters) {
        double[] xy = this.getXYFromOverlay(filters);
        this.x = xy[0];
        this.y = xy[1];
    }

    public void resetAlphaByAnimations(List<Filter> filters) {
        float alpha = this.getAlphaFromFilters(filters);
        this.alpha = alpha;
    }

    public float getAlphaFromFilters(List<Filter> filters) {
        for (Filter f : filters) {
            if (f.getFilter().equals("alpha")) {
                float alpha = Float.parseFloat("" + f.getOptions().get("alpha"));
                return alpha;
            }
        }
        return 1;
    }

    public double[] getXYFromOverlay(List<Filter> filters) {
        double[] xy = new double[]{this.x, this.y};
        for (Filter f : filters) {
            if (f.getFilter().equals("overlay")) {
                xy = new double[]{
                        Double.parseDouble(f.getOptions().get("x") + ""),
                        Double.parseDouble(f.getOptions().get("y") + "")
                };
            }
        }
        return xy;
    }

    /**
     * Converted to ffmpeg filter command line parameters
     *
     * @private
     */
    public Filter toFilter() {
        // Usually FFMpeg text must specify the font file directory
        // if (!this.fontfile) {
        //   console.error('[FFCreatorLite] Sorry FFText no input font file!');
        //   return;
        // }

        float curAppearTime = this.appearTime;
        if (this.appearTime <= 0) {
            curAppearTime = this.animations.getAppearTime();
        }
        float curDuration = this.duration;
        if (this.duration <= 0) {
            curDuration = this.animations.getDuration();
        }
        String enable = FilterUtil.createFilterEnable(curAppearTime, curDuration);

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("line_spacing", line_spacing);

        options.put("bordercolor", this.bordercolor);
        options.put("borderw", this.borderw);
        options.put("fontcolor", this.fontcolor);
        options.put("fontfile", this.fontfile);
        options.put("fontsize", this.fontsize);
        options.put("boxcolor", this.boxcolor);
        options.put("text", this.text);
        options.put("alpha", this.alpha);
        options.put("x", this.x);
        options.put("y", this.y);
        options.put("enable", enable);

        // Utils.deleteUndefined(options);
        if (boxcolor != null) {
            options.put("box", 1);
        }

        return new Filter("drawtext", options);
    }

    /**
     * Add input param and output param to filter
     *
     * @private
     */
    public void addInputsAndOutputs(FFContext context) {
        if (this.filters.isEmpty()) return;
        for (int index = 0; index <= this.filters.size(); index++) {
            Object filter = filters.get(index);

            String inputs = index == 0 ? context.input : this.getInId(false);
            this.genNewOutId();
            String outputs = this.getOutId(false);

            Map<String, Object> options = new HashMap<>();
            options.put("filter", filter);
            options.put("inputs", inputs);
            options.put("outputs", outputs);
            options.put("contextInputs", context.input);

            this.filters.set(index, FilterUtil.setInputsAndOutputs(options));
        }
        // 5. set context input
        context.input = this.getOutId(false);
    }

    public void genNewOutId() {
        this.fIndex++;
    }
}
