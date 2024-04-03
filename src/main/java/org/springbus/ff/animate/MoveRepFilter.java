package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.filter.FilterData;
import org.springbus.ff.filter.Pos;
import org.springbus.ff.math.Ease;
import org.springbus.ff.math.Maths;
import org.springbus.ff.util.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveRepFilter {
    public static AniFilter toMoveFilter(FFBaseConf conf) {

        Pos from = conf.getMoveFrom();
        Pos to = conf.getMoveTo();
        double time = conf.getTime();
        Pos add = conf.getMoveAdd();
        boolean ing = conf.isIng();
        double delay = conf.getDelay();
        String ease = "linear";
        time = Utils.floor(time);
        if (conf.getEase() != null) {
            ease = conf.getEase();
        }


        String x, y;
        String movex, movey;

        time = Utils.floor(time);
        BigDecimal ddelay = Maths.accAdd(new BigDecimal(delay), new BigDecimal(time));
        String coodi = "between(t," + delay + "," + ddelay + ")";

        // Is it continuous animation or single easing animation
        if (ing) {
            if (to != null) {
                to = new Pos();
                to.x = from.x + add.x * time;
                to.y = from.y + add.y * time;
            }
            movex = from.x + add.x + "*t";
            movey = from.y + "" + add.y + "*t";
        } else {
            movex = Ease.getVal(ease, from.x, to.x - from.x, time, delay);
            movey = Ease.getVal(ease, from.y, to.y - from.y, time, delay);
        }

        Pos elsestr = new Pos();
        elsestr.sx = "if(lte(t,_delay_)," + to.x + ",_else_)";
        elsestr.sy = "if(lte(t,_delay_)," + to.y + ",_else_)";

        x = "if(" + coodi + "," + movex + ",_else_" + to.x + "_else_)";
        y = "if(" + coodi + "," + movey + "+,_else_" + to.y + "_else_)";
        x = Utils.replacePlusMinus(x);
        y = Utils.replacePlusMinus(y);
        Map<String, Object> options = new HashMap<>();
        options.put("x", x);
        options.put("y", y);
        Filter filter = new Filter("overlay", options);
        FilterData data = new FilterData();
        data.setTime(time);
        data.setDelay(delay);
        data.setPosElsestr(elsestr);

        return new AniFilter("overlay",
                filter, "object", "", data);
    }

    /**
     * create new overlay filter
     * if(a<t<b, T1, if(t<c, C1, if(c<t<d, T2, C2)))
     *
     * @private
     */
    public static AniFilter mergeIntoNewOverflyFilter(List<AniFilter> tfilters) {
        String elseReg = "_else_";
        String delayReg = "_delay_";
        String elseNelse = "_else_[0-9a-z]*_else_";

        String x = "";
        String y = "";
        String elsex="";
        String elsey="";

        // if(lte(t,_delay_),${to.x},_else_)
        for (int index = 0; index < tfilters.size(); index++) {
            AniFilter aniFilter = tfilters.get(index);
            FilterData data = aniFilter.getData();
            double delay = data.getDelay();
            Filter filter = aniFilter.getFilter();
            if (index > 0) {
                elsex = elsex.replace(delayReg, "" + delay).replace(elseReg, filter.getOptionKey("x"));
                elsey = elsey.replace(delayReg, "" + delay).replace(elseReg, filter.getOptionKey("y"));
                x = x.replace(elseNelse, elsex);
                y = y.replace(elseNelse, elsey);
            } else {
                x = filter.getOptionKey("x");
                y = filter.getOptionKey("y");
                elsex = data.getPosElsestr().sx;
                elsey = data.getPosElsestr().sy;
            }
        }

        x = x.replace(elseReg, "");
        y = y.replace(elseReg, "");

        Map<String, Object> options = new HashMap<>();
        options.put("raw", "overlay=x='" + x + "':y='" + y + "'");
        Filter filter = new Filter("overlay", options);
        return new AniFilter("overlay",
                filter,
                "overlay",
                "string", null);
    }

    /**
     * Replace placeholder characters in the filter field
     *
     * @private
     */
    public static void replaceOverflyFilter(AniFilter aniFilter) {
        String elseReg = "_else_";
        Filter filter = aniFilter.getFilter();
        String x = filter.getOptionKey("x").replace(elseReg, "");
        String y = filter.getOptionKey("y").replace(elseReg, "");
        filter.getOptions().put("x", x);
        filter.getOptions().put("y", y);
    }
}
