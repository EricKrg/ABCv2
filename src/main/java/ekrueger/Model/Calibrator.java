package ekrueger.Model;

import ekrueger.DataReader;
import ekrueger.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author eric.krueger@uni-jena.de
 */

public class Calibrator {

    public double a;
    public double b;
    public double c;
    public ABCv2 model;
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
        /**
         * generates random values for a,b,c and executes the model with those parameters
         * in the first step the correlation between the model and the calibration data is calculated
         * then the top-n (depends on acceptance) models are used the calculate the nse, the model with the max
         * nse is choosen as model for all further steps
         */
        System.out.println("calibrate ...");
        this.bestMap = new HashMap<Integer, Double>();
        for(int i = 0; i < this.iterator.length; i++){
            Logger logger = new Logger(i);
            logger.progressPercentage(i, this.iterator.length, "calibrate");
            ArrayList <Double> resList = new ArrayList<Double>();
            Random r = new Random();
            double a = 0 + (1 - 0) * r.nextDouble();
            double b = 0 + (1 - 0) * r.nextDouble();
            double c = 0 + (1 - 0) * r.nextDouble();
            ABCv2 tmpModel = new ABCv2(0, this.model.envData, false,false, a,b,c);
            ArrayList<Double> runoffList = tmpModel.execute();

            double corr = correlation(runoffList, calibData.calibList);
            bestMap.put(i, corr);
            this.modelList.add(tmpModel);
        }
        ABCv2 bestModell = null;
        double accept = 0.1;  // variance acceptance, acceptance is weakened if there is no model with a satisfing nse
        while (bestModell == null){
            bestModell = this.getBestModel(accept);
            accept = accept + 0.1;
        }
        this.setModel(bestModell);
    }

    public ABCv2 getBestModel(double accept){
        /**
         * picks the best model with the highest nse, if there is no model with a nse over 0 it returns null,
         * this function is then called again with a higher accept value
         */
        Map<Integer, Double> bestPick = this.pickBestCorr(accept);

        double cor = 0;
        double nse = 0;
        ABCv2 bestModell = null;
        for(int modelNr : bestPick.keySet()){

            double newNse = this.nashSutcliffEff(this.modelList.get(modelNr));
            if (newNse > nse){
                nse = newNse;
                bestModell = this.modelList.get(modelNr);
                cor = bestPick.get(modelNr);
            }
        }
        if (bestModell == null){ return  null; }
        bestModell.setCalibFit("NSE", nse);
        bestModell.setCalibFit("cor", cor);
        bestModell.setCalibFit("a", bestModell.getA());
        bestModell.setCalibFit("b", bestModell.getB());
        bestModell.setCalibFit("c", bestModell.getC());

        return bestModell;
    }


    public Map<Integer, Double> pickBestCorr(double corRel){
        Map.Entry<Integer, Double> maxEntry = null;
        for (Map.Entry<Integer,Double> entry : this.bestMap.entrySet()) {
            if(entry.getValue().isNaN()){
                continue;
            }
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
       final Map.Entry<Integer, Double> max = maxEntry;
       return this.bestMap.entrySet().stream().
               filter(entry -> entry.getValue() > max.getValue() -(max.getValue()*corRel)).
               collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static double correlation(ArrayList<Double> xs, ArrayList<Double> ys) {
        /**
         * simple correlation between two arrays,
         */
        xs =(xs.size() > ys.size()) ? new ArrayList<Double>(xs.subList(0, ys.size())) : xs;
        ys =(ys.size() > xs.size()) ? new ArrayList<Double>(ys.subList(0, xs.size())) : ys;

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
        double cor = cov / sigmax / sigmay;
        return cor;
    }

    public double nashSutcliffEff(ABCv2 bestPicks){
        OptionalDouble mesMean =  calibData.calibList.stream().
                mapToDouble(a -> a).average();
        double mAll = 0;
        for(double m: calibData.calibList){
            mAll =+ Math.pow(m-mesMean.getAsDouble(),2);
        }
        double simAll = 0;
        for(int index = 0; index < calibData.calibList.size(); index++){
            simAll =+ Math.pow(bestPicks.runOffList.get(index) - calibData.calibList.get(index), 2);
        }
        double nse = 1 - (simAll/mAll);
        return nse;
    }

    // generated getter and setter
    public double getA() {
        return this.model.getA();
    }

    public double getB() {
        return this.model.getB();
    }

    public double getC() {
        return this.model.getC();
    }

    public void setModel(ABCv2 model) {
        this.model = model;
    }

}
