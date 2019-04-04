package ekrueger.Model;

import ekrueger.DataReader;

import java.util.*;

public class Calibrator {

    public double a;
    public double b;
    public double c;
    private ABCv2 model;
    private DataReader calibData;
    private int[] iterator;
    public Map<Integer, Double> bestMap;
    public ArrayList<ABCv2> modelList;

    public Calibrator(ABCv2 model, DataReader calibData, int iterations){
        this.iterator = new int[iterations];
        this.model = model;
        this.modelList = new ArrayList<>();
        this.calibData = calibData;
        this.calibrate();

    }

    private void calibrate(){
        this.bestMap = new HashMap<Integer, Double>();
        for(int i = 0; i < this.iterator.length; i++){
            ArrayList <Double> resList = new ArrayList<Double>();
            Random r = new Random();
            double a = 0 + (1 - 0) * r.nextDouble();
            double b = 0 + (8 - 0) * r.nextDouble();
            double c = 0 + (1 - 0) * r.nextDouble();
            model.setA(a);
            model.setB(b);
            model.setC(c);
            ArrayList<Double> runoffList = model.excute();

            double corr = correlation(runoffList, calibData.calibList);
            bestMap.put(i, corr);
            this.modelList.add(model);
        }
    }

    public double getA() {
        return this.model.getA();
    }

    public double getB() {
        return this.model.getB();
    }

    public double getC() {
        return this.model.getC();
    }

    public static double correlation(ArrayList<Double> xs, ArrayList<Double> ys) {
        //TODO: check here that arrays are not null, of the same length etc
        if(xs.size() > ys.size()){
            xs =new ArrayList<Double>(xs.subList(0, ys.size()));
        }
        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;

        int n = xs.size();

        for(int i = 0; i < n; ++i) {
            double x = xs.get(i);
            double y = ys.get(i);

            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }

        // covariation
        double cov = sxy / n - sx * sy / n / n;
        // standard error of x
        double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
        // standard error of y
        double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

        // correlation is just a normalized covariation
        return cov / sigmax / sigmay;
    }
}
