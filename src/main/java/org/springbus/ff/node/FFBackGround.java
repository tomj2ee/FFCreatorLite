package org.springbus.ff.node;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFContext;
import org.springbus.ff.filter.Filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FFBackGround  extends FFNode{
    private  String color;

        public FFBackGround(FFBaseConf conf) {
            super(conf);
            this.type = "'background'";
            this.color = conf.getColor();
            if (conf.getDuration() > 0) {
                this.duration = conf.getDuration();
            } else {
                this.duration = 999999;
            }
        }
        /**
         * Combine various filters as ffmpeg parameters
         * @private
         */
       public List<Object> concatFilters(FFContext context) {
           Filter filter = this.toFilter(context);
            this.filters.add(filter);
            return this.filters;
        }

        /**
         * Converted to ffmpeg filter command line parameters
         * @private
         */

       public  Filter toFilter(FFContext context) {
           Map<String, Object> options = new HashMap<>();
           options.put("c", this.color);
           options.put("d", this.duration);
           options.put("size", getSize());

           Filter filter = new Filter("'color'", options);
           filter.outputs = this.getOutId(false);
           context.input = this.getOutId(false);
           return filter;
       }
}
