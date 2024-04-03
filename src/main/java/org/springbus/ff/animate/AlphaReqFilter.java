package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.filter.FilterData;
import org.springbus.ff.math.Ease;
import org.springbus.ff.math.Maths;
import org.springbus.ff.util.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaReqFilter {
    public static AniFilter toAlphaFilter(FFBaseConf conf) {

        double time = conf.getTime();
        String showType = conf.getShowType();
        Double add = conf.getAdd();
        boolean ing = conf.isIng();
        Double delay = conf.getDelay();
        String ease = "linear";
        if (conf.getEase() != null) {
            ease = conf.getEase();
        }
        ;
        String a, elsestr;
        int from = showType.equals("in") ? 0 : 1;
        int to = showType.equals("in") ? 1 : 0;
        time = Utils.floor(time);
        BigDecimal ddelay = Maths.accAdd(new BigDecimal(delay), new BigDecimal(time));
        String coodi = "between(t," + delay + "," + ddelay + ")";

        // Is it continuous animation or single easing animation
        if (ing) {
            if (to == 0) {
                to = (int) (from + add * time);
            }
            add = Utils.angleToRadian(add);
            a = from + "" + add + "*t";
        } else {
            a = Ease.getVal(ease, from, to - from, time, delay);
        }

        elsestr = "if(lte(t,_delay_)," + to + ",_else_)";
        String alpha = "if(" + coodi + "," + a + ",_else_" + to + "_else_)";
        alpha = Utils.replacePlusMinus(alpha);
        Map<String, Object> op = new HashMap<>();
        op.put("alpha", alpha);
        Filter filter = new Filter("alpha", op);

        FilterData data = new FilterData();
        data.setTime(time);
        data.setDelay(delay);
        data.setElseStr(elsestr);
        return new AniFilter("alpha", filter, "alpha", "object", data);
    }

    /**
     * create new alpha filter
     * if(a<t<b, T1, if(t<c, C1, if(c<t<d, T2, C2)))
     *
     * @private
     */
    public static AniFilter mergeIntoNewAlphaFilter(List<AniFilter> tfilters) {
        String elseReg = "_else_";
        String delayReg = "_delay_";
        String elseNelse = "_else_[0-9a-z]*_else_";

        String a = "";
        String elsea = "";
        for (int index = 0; index < tfilters.size(); index++) {
            AniFilter aniFilter = tfilters.get(index);
            FilterData data = aniFilter.getData();

            Double delay = data.getDelay();
            Filter filter = aniFilter.getFilter();
            if (index > 0) {
                elsea = elsea.replace(delayReg, "" + delay).replace(elseReg,
                        filter.getOptionKey("alpha"));

                a = a.replace(elseNelse, elsea);
            } else {
                a = filter.getOptionKey("alpha");
                elsea = data.getElseStr();
            }
        }


        a = a.replace(elseReg, "");
        Map<String, Object> op = new HashMap<>();
        op.put("alpha", a);
        Filter f = new Filter("alpha", op);
        return new AniFilter("alpha", f, "alpha", "object", null);
    }

    /**
     * Replace placeholder characters in the filter field
     *
     * @private
     */
    public static void replaceAlphaFilter(AniFilter aniFilter) {
        String elseReg = "_else_";
        Filter filter = aniFilter.getFilter();
        String a = filter.getOptionKey("alpha");
        a = a.replace(elseReg, "");
        filter.getOptions().put("alpha", a);
    }

}
