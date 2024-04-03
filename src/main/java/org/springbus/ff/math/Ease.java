package org.springbus.ff.math;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Ease {
    private static Map<String, Function<double[], String>> functions;

    static {
        functions = new HashMap<>();
        functions.put("linear", Ease::linear);
        functions.put("quadIn", Ease::quadIn);
        functions.put("quadOut", Ease::quadOut);
        functions.put("backIn", Ease::backIn);
        functions.put("backOut", Ease::backOut);
    }

    public static String getVal(String type, double b, double c, double d, double e) {
        return functions.getOrDefault(type, Ease::linear).apply(new double[]{b, c, d, e});
    }

    private static String linear(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        return b + "+" + c + "*((t-" + e + ")/" + d + ")";
    }

    private static String quadIn(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        return b + "+" + c + "*pow(((t-" + e + ")/" + d + "),2)";
    }

    private static String quadOut(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        double c2 = 2 * c;
        return b + "+" + c2 + "*((t-" + e + ")/" + d + ")-" + c + "*pow(((t-" + e + ")/" + d + "),2)";
    }

    private static String backIn(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        double s = 1.7016;
        double cs = c * s;
        double cs1 = c * (s + 1);
        return cs1 + "*pow(((t-" + e + ")/" + d + "),3)" + cs + "*pow(((t-" + e + ")/" + d + "),2)" + b;
    }

    private static String backOut(double[] args) {
        double b = args[0], c = args[1], d = args[2], e = args[3];
        double s = 1.7016;
        double cs = c * s;
        double cs1 = c * (s + 1);
        return cs1 + "*pow(((t-" + e + ")/" + d + ")-1,3)" + cs + "*pow(((t-" + e + ")/" + d + "),2)" + b + "+" + c;
    }
}