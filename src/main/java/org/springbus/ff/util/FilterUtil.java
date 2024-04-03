package org.springbus.ff.util;

import org.springbus.ff.filter.Filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterUtil {
    public static Object getOverlayFromFilters(List<Object> filters) {
        for (Object filter : filters) {
            if (filter instanceof String) {
                if (Pattern.compile("^overlay=").matcher((String) filter).find()) {
                    return filter;
                }
            } else {
                if (((Filter) filter).getFilter().equals("overlay")) {
                    return filter;
                }
            }
        }
        return null;
    }

    public static String createPadFilter(double scale) {
        return String.format("format=yuva420p,pad=%siw:%sih:(ow-iw)/2:(oh-ih)/2:black@0", scale, scale);
    }

    public static String createScaleFilter(double w) {
        return String.format("scale=%sx%s", w, w);
    }

    // Note: Use a Map to represent a JavaScript object
    public static String createZoomFilter(Map<String, Object> params) {
        StringBuilder filter = new StringBuilder("zoompan=");
        if (params.containsKey("z")) filter.append(String.format("z='%s'", params.get("z")));
        if (params.containsKey("d")) filter.append(String.format(":d='%s'", params.get("d")));
        if (params.containsKey("x")) filter.append(String.format(":x='%s'", params.get("x")));
        if (params.containsKey("y")) filter.append(String.format(":y='%s'", params.get("y")));
        if (params.containsKey("s")) filter.append(String.format(":s='%s'", params.get("s")));
        if (params.containsKey("fps")) filter.append(String.format(":fps='%s'", params.get("fps")));

        return filter.toString();
    }

    public static Filter createOverlayFilter(double x, double y) {
        Map<String, Object> options = new HashMap<>();
        options.put("x", x);
        options.put("y", y);
        return new Filter("overlay", options);
    }

    public static String createSetptsFilter(double appearTime) {
        return String.format("setpts=PTS-STARTPTS+%s/TB", appearTime);
    }

    public static String assembleSRFilter(Map<String, Double> params) {
        StringBuilder filter = new StringBuilder();
        if (params.get("scale") != 1) {
            double scale = Math.floor(params.get("scale"));
            filter.append(String.format("scale=iw*%s:ih*%s,", scale, scale));
        }

        if (params.get("rotate") != 0) {
            double rotate = Utils.angleToPI(params.get("rotate"));
            filter.append(String.format("rotate=%s,", rotate));
        }

        return filter.toString().replaceAll(",$", "");
    }

    public static Object setInputsAndOutputs(Map<String, Object> params) {
        Object filter = params.get("filter");
        if (filter instanceof String) {
            if (Pattern.compile("^overlay=").matcher((String) filter).find()) {
                filter = String.format("[%s][%s]%s[%s]",
                        params.get("contextInputs"),
                        params.get("inputs"),
                        filter,
                        params.get("outputs"));
            } else {
                filter = String.format("[%s]%s[%s]",
                        params.get("inputs"),
                        filter,
                        params.get("outputs"));
            }
        } else {
//            if (((Filter)filter).getFilter().equals("overlay")) {
//                //((Filter)filter).setInputs(Arrays.asList(params.get("contextInputs"), params.get("inputs")));
//                //((Filter)filter).setOutputs(params.get("outputs"));
//            } else {
//               // ((Filter)filter).setInputs(params.get("inputs"));
//              //  ((Filter)filter).setOutputs(params.get("outputs"));
//            }
        }

        return filter;
    }

    public static String createFilterEnable(float duration, float appearTime) {
        if (duration <= 0) duration = 99999.0f;
        return String.format("between(t,%s,%s)", appearTime, duration);
    }

    public static List<String> makeFilterStrings(List<Filter> filters) {
        return filters.stream()
                .map(Filter::toString)
                .collect(Collectors.toList());
    }

    public static void addDurationToOverlay(List<Object> filters, float appearTime, float duration) {
        Object overlay = getOverlayFromFilters(filters);
        if (null == overlay) return;
        if (appearTime <= 0) return;

        int index = filters.indexOf(overlay);
        String enable = createFilterEnable(appearTime, duration);
        if (overlay instanceof String) {
            overlay += ":enable='" + enable + "'";
        } else {
            Filter f = (Filter) overlay;
            f.getOptions().put("enable", enable);
        }
        filters.set(index, overlay);
    }
}