package ekrueger.Model;

import ekrueger.DataReader;
import ekrueger.Logger;
import me.tongfei.progressbar.ProgressBar;

import java.util.*;
import java.util.stream.Collectors;

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
        //ProgressBar pb = new ProgressBar("Calibrate",this.iterator.length);

        this.bestMap = new HashMap<Integer, Double>();
        for(int i = 0; i < this.iterator.length; i++){
            //pb.step();
            Logger logger = new Logger(i);
            logger.progressPercentage(i, this.iterator.length, "calibrate");
            ArrayList <Double> resList = new ArrayList<Double>();
            Random r = new Random();
            double a = 0 + (1 - 0) * r.nextDouble();
            double b = 0 + (1 - 0) * r.nextDouble();
            double c = 0 + (1 - 0) * r.nextDouble();
            ABCv2 tmpModel = new ABCv2(0, this.model.envData, false,false, a,b,c);
            ArrayList<Double> runoffList = tmpModel.excute();

            double corr = correlation(runoffList, calibData.calibList);
            bestMap.put(i, corr);
            this.modelList.add(tmpModel);
        }
        ABCv2 bestModell = null;
        double accept = 0.1;  // variance acceptance
        while (bestModell == null){
            bestModell = this.getBestModel(accept);
            accept = accept + 0.1;
        }
        this.setModel(bestModell);


    }

    public ABCv2 getBestModel(double accept){
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
        double cor = cov / sigmax / sigmay;
        return cor;
    }

    public double nashSutcliffEff(ABCv2 bestPicks){
        OptionalDouble mesMean =  calibData.calibList.stream().
                mapToDouble(a -> a).average();
        double mAll = 0;
        for(double m: calibData.calibList){
            mAll =+ Math.pow((m - mesMean.getAsDouble()),2);
        }
        double simAll = 0;
        for(int index = 0; index < calibData.calibList.size(); index++){
            simAll =+ Math.pow(bestPicks.runOffList.get(index) - calibData.calibList.get(index), 2);
        }
        double nse = 1 - (simAll/mAll);
        return nse;
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

    public void setModel(ABCv2 model) {
        this.model = model;
    }

}
