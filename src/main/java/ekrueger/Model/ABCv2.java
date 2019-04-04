package ekrueger.Model;

import ekrueger.DataReader;
import ekrueger.Logger;
import ekrueger.Process.Evaporation;
import ekrueger.Process.Infiltration;
import ekrueger.Process.RunOff;
import ekrueger.Storage.BaseStore;
import ekrueger.Storage.DepStore;
import ekrueger.Storage.SoilWaterStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
todo:
    - logger
    - what if there is no percip ??

 */

public class ABCv2 {

    private static double storeInit;
    private static boolean verbose;
    private static boolean textOut;
    private double a;
    private double b;
    private double c;
    public DataReader calibData;
    public Map<String, Double> calibFit = new HashMap<>();
    public ArrayList<Double>  runOffList;
    public List<EnvCon> envData; // input environment data
    public Logger logger;

    public ABCv2(double storeInit,List<EnvCon> envData, boolean inVerbose, boolean inTextOut,
                 double inA , double inB, double inC,DataReader ... inCalibData){
        this.setEnvData(envData);
        setStoreInit(storeInit);
        a = inA;
        b = inB;
        c = inC;
        if (inCalibData.length > 0) {
            this.calibData = inCalibData[0];
        }
        //
        textOut = inTextOut;
        verbose = inVerbose;

        this.logger = new Logger(this);

        if(isTextOut()){
            logger.toLogFile();
        }

    }

    public ArrayList<Double> excute(){
        double init = storeInit;
        int i = 0;
        ArrayList<Double> runOff = new ArrayList<>();
        for(EnvCon env:  this.envData){
            if(env.getPrecip() > 0) {
                // DEPT WATERSTORE
                DepStore depStore = new DepStore(env.getPrecip(), init, a);  // depstore gives 50% of the percip to the soilwaterstore
                // SOIL WATERSTORE
                SoilWaterStore soilWaterStore = new SoilWaterStore(depStore.getOutWater(), init);
                BaseStore baseStore = new BaseStore(0, 0); // empty, is filled with the first infiltration event
                // PROCESS EVAPORATION
                Evaporation evaporation = new Evaporation(soilWaterStore, env);
                evaporation.evaporate();
                // PROCESS INFILTRATION
                Infiltration infiltration = new Infiltration(soilWaterStore, baseStore, c, evaporation.getPotEvapo());
                infiltration.recharge();
                //RunOff
                RunOff tempRunoff = new RunOff(a, b, c, depStore, soilWaterStore, baseStore);

                runOff.add(tempRunoff.runOff);

                if (isVerbose()) {
                    /*
                    logger.log(init);
                    logger.log(evaporation);
                    logger.log(infiltration);
                    logger.log(soilWaterStore);
                    logger.log(runOff);
                    *
                     */
                    logger.logABCv2(this, tempRunoff, evaporation, i);
                }
                if (isTextOut()) {
                    logger.writeLogFile(init);
                    logger.writeLogFile(evaporation);
                    logger.writeLogFile(infiltration);
                    logger.writeLogFile(soilWaterStore);
                    logger.writeLogFile(runOff);
                }

                //init = soilWaterStore.waterStore; // reset oldstore with new store value
                logger.save();
            } else {
                runOff.add(0d);
            }
            i++;

        }
        this.runOffList = runOff;
        return runOff;
    }

    public void setCalibFit(String measure, double value){
        this.calibFit.put(measure, value);
    }

    // generated getter setter


    public Map<String, Double> getCalibFit() {
        return calibFit;
    }

    public static boolean isVerbose() {
        return verbose;
    }

    public static void setVerbose(boolean verbose) {
        ABCv2.verbose = verbose;
    }

    public  List<EnvCon> getEnvData() {
        return envData;
    }

    public void setEnvData(List<EnvCon> envData) {
        this.envData = envData;
    }

    public static double getStoreInit() {
        return storeInit;
    }

    public static void setStoreInit(double storeInit) {
        ABCv2.storeInit = storeInit;
    }

    public static boolean isTextOut() {
        return textOut;
    }

    public static void setTextOut(boolean textOut) {
        ABCv2.textOut = textOut;
    }

    public double getA() {
        return this.a;
    }
    public  double getB() {
        return this.b;
    }
    public  double getC() {
        return this.c;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }
}

