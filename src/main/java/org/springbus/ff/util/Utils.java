package org.springbus.ff.util;

import java.util.UUID;

import static java.lang.Math.floor;

public class Utils {
    public static double angleToRadian(double angle) {
        return floor((angle / 180) * Math.PI);
    }

    public static double angleToPI(double angle) {
        double pi = angleToRadian(angle);
        return pi * Math.PI;
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
