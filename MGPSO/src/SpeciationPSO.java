package com.su22572252.ass1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SpeciationPSO {
    private static int dim ;
    private final int num_p;
    private ArrayList<Vector> archive = new ArrayList<>();
    private ArrayList<Vector> seeds ;
    private final int ARCHIVE_SIZE;
    private final int MAX_ITERS = 2000;
    private final int numSwarm;
    private final int ppSwarm;
    private final double r = 1/15;
    public static function function;
    public SpeciationPSO(int swarms, int func) {
        ARCHIVE_SIZE = 60;
        this.dim = 2;
        this.num_p = 60;
        numSwarm = swarms;
        ppSwarm = num_p/numSwarm;
        this.function = new function(func);
    }

    public void doSPSO() {
        archive = new ArrayList<>();
        ArrayList<ArrayList<Particle>> swarms = new ArrayList<>();
        for (int i = 0; i < numSwarm; i++) {
            ArrayList<Particle> particles = new ArrayList<>();
            for (int j = 0; j < ppSwarm; j++) {
                particles.add(new Particle(randomArray(), i, 1)) ;
            }
            swarms.add(particles);
        }

        int t = 0;
        while (t < MAX_ITERS) {
            for (int m = 0; m < numSwarm; m++) {
                for (int i = 0; i < ppSwarm; i++) {
                    updateArchive(swarms.get(m).get(i).position);
                }
            }
            Vector[] arc = betterCrowding();
            for (int m = 0; m < numSwarm; m++) {
                updateSeeds(swarms.get(m), m);
                for (int i = 0; i < ppSwarm; i++) {
                    Vector neighBest = null;
                    for (Vector v: seeds) {
                        if (v.distance(swarms.get(m).get(i).position) <= r) {
                            neighBest = v;
                            break;
                        }
                    }
                    swarms.get(m).get(i).updateVelocity(neighBest, archive.get(tSelection(arc)));
                    swarms.get(m).get(i).updatePosition();
                    swarms.get(m).get(i).updatePbest(1);
                }
            }
            /*
            if (t % 50 == 0) {

            }*/

            t++;
        }
        ArrayList<Vector> A = new ArrayList<>();
        for (Vector v : archive) {
            A.add(new Vector(evalFunc(0,v), evalFunc(1,v)));
        }
        System.out.println(spacing(A));

        /*
        ArrayList<Vector> A = new ArrayList<>();
        System.out.println("Archive: ");
        for (Vector v : archive) {
            A.add(new Vector(evalFunc(0,v), evalFunc(1,v)));
            System.out.println(evalFunc(0, v) + "," + evalFunc(1, v));
        }
        System.out.println("IGD: ");
        System.out.println(igd(A));*/
    }

    public double spacing(ArrayList<Vector> A) {
        double sum = 0;
        double dis;
        double min;
        for (int i = 0; i < A.size(); i++) {
            min = Double.MAX_VALUE;
            for (int j = 0; j < A.size(); j++) {
                if (A.get(i) == A.get(j)) {
                    continue;
                }
                dis = A.get(i).distance(A.get(j));
                if (dis < min) {
                    min = dis;
                }
            }
            sum += min;
        }
        double ave_dis =  (sum/A.size());
        sum = 0;
        for (int i = 0; i < A.size(); i++) {
            min = Double.MAX_VALUE;
            for (int j = 0; j < A.size(); j++) {
                if (A.get(i) == A.get(j)) {
                    continue;
                }
                dis = A.get(i).distance(A.get(j));
                if (dis < min) {
                    min = dis;
                }
            }
            sum += Math.pow(ave_dis - min, 2);
        }
        sum = sum/(A.size()-1);
        return Math.sqrt(sum);
    }


    public double igd(ArrayList<Vector> A) {
        double sum = 0;
        for (int i = 0; i < function.R.size(); i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < A.size(); j++) {
                double dis = function.R.get(i).distance(A.get(j));
                if (dis < min) {
                    min = dis;
                }
            }
            sum += min;
        }
        return (sum/function.R.size());
    }

    private void updateSeeds(ArrayList<Particle> particles, int swarm) {
        Vector[] v = new Vector[particles.size()];
        for (int i = 0; i < particles.size(); i++) {
            v[i] = Vector.copyVector(particles.get(i).position);
        }
        Comparator<Vector> cmp = (t1, t2) -> (-1) * Double.compare(evalFunc(swarm, t1), evalFunc(swarm, t2));
        Arrays.sort(v,cmp);
        seeds = new ArrayList<>();
        for (Vector v_i:v) {
            boolean found = false;
            for (Vector s :seeds) {
                if (s.distance(v_i) <= r) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                seeds.add(v_i);
            }
        }
    }

    public void updateArchive(Vector v) {
        if (archive.size() == 0) {
            archive.add(Vector.copyVector(v));
        } else {
            boolean dom = true;
            for (int i = 0; i < archive.size(); i++) {
                if (isDominant(archive.get(i), v)) {
                    dom = false;
                    break;
                }
            }
            if (dom) {
                archive.add(Vector.copyVector(v));
                ensureDominance();
                if (archive.size() > ARCHIVE_SIZE) {
                    archive.remove(checkAllCrowds());
                }
            }
        }
    }

    public void ensureDominance() {
        int count = 0;
        while (count < archive.size()-1) {
            if (isDominant(archive.get(archive.size()-1),archive.get(count))) {
                archive.remove(count);
                count--;
            }
            count++;
        }
    }


    private int checkAllCrowds() {
        double smallestDist = Double.MAX_VALUE;
        Vector v = null;
        double dist;
        Vector[] arc = betterCrowding();
        for (int i = 0; i < archive.size(); i++) {
            dist = arc[i].crowdingDistance;
            if (smallestDist > dist) {
                smallestDist = dist;
                v = arc[i];
            }
        }
        for (int i = 0; i < archive.size(); i++) {
            if (v.equals(archive.get(i))) {
                return i;
            }
        }
        return -100;
    }

    private int tSelection(Vector [] arc) {
        int i = (int) (Math.random() * archive.size());
        int j = (int) (Math.random() * archive.size());
        while (j == i) {
            j = (int) (Math.random() * archive.size());
        }
        if (arc[i].crowdingDistance > arc[j].crowdingDistance) {
            return i;
        }
        return j;
    }

    private Vector[] betterCrowding() {
        Vector[] sortedArchive = new Vector[archive.size()];
        for (int j = 0; j < archive.size(); j++) {
            sortedArchive[j] = Vector.copyVector(archive.get(j));
            sortedArchive[j].crowdingDistance = 0;
        }
        double div = function.max - function.min;
        for (int i = 0; i < numSwarm; i++) {
            int finalI = i;
            Comparator<Vector> cmp = (t1, t2) -> Double.compare(evalFunc(finalI, t1), evalFunc(finalI, t2));
            Arrays.sort(sortedArchive, cmp);
            sortedArchive[0].crowdingDistance = function.max - function.min;
            sortedArchive[sortedArchive.length-1].crowdingDistance = function.max - function.min;
            for (int j = 1; j < sortedArchive.length-1; j++) {
                sortedArchive[j].crowdingDistance += (evalFunc(i, sortedArchive[j+1])-evalFunc(i, sortedArchive[j-1]))/div ;
            }
        }
        return sortedArchive;
    }


    public boolean isDominant(Vector v1, Vector v2) {
        boolean flag = false;
        for (int i = 0; i < numSwarm; i++) {
            if (evalFunc(i, v1) > evalFunc(i, v2)) {
                return false;
            } else if (evalFunc(i, v1) < evalFunc(i, v2)) {
                flag = true;
            }
        }
        return flag;
    }

    public static double[] randomArray(){
        double[] arr = new double[dim];
        for (int i = 0; i < dim; i++) {
            arr[i] = Math.random();
        }
        return arr;
    }

    public static double evalFunc(int func, Vector p) {
        return function.evaluateFunction(func, p);
    }

    public static void main(String[] args) {

        //System.out.println("t,f1,f2");
        for (int i = 0; i < 20; i++) {
            SpeciationPSO spso = new SpeciationPSO(2,4);

            spso.doSPSO();
            /*
            Comparator<Vector> cmp = (t1, t2) -> Double.compare(evalFunc(0, t1), evalFunc(0, t2));
            Vector[] v = new Vector[spso.archive.size()];
            for (int j = 0; j < spso.archive.size(); j++) {
                v[j] = Vector.copyVector(spso.archive.get(j));
            }
            Arrays.sort(v, cmp);
            for (int j = 0; j < v.length; j++) {
                System.out.println(j + "," + evalFunc(0, v[j]) + "," + evalFunc(1, v[j]));
            }*/
        }

    }

}
