package org.springbus.ff.util;

import java.util.UUID;

public class Utils {
    public static double angleToRadian(double angle) {
        return floor((angle / 180) * Math.PI);
    }

    public static double angleToPI(double angle) {
        double pi = angleToRadian(angle);
        return pi * Math.PI;
    }

    public static double  floor(double n) {
        double k = Math.pow(10, 2);
        return Math.floor(n * k) / k;
    }

    public static String replacePlusMinus(String str) {
        return str
                .replace("+-", "-")
                .replace("-+", "-")
                .replace("--", "+")
                .replace("++", "+")
                .replace("(t-0)", "t")
                .replace("(on-0)", "on");
    }

    public static String uid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
