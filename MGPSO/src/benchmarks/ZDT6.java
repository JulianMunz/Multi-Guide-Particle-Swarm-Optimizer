package com.su22572252.ass1.benchmarks;

import com.su22572252.ass1.Vector;

public class ZDT6 {
    public static final double min = 0;
    public static final double max = 1;

    public static double g(Vector x) {
        double sum = 0;
        int n = x.length();
        for (int i = 1; i < n; i++) {
            sum += x.get(i)/(n-1);
        }
        return 1 + (9 * ( Math.pow(sum, 0.25) ));
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
        double x0 = x.get(0);
        double sine = Math.sin(6 * Math.PI * x0);
        double expo = Math.exp((-4)*x0) * Math.pow(sine,6);
        return (1 - expo);
    }

    private static double f2(Vector x) {
        double g = g(x);
        return (g * (1 - Math.pow(x.get(0)/g,2)));
    }
}
