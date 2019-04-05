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

        SoilWaterStore oldSoil = null;
        BaseStore oldBase = null;
        DepStore oldDep = null;

        for(EnvCon env:  this.envData){

            double inDep = (oldDep == null) ? 0 + env.getPrecip() : env.getPrecip() +(oldDep.waterStore - oldDep.runnOff);
            double inSoil = (oldSoil == null) ? 0 : oldSoil.waterStore -oldSoil.runnOff;
            double inBase = (oldBase == null) ? 0 : oldBase.waterStore - oldBase.runnOff;
            // DEPT WATERSTORE
            DepStore depStore = new DepStore(inDep, init, a);  // depstore gives the percip to the soilwaterstore
            // SOIL WATERSTORE
            SoilWaterStore soilWaterStore = new SoilWaterStore(inSoil + depStore.getOutWater(), init, b);
            BaseStore baseStore = new BaseStore(inBase, 0, c ); // empty, is filled with the first infiltration event
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
                logger.logABCv2(this, tempRunoff, evaporation, i, isTextOut());
            }

            //init = soilWaterStore.waterStore; // reset oldstore with new store value
            logger.save();
            oldSoil = soilWaterStore;
            oldBase = baseStore;
            oldDep = depStore;

            i++;

        }
        if(isTextOut()){
            try {
                logger.writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

