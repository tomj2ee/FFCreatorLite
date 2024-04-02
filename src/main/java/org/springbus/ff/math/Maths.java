package org.springbus.ff.math;

import java.math.BigDecimal;

public class Maths {
    public static BigDecimal accAdd(BigDecimal arg1, BigDecimal arg2) {
        int r1, r2, c;
        BigDecimal m;

        try {
            r1 = arg1.scale();
        } catch (Exception e) {
            r1 = 0;
        }

        try {
            r2 = arg2.scale();
        } catch (Exception e) {
            r2 = 0;
        }

        c = Math.abs(r1 - r2);
        m = BigDecimal.valueOf(Math.pow(10, Math.max(r1, r2)));

        if (c > 0) {
            BigDecimal cm = BigDecimal.valueOf(Math.pow(10, c));
            if (r1 > r2) {
                arg1 = arg1.multiply(cm);
            } else {
                arg2 = arg2.multiply(cm);
            }
        }

        return arg1.add(arg2).divide(m);
    }
}
