package com.su22572252.ass1.benchmarks;

import com.su22572252.ass1.Vector;

public class ZDT2 {
    public static final double min = 0;
    public static final double max = 1;


    public static double g(Vector x) {
        return ZDT1.g(x);
    }

    public static double evaluateFunction(int func, Vector x) {
        for (double d: x.point) {
            if (d > max || d < min) {
                return Double.MAX_VALUE;
            }
        }
        if (func == 0) {
            return f1(x);
        } else {
            return f2(x);
        }
    }

    private static double f1(Vector x) {
        return ZDT1.evaluateFunction(0,x);
    }

    private static double f2(Vector x) {
        double g = g(x);
        return (g * (1 - Math.pow(x.get(0)/g,2)));
    }


}
