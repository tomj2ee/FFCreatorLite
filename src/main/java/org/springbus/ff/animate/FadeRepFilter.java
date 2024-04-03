package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.filter.FilterData;
import org.springbus.ff.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class FadeRepFilter {
    public AniFilter toFadeFilter(FFBaseConf conf) {

        double time = conf.getTime();
        Double delay = conf.getDelay();
        String color = conf.getColor();
        if (color == null) {
            color = "black";
        }
        String showType = conf.getShowType();
        if (showType == null) {
            showType = "in";
        }
        Float alpha = conf.getAlpha();
        if (alpha == null) {
            alpha = 1f;
        }
        time = Utils.floor(time);
        delay = Utils.floor(delay);

        Map<String, Object> options = new HashMap<>();
        options.put("type", showType);
        options.put("st", delay);
        options.put("d", time);
        options.put("color", color);
        options.put("alpha", alpha);

        Filter filter = new Filter("fade", options);
        FilterData data = new FilterData();
        data.setTime(time);
        data.setDelay(delay);
        return new AniFilter("fade",
                filter,
                "object",
                showType, data);

    }
}
