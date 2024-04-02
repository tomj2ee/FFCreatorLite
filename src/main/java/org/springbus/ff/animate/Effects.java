package org.springbus.ff.animate;

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
    private static final Map<String, Object> ins = new HashMap<>();
    private static final Map<String, Object> outs = new HashMap<>();

    static {
        ins.put("showType", "in");
        ins.put("time", time);
        ins.put("delay", inDelay);

        outs.put("showType", "out");
        outs.put("time", time);
        outs.put("delay", outDelay);
    }

    private static final Map<String, Map<String, Object>> effects = new HashMap<>();

    static {
        // Initialize `effects` map here, similar to JS version
        // ...
    }

    public static Object mapping(String key, Object obj) {
        // Implement this method similar to JS version
        // ...
        return null;
    }
}