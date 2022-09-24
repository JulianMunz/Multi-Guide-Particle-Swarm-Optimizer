package com.su22572252.ass1;

import java.util.Arrays;

public class Particle {
    public Vector position;
    private Vector velocity;
    public Vector pbest;
    public double pbest_val;
    double w = 0.475;
    double c1 = 1.8;
    double c2 = 1.1;
    double c3 = 1.8;
    double lambda;
    int swarm;

    public Particle (double[] start, int swarmNum, int type) {
        position = new Vector(start);
        pbest = position;
        swarm = swarmNum;
        if (type == 0) {
            pbest_val = MGPSO.evalFunc(swarm, pbest);
        } else {
            pbest_val = SpeciationPSO.evalFunc(swarm, pbest);
        }
        lambda = Math.random();
        double[] zeroes = new double[position.length()];
        Arrays.fill(zeroes, 0);
        velocity = new Vector(zeroes);

    }

    private void generateParams() {
        boolean flag = true;
        double _c1 = 0, _c2 = 0, _c3 = 0, _w = 0;
        double LHS, RHS, TOP;
        while (flag) {
            _c1 = Math.random() * 2;
            _c2 = Math.random() * 2;
            _c3 = Math.random() * 2;
            _w = Math.random();
            LHS = _c1 + lambda*_c2 + (1-lambda)*_c3;
            TOP = _c1*_c1 + Math.pow(lambda,2)*_c2*_c2 + Math.pow((1-lambda),2)*_c3*_c3 ;
            RHS = (4*(1-_w*_w)) / (1 - _w + TOP / 3*Math.pow(LHS , 2));
            if (LHS < RHS) {
                flag = false;
            }
        }
        c1 = _c1;
        c2 = _c2;
        c3 = _c3;
        w = _w;
    }

    public double[] randomArray(){
        double[] arr = new double[position.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.random();
        }
        return arr;
    }

    public void updateVelocity(Vector gbest, Vector archiveChoice) {
        generateParams();
        Vector r1 = new Vector(randomArray());
        Vector r2 = new Vector(randomArray());
        Vector r3 = new Vector(randomArray());

        velocity.scalarMult(w);
        r1.scalarMult(c1);
        r2.scalarMult(c2);
        r3.scalarMult(c3);
        r2.scalarMult(lambda);
        r3.scalarMult(1-lambda);

        Vector cognitive = Vector.minus(pbest, position);
        cognitive.product(r1);
        Vector social = Vector.minus(gbest, position);
        social.product(r2);
        Vector archive = Vector.minus(archiveChoice, position);
        archive.product(r3);

        velocity = Vector.add(velocity, cognitive);
        velocity = Vector.add(velocity, social);
        velocity = Vector.add(velocity, archive);
    }

    public void updatePbest(int type) {
        double temp ;
        if (type == 0) {
            temp = MGPSO.evalFunc(swarm, position);
        } else {
            temp = SpeciationPSO.evalFunc(swarm, position);
        }
       ;
        if (pbest_val > temp) {
            pbest = Vector.copyVector(position);
            pbest_val = temp;
        }
    }

    public void updatePosition() {
        position = Vector.add(position, velocity);
    }

}
