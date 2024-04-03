package org.springbus.ff.node;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FFAudio extends FFNode {
   public FFAudio(FFBaseConf conf) {
        super(conf);
        this.hasInput = true;
        this.type="audio";
    }

    /**
     * Add video ffmpeg input
     * ex: loop 1 -t 20  -i imgs/logo.png
     * @private
     */
   public void  addInput(FFCommand command) {
        command.addInput(conf.getAudio());
        if(conf.isAudioLoop()) {
            List<String> options=new ArrayList<>();
            options.add("-stream_loop");
            options.add("-1");
            command.inputOptions(options);
        }
    }

    public  String getFId(boolean k ) {
        String vid = this.index + ":a";
        return k ? "[" + vid + "]" : vid;
    }

   public void  setLoop(boolean loop) {
        this.conf.setAudioLoop(loop);
    }

   public void  addOptions(FFCommand command) {
       String inputs = this.getInId(false);
       List<String> options = new ArrayList<>();
       String cmd = "-map " + inputs + " -c:a aac -shortest";

       options.addAll(Arrays.asList(cmd.split(" ")));
       command.outputOptions(options);
   }

    public List<Object> concatFilters() {
        return this.filters;
    }
}