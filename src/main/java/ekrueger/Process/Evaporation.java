package ekrueger.Process;

import ekrueger.Model.EnvCon;
import ekrueger.Storage.ProcessObserver;
import ekrueger.Storage.SoilWaterStore;

/**
 * @author eric.krueger@uni-jena.de
 */

public class Evaporation implements ProcessSubjects{
    /**
     * this class is a process subject for the soilwaterstore, it calculates the potEvapo based on the waterstore
     * of the soilStore, temp in degC and haude factor
     * all calculations are based on the material given from the instructor
     */
    public double potEvapo;
    private double relHum14; // rel. humi at 14:00
    private double es;
    private double waterStore;
    private ProcessObserver soilObs;
    private EnvCon environment;

    public Evaporation(SoilWaterStore soilWaterStore, EnvCon environment){
        this.waterStore = soilWaterStore.getWaterStore();
        this.environment = environment;
        this.soilObs = soilWaterStore; // set observer Store

        this.es = this.calcEs(); // calc needed factors for potEvapo
        this.setRelHum14(environment);
        this.setPotEvapo(environment);

    }

    private void setPotEvapo(EnvCon environment){ // calc potential Evapo for this day
        this.potEvapo = environment.getHaude() * this.es * (1- (this.relHum14/100));

    }
    private void setRelHum14(EnvCon environment){ // calc rel hum at 14:00
        double atMean = this.calcAt(environment.meanTemp);
        double atMax = this.calcAt(environment.maxTemp);
        this.relHum14 = (atMean*environment.getRelHum())/atMax;
    }

    private double calcEs(){ // es factor
        double t = ((17.62*environment.getMeanTemp())/(243.12+environment.getMeanTemp()));
        double es = 6.11f * Math.exp(t);
        return es;
    }

    private double calcAt(double temp){ // calc max. hum A(temp)
        return this.es * 216.7f/(temp+273.15f);
    }

    public void evaporate(){ // execute evapo process and inform observer
        this.waterStore = (this.potEvapo > this.waterStore) ? 0 : this.waterStore - this.potEvapo; // prevent negative waterstores
        updateObs(this.waterStore);
    }

    public double getPotEvapo() {
        return potEvapo;
    }

    // observer related funs
    @Override
    public void deleteObs(ProcessObserver pObserver) {

    }
    @Override
    public void registerObs(ProcessObserver pObserver) {

    }
    @Override
    public void updateObs(double waterStore) {
        /**
         * update the corresponding Observer (Soilwaterstore) with the new waterstore
         */
        this.soilObs.update(this.waterStore);
    }
    // to string
    @Override
    public String toString() {
        return "Evaporation{" +
                "potEvapo=" + potEvapo +
                ", waterStore=" + waterStore +
                ", soilObs=" + soilObs +
                '}';
    }
}
