package com.su22572252.ass1.benchmarks;

import com.su22572252.ass1.Vector;

public class ZDT4 {
    public static final double min = -5;
    public static final double max = 5;

    public static double g(Vector x) {
        int n = x.length();
        double sum = 0;
        for (int i = 1; i < n; i++) {
            double xi = x.get(i);
            sum += (xi*xi - 10*Math.cos(4*Math.PI*xi));
        }
        return 1 + 10*(n-1) + sum;
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
        double d = x.get(0);
        if (d > 1 || d < 0) {
            return Double.MAX_VALUE;
        }
        return d;
    }

    private static double f2(Vector x) {
        double d = x.get(0);
        if (d > 1 || d < 0) {
            return Double.MAX_VALUE;
        }
        return g(x) * (1 - Math.sqrt(d/g(x)));
    }


}
