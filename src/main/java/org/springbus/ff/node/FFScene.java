package org.springbus.ff.node;

import org.apache.commons.io.FileUtils;
import org.springbus.ff.animate.FFTransition;
import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.core.FFCommand;
import org.springbus.ff.core.FFContext;
import org.springbus.ff.filter.Filter;
import org.springbus.ff.util.FFmpegUtil;
import org.springbus.ff.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FFScene extends  FFCon {

    private  String pathId;
    private  int percent=0;
    private int duration;
    private String directory;
    private FFContext context;

    
    private FFTransition transition ;

    private FFNode  background;

    private  String filepath;
    public  FFScene(FFBaseConf conf) {
        //super({ type: 'scene', ...conf });
        super(conf);
        this.type="scene";

        this.percent = 0;
        this.duration = 10;
        this.directory = "";
        this.context = null;
        this.command = null;
        this.transition = null;
        this.pathId = Utils.uid();

        this.setBgColor("#000000");
        this.addBlankImage();
    }


   public void  addBlankImage() {
       String blank = "assets/blank.png";
       FFBaseConf cf = new FFBaseConf();
       cf.setPath(blank);
       cf.setX(-10);
       cf.setY(0);
       this.addChild(new FFImage(cf));
   }

    /**
     * Set the time the scene stays in the scree
     * @param {number} duration - the time the scene
     * @public
     */
   public void  setDuration(int duration) {
       if(this.background!=null) {
           this.background.setDuration(duration);
       }
        this.duration = duration;
    }

    /**
     * Set scene transition animation
     * @param {string|object} name - transition animation name or animation conf object
     * @param {number} duration - transition animation duration
     * @param {object} params - transition animation params
     * @public
     */
    public  void setTransition(Object name, float duration) {
        String curName="";
        if (name instanceof  FFBaseConf ) {
            FFBaseConf cf=(FFBaseConf) name;
            curName = cf.getName();
            duration = cf.getTansDuration();
        }else if(name instanceof  String) {
            curName = (String) name;
        }
        FFBaseConf  baseConf=new FFBaseConf();
        baseConf.setName(curName);
        baseConf.setTansDuration(duration);
        this.transition = new FFTransition(baseConf);
    }

    /**
     * Set background color
     * @param {string} bgcolor - background color
     * @public
     */
   public void  setBgColor(String color) {
       if (this.background != null) {
           this.removeChild(this.background);
       }
       FFBaseConf cf = new FFBaseConf();
       cf.setColor(color);
       this.background = new FFBackGround(cf);
       this.addChildAt(this.background, 0);
   }

   public String getFile() {
        return this.filepath;
    }

    public  int getNormalDuration() {
        int transTime = 0;
        if (this.transition != null) {
            transTime = this.transition.duration;
        }
        return this.duration - transTime;
    }

   public String  createFilepath() {
       String cacheDir = conf.getCacheDir().replace("\\", "/");
       String format = conf.getCacheFormat();
       String directory = cacheDir + "/" + pathId;

       String filepath = directory + "+/" + id+ "+." + format;
       new File(directory).mkdirs();

       this.filepath = filepath;
       this.directory = directory;
       return filepath;
   }

    // about command
   public void  createNewCommand() {
       int threads = conf.getThreads();
       boolean upStreaming = conf.isUpStreaming();
       FFCommand command = FFmpegUtil.createCommand(threads);
       if (!upStreaming) {
           command.setDuration(this.duration);
       }

       this.command = command;
       this.context = new FFContext();
   }

   public FFCommand  toCommand() {
       FFCommand c = this.command;
       this.addNodesInputCommand(c);
       this.toFiltersCommand(c);
       this.addCommandOptions(c);
       this.addCommandOutputs(c);
       this.addNodesOutputCommand(c);
       this.addCommandEvents(c);
       return command;
   }

   public void  start() {
        this.createNewCommand();
        this.toCommand();
        this.command.run();
    }

   public void  addCommandOptions(FFCommand command) {

    boolean upStreaming = conf.isUpStreaming();

        if (upStreaming) {
            FFmpegUtil.addDefaultOptions( command, conf, true );
        } else {
            FFmpegUtil.addDefaultOptions( command, conf, false );
        }

    int  fps = conf.getFps();
        if (fps != 25) {
            command.outputFPS(fps);
        }
        for(FFNode child:children) {
            child.addOptions(command);
        }
    }

   public void  addCommandOutputs(FFCommand command) {

    String output = conf.getOutput();
    boolean upStreaming = conf.isUpStreaming();

        if (upStreaming) {
            filepath = output;
            List<String> f=new ArrayList<>();
            f.add("-f");
            f.add("flv");
            command.outputOptions(f);
        } else {
            filepath = this.createFilepath();
        }

        command.output(filepath);
    }

   public void  deleteCacheFile() throws IOException {
   boolean debug=conf.isDebug();
        if (!debug && this.directory!=null){
            FileUtils.deleteDirectory(new File(directory));
       }
    }

    // addInputs
    public  void addNodesInputCommand(FFCommand command) {
        for(FFNode  child : children ) {
            child.addInput(command);
        }

    }

    // addOutputs
   public void  addNodesOutputCommand(FFCommand command) {
       for(FFNode  child : children ) {
          child.addOutput(command);
       }
    }

    // filters toCommand
    public void  toFiltersCommand(FFCommand command) {
        List<Object> filters = this.concatFilters();
        command.complexFilter(filters, this.context.input);
    }

    public Filter toTransFilter(int offset) {
        return this.transition.toFilter(offset);
    }

   public void  fillTransition() {
        if (this.transition!=null) {
            FFBaseConf cf=new FFBaseConf();
            cf.setName("fade");
            cf.setTansDuration(0.5f);
            this.setTransition(cf,0.5f);
        }
    }

    /**
     * Combine various filters as ffmpeg parameters
     * @private
     */
   public List<Object> concatFilters() {
        for(FFNode  child : children ) {
            List<Object> filters = child.concatFilters(this.context);
            if (!filters.isEmpty()) {
                this.filters.addAll(filters);
            }
        }
        return this.filters;
    }

  public  int   getTotalFrames() {
        return conf.getFps() * this.duration;
    }

    // add command event emitter3
   public void  addCommandEvents(FFCommand command) {
//    const log = this.rootConf('log');
//        FFmpegUtil.addCommandEvents({
//                log,
//                command,
//                type: 'single',
//                totalFrames: this.getTotalFrames(),
//                start: this.commandEventHandler.bind(this),
//                error: this.commandEventHandler.bind(this),
//                complete: this.commandEventHandler.bind(this),
//                progress: this.commandEventHandler.bind(this),
//    });
    }

//    commandEventHandler(event) {
//        event.target = this;
//        this.emits(event);
//    }

   public void  addAudio() {
       String audio = conf.getAudio();
       boolean loop = conf.isAudioLoop();
       if (audio != null) {
           FFBaseConf cf = new FFBaseConf();
           cf.setAudio(audio);
           cf.setAudioLoop(loop);
           this.addChild(new FFAudio(cf));
       }
   }

   public boolean isReady() {
//        return new Promise(resolve => {
//                let readyIndex = 0;
//        forEach(this.children, child => {
//                child.isReady().then(() => {
//                        readyIndex++;
//        if (readyIndex >= this.children.length) {
//            resolve();
//        }
//        });
//      });
//    });
       return false;
    }

    public  void destroy() {
        //FFmpegUtil.destroy(this.command);
        super.destroy();
        this.transition.destroy();
        this.transition = null;
        this.context = null;
        this.command = null;
    }

}
