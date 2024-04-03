package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.filter.FilterData;
import org.springbus.ff.math.Ease;
import org.springbus.ff.math.Maths;
import org.springbus.ff.util.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RotateRepFilter {

    public static  AniFilter toRotateFilter(FFBaseConf conf) {
        String rotate;
        double from = conf.getFrom();
        Double to = conf.getTo();
        double time = conf.getTime();
        String type = conf.getType();
        double add = conf.getAdd();
        boolean ing = conf.isIng();
        double delay = conf.getDelay();
        String ease = "linear";
        time = Utils.floor(time);
        from = Utils.angleToRadian(from);
        to = Utils.angleToRadian(to);

        BigDecimal ddelay = Maths.accAdd(new BigDecimal(delay), new BigDecimal(time));
        String coodi = "between(t," + delay + "," + ddelay + ")";

        // Is it continuous animation or single easing animation
        if (ing) {
            if (to == null) {
                to = from + add * time;
            }
            add = Utils.angleToRadian(add);
            rotate = from + (add + "*t");
        } else {
            rotate = Ease.getVal(ease, from, to - from, time, delay);
        }

        String elsestr = "if(lte(t,_delay_)," + to + ",_else_)";
        String a = "if(" + coodi + "," + rotate + ",_else_" + to + "_else_)";
        a = Utils.replacePlusMinus(a);

        Map<String, Object> options = new HashMap<>();
        options.put("a", a);
        options.put("ow", "hypot(iw,ih)");
        options.put("oh", "ow");
        options.put("c", "black@0");
        Filter filter = new Filter("rotate", options);
        FilterData data = new FilterData();
        data.setTime(time);
        data.setDelay(delay);
        data.setElseStr(elsestr);

        return new AniFilter("rotate", filter, type,"object", data);
    }

}
