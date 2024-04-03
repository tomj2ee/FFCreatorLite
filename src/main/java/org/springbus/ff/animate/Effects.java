package org.springbus.ff.animate;

import org.springbus.ff.conf.FFBaseConf;
import org.springbus.ff.filter.Pos;
import org.springbus.ff.node.FFNode;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Effects {
    private static final String target = "_target_";
    private static final String targetLeft = "_target_left_";
    private static final String targetRight = "_target_right_";
    private static final String targetUp = "_target_up_";
    private static final String targetBottom = "_target_bottom_";
    private static final String targetLeftBig = "_target_left_big_";
    private static final String targetRightBig = "_target_right_big_";
    private static final String targetUpBig = "_target_up_big_";
    private static final String targetBottomBig = "_target_bottom_big_";
    private static final String targetRotate = "_target_rotate_";
    private static final String targetRotateAdd = "_target_rotate_add_";
    private static final String targetRotateAddBig = "_target_rotate_add_big_";
    private static final String zoomBig = "_zoom_big_";
    private static final String zoomBiging = "_zoom_biging_";
    private static final String targetSize = "_target_size_";

    private static final int time = 3;
    private static final int inDelay = 0;
    private static final int outDelay = 5;
    private static final double zoomingSpeed = 0.005;
    private static final int moveingSpeed = 50;

    public static FFBaseConf ins = new FFBaseConf("in", time, inDelay, false);
    public static FFBaseConf outs = new FFBaseConf("out", time, outDelay, false);
    public static FFBaseConf ingins = new FFBaseConf("in", 60 * 60, inDelay, true);
    public static FFBaseConf ingouts = new FFBaseConf("out", 60 * 60, outDelay, true);



    public static Pos mapping(String key, FFNode obj) {
        Pos val = null;
        int minDis = 100;
        int maxDis = 400;

        switch (key) {
            case target:
                val = new Pos(obj.x, obj.y);
                break;
            // up / down/ left/ right
            case targetLeft:
                val = new Pos(obj.x - minDis, obj.y);
                break;
            case targetRight:
                val = new Pos(obj.x + minDis, obj.y);
                break;
            case targetUp:
                val = new Pos(obj.x, obj.y - minDis);
                break;
            case targetBottom:
                val = new Pos(obj.x, obj.y + minDis);
                break;

            // big up / down/ left/ right
            case targetLeftBig:
                val = new Pos(obj.x - maxDis, obj.y);
                break;
            case targetRightBig:
                val = new Pos(obj.x + maxDis, obj.y);
                break;
            case targetUpBig:
                val = new Pos(obj.x, obj.y - maxDis);
                break;
            case targetBottomBig:
                val = new Pos(obj.x, obj.y + maxDis);
                break;

            // rotate
            case targetRotate:
                val = new Pos(obj.rotate);
                break;
            case targetRotateAdd:
                val = new Pos(obj.rotate + 60);
                break;
            case targetRotateAddBig:
                val = new Pos(obj.rotate + 180);
                break;

            // zoom
            case zoomBig:
                val = new Pos();
                val.zoom = 1.5;
                break;
            case zoomBiging:
                val = new Pos();
                val.zoom = 2;
                break;
            case targetSize:
                val = new Pos();
                val.size = obj.getSize();
                break;
        }
        return val;
    }
}