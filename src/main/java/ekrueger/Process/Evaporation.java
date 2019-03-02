package ekrueger.Process;

import ekrueger.Model.EnvCon;
import ekrueger.Storage.ProcessObserver;
import ekrueger.Storage.SoilWaterStore;

public class Evaporation implements ProcessSubjects{

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
        this.waterStore = this.waterStore - this.potEvapo; //
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
        this.soilObs.update(this.waterStore);
    }
    // to string
    @Override
    public String toString() {
        return "Evaporation{" +
                "potEvapo=" + potEvapo +
                ", relHum14=" + relHum14 +
                ", es=" + es +
                ", waterStore=" + waterStore +
                ", soilObs=" + soilObs +
                ", environment=" + environment +
                '}';
    }
}
