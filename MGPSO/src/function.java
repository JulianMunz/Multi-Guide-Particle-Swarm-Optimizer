package com.su22572252.ass1;

import com.su22572252.ass1.benchmarks.*;

import java.util.ArrayList;

public class function {
    int function;
    double min;
    double max;

    ArrayList<Vector> R = new ArrayList<>();

    public function(int function) {
        R = new ArrayList<>();
        this.function = function;
        switch (function) {
            case 0:
                min = ZDT1.min;
                max = ZDT1.max;
                for (double i = 0; i <= 1; i+=0.02) {
                    R.add(new Vector(i,(1 - Math.sqrt( i ))));
                }
                break;
            case 1:
                min = ZDT2.min;
                max = ZDT2.max;
                for (double i = 0; i <= 1; i+=0.02) {
                    R.add(new Vector(i, 1 - Math.pow(i,2)));
                }
                break;
            case 2:
                min = ZDT3.min;
                max = ZDT3.max;
                for (double i = 0; i <= 1; i+=0.02) {
                    R.add(new Vector(i,1 - Math.pow( i ,0.5) + i * Math.sin(10*Math.PI*i) ));
                }
                break;
            case 3:
                min = ZDT4.min;
                max = ZDT4.max;
                for (double i = 0; i <= 1; i+=0.02) {
                    R.add(new Vector(i,(1 - Math.sqrt( i ))));
                }
                break;
            case 4:
                min = ZDT6.min;
                max = ZDT6.max;
                for (double i = 0; i <= 1; i+=0.02) {
                    R.add(new Vector(i, 1 - Math.pow(i,2)));
                }
                break;
        }
    }

    public double evaluateFunction(int func, Vector x) {
        switch (function) {
            case 0:
                return ZDT1.evaluateFunction(func,x);
            case 1:
                return ZDT2.evaluateFunction(func,x);
            case 2:
                return ZDT3.evaluateFunction(func,x);
            case 3:
                return ZDT4.evaluateFunction(func,x);
            case 4:
                return ZDT6.evaluateFunction(func,x);
        }
        return 0;
    }
}
