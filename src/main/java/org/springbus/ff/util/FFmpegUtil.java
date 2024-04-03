package org.springbus.ff.util;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFCommand;

public class FFmpegUtil {

    public static FFCommand createCommand(int threads){
        FFCommand cmd=new FFCommand();
        return cmd;
    }

    public static void addDefaultOptions(FFCommand command, FFBaseConf conf,
                                         boolean audio) {
    }
}
