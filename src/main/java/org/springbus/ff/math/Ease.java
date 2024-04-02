package org.springbus.ff.math;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Ease {
    private Map<String, Function<double[], String>> functions;

    public Ease() {
        functions = new HashMap<>();
        functions.put("linear", this::linear);
        functions.put("quadIn", this::quadIn);
        functions.put("quadOut", this::quadOut);
        functions.put("backIn", this::backIn);
        functions.put("backOut", this::backOut);
    }

    public String getVal(String type, double b, double c, double d, double e) {
        return functions.getOrDefault(type, this::linear).apply(new double[]{b, c, d, e});
    }

    private String linear(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        return b + "+" + c + "*((t-" + e + ")/" + d + ")";
    }

    private String quadIn(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        return b + "+" + c + "*pow(((t-" + e + ")/" + d + "),2)";
    }

    private String quadOut(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        double c2 = 2 * c;
        return b + "+" + c2 + "*((t-" + e + ")/" + d + ")-" + c + "*pow(((t-" + e + ")/" + d + "),2)";
    }

    private String backIn(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        double s = 1.7016;
        double cs = c * s;
        double cs1 = c * (s + 1);
        return cs1 + "*pow(((t-" + e + ")/" + d + "),3)" + cs + "*pow(((t-" + e + ")/" + d + "),2)" + b;
    }

    private String backOut(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        double s = 1.7016;
        double cs = c * s;
        double cs1 = c * (s + 1);
        return cs1 + "*pow(((t-" + e + ")/" + d + ")-1,3)" + cs + "*pow(((t-" + e + ")/" + d + "),2)" + b + "+" + c;
    }
}