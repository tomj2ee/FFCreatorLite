package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.filter.FilterData;
import org.springbus.ff.math.Ease;
import org.springbus.ff.util.FilterUtil;
import org.springbus.ff.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZoomRepFilter {

    public static AniFilter toRotateFilter(FFBaseConf conf) {
        Double from = conf.getFrom();
        Double to = conf.getTo();
        double time = conf.getTime();
        String showType = conf.getShowType();
        double add = conf.getAdd();
        boolean ing = conf.isIng();
        double delay = conf.getDelay();
        boolean pad = conf.isPad();
        String ease = "linear";
        time = Utils.floor(time);
        from = Utils.angleToRadian(from);
        to = Utils.angleToRadian(to);

        String size = conf.getSize();
        String zoom, strFilter;
        int maxScale = 1;
        String x = "iw/2-(iw/zoom/2)";
        String y = "ih/2-(ih/zoom/2)";

        // reset from to value
        if (from < 1 && pad) {
            to = (1 / from) * to;
            from = 1d;
        }

        int fps = conf.getFps();
        int frames = (int) (fps * time);
        int delayFrames = (int) (fps * delay);
        // (max>=x>=min)
        String coodi = "between(on," + (delayFrames - 1) + "," + delayFrames + frames + ")";

        // Is it continuous animation or single easing animation
        if (ing) {
            if (to == null) {
                to = from + add * frames;
            }
            zoom = "{from}+" + add + "*(on-" + delayFrames + ")";
        } else {
            zoom = Ease.getVal(ease, from, to - from, frames, delayFrames).replace("t", "on");
        }

        //-
        String z = "if(" + coodi + "," + zoom + ",_else_" + to + "_else_)";
        z = Utils.replacePlusMinus(z);

        //-
        maxScale = (int) Math.max(from, to);
        String padFilter = FilterUtil.createPadFilter(maxScale);
        String scaleFilter = FilterUtil.createScaleFilter(4000);
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        params.put("z", z);
        params.put("s", size);
        params.put("fps", fps);
        params.put("d", frames);
        String zoomFilter = FilterUtil.createZoomFilter(params);
        strFilter = pad ? padFilter + "," + scaleFilter + "," + zoomFilter
                : scaleFilter + "," + zoomFilter;

        Map<String, Object> option = new HashMap<>();
        option.put("raw", strFilter);
        Filter filter = new Filter("zoompan", option);
        FilterData data = new FilterData();
        data.setTime(time);
        data.setDelay(delay);
        data.setElseStr(ease);
        data.setZ(z);
        data.setDelayFrames(delayFrames);
        return new AniFilter("zoompan", filter, "string", showType, data);
    }


    /**
     * create new zoompan filter
     * if(a<t<b, T1, if(t<c, C1, if(c<t<d, T2, C2)))
     * @private
     */
public AniFilter mergeIntoNewZoompanFilter(List<AniFilter> tfilters) {
    String elseReg = "_else_";
    String delayReg = "_delay_";
    String elseNelse = "_else_[0-9a-z]*_else_";

    String zoom = "";
    String elsez = "";

    // if(lte(t,_delay_),${to.x},_else_)
    for (int index = 0; index < tfilters.size(); index++) {
        AniFilter aniFilter = tfilters.get(index);
        FilterData data = aniFilter.getData();
        double delay = data.getDelay();
        String z = data.getZ();
        Filter filter = aniFilter.getFilter();
        if (index > 0) {
            elsez = elsez.replace(delayReg, "" + delay).replace(elseReg, z);
            zoom = zoom.replace(elseNelse, elsez);
        } else {
            zoom = filter.getOptionKey("z");
            elsez = data.getElseStr();
        }
    }

    zoom = zoom.replace(elseReg, "");
    Map<String, Object> op = new HashMap<>();
    op.put("raw", zoom);
    Filter filter = new Filter("zoompan", op);
    return new AniFilter("zoompan", filter, "string", "", null);
}

/**
 * Replace placeholder characters in the filter field
 * @private
 */
public static void replaceZoomFilter(AniFilter aniFilter) {
    String elseReg = "_else_";
    Filter filter = aniFilter.getFilter();
    String strFilter = filter.getOptionKey("raw");
    strFilter = strFilter.replace(elseReg, "");
    filter.getOptions().put("raw", strFilter);
}
}
