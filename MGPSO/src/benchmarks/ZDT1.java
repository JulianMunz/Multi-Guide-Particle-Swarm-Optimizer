package com.su22572252.ass1.benchmarks;

import com.su22572252.ass1.Vector;
public class ZDT1 {
    public static final double min = 0;
    public static final double max = 1;

    public static double g(Vector x) {
        double sum = 0;
        int n = x.length();
        for (int i = 1; i < n; i++) {
            sum += x.get(i)/(n-1);
        }
        return 1 + ( 9 * (sum));
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
        return x.get(0);
    }

    private static double f2(Vector x) {
        return g(x) * (1 - Math.sqrt( f1(x)/g(x) ));
    }



}
