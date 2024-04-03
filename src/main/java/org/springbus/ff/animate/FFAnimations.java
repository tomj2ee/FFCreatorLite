package org.springbus.ff.animate;

import org.springbus.ff.filter.Filter;
import org.springbus.ff.node.FFCon;
import org.springbus.ff.node.FFNode;

import java.util.ArrayList;
import java.util.List;

public class FFAnimations {
    private  List<FFAnimation>  list;
    private FFNode target;
    public FFAnimations(List<FFAnimation> animations) {
        list=new ArrayList<>();
        setAnimations(animations);
    }
   public void  setAnimations(List<FFAnimation> animations) {
         for(FFAnimation a :animations){
             addAnimate(a);
         }
    }

   public FFAnimation  addAnimate(FFAnimation animation) {
        this.replaceFadeToAlphaByText(animation);
        if (!animation.isFFAni) {
            animation = new FFAnimation(animation.conf);
        }
        this.list.add(animation);
        animation.parent = this.target;
        return animation;
    }

    private FFAnimation replaceFadeToAlphaByText(FFAnimation animation) {

        if (this.target.type.equals("text")){
            return animation;
        }
        if (!animation.isFFAni) {
            if (animation.type.equals("fade") || animation.type.equals("show")){
                animation.type = "alpha";
            }
        } else {
            if (animation.type.equals("fade") || animation.type.equals("show")) {
                animation.type = "alpha";
            }
        }

        return animation;
    }

    public void setTarget(FFNode ffNode) {
        this.target=ffNode;
    }

    /**
     * add default effect animation
     * @param {String} type - effect type
     * @param {Number} time - effect time
     * @param {Number} delay - effect delay
     * @public
     */
    public  void addEffect(String type, double time, double delay) {
//        let conf = {};
//        if (time === undefined) {
//            conf = Utils.clone(type);
//            type = conf.type;
//            time = conf.time;
//            delay = conf.delay;
//        } else {
//            conf = { type, time, delay };
//        }
//
//    const params = cloneDeep(Effects.effects[type]);
//        if (!params) return;
//
//        if (Array.isArray(params)) {
//            forEach(params, p => {
//                    Utils.excludeBind(p, conf, ['type']);
//            this.addAnimate(p);
//      });
//        } else {
//            Utils.excludeBind(params, conf, ['type']);
//            this.addAnimate(params);
//        }
    }


    public void replaceEffectConfVal() {

    }

    public float getAppearTime() {

        return 0;
    }

    public List<Filter> concatFilters() {
        return null;
    }

    public int  getDuration() {
        return 0;
    }
}
