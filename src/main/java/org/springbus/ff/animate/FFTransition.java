package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFBase;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

public class FFTransition  extends FFBase {
    public  String name;
    public int offset;
    public int  duration;
    public FFTransition(FFBaseConf conf) {
        super(conf);
        this.type = "'transition'";
        this.name = conf.getName();
        this.offset = 0;
        this.duration = DateUtil.toSeconds(duration);
    }

        /**
         * Converted to ffmpeg command line parameters
         * @private
         */
      public Filter toFilter(int aoffset) {
          Map<String, Object> options = new HashMap<>();
          options.put("transition", name);
          options.put("duration", duration);
          options.put("offset", offset + aoffset);
          return new Filter("xfade", options);
      }

       public void  destroy() {

       }
}
