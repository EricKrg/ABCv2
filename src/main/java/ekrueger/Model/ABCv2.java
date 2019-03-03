package ekrueger.Model;

import ekrueger.Logger;
import ekrueger.Process.Evaporation;
import ekrueger.Process.Infiltration;
import ekrueger.Process.RunOff;
import ekrueger.Storage.BaseStore;
import ekrueger.Storage.SoilWaterStore;

import java.util.List;

/*
todo:
    - logger
    - recharge and evapo, sequential ?

 */

public class ABCv2 {

    private static double storeInit;
    private static boolean verbose;
    private static boolean textOut;
    public List<EnvCon> envData; // input environment data
    public Logger logger;

    public ABCv2(double storeInit,List<EnvCon> envData, boolean inVerbose, boolean inTextOut){
        this.setEnvData(envData);
        setStoreInit(storeInit);
        //
        textOut = inTextOut;
        verbose = inVerbose;

        this.logger = new Logger(this);

        if(isTextOut()){
            logger.toLogFile();
        }

    }

    public void excute(){
        double init = storeInit;
        for(EnvCon env:  this.envData){

            // SOIL WATERSTORE
            SoilWaterStore soilWaterStore = new SoilWaterStore(env.getPrecip(),init);
            BaseStore baseStore = new BaseStore(0,0); // empty, is filled with the first infiltration event
            // PROCESS EVAPORATION
            Evaporation evaporation = new Evaporation(soilWaterStore, env);
            evaporation.evaporate();
            // PROCESS INFILTRATION
            Infiltration infiltration = new Infiltration(soilWaterStore,baseStore,0.5, evaporation.getPotEvapo());
            infiltration.recharge();

            //RunOff
            RunOff runOff = new RunOff(soilWaterStore,baseStore);


            if(isVerbose()){
                logger.log(init);
                logger.log(evaporation);
                logger.log(infiltration);
                logger.log(soilWaterStore);
                logger.log(runOff);
            }
            if(isTextOut()){
                logger.writeLogFile(init);
                logger.writeLogFile(evaporation);
                logger.writeLogFile(infiltration);
                logger.writeLogFile(soilWaterStore);
                logger.writeLogFile(runOff);
            }

            //init = soilWaterStore.waterStore; // reset oldstore with new store value
            logger.save();

        }
    }

    // generated getter setter


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
}
