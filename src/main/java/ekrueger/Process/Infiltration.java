package ekrueger.Process;

import ekrueger.Storage.BaseStore;
import ekrueger.Storage.ProcessObserver;
import ekrueger.Storage.SoilWaterStore;

/*
GroundWater recharge = c * (W - Y)
c = ratio groundwater recharge to surface runoff
W = available soilwater = soilWaterStore.waterstore
Y = potEvapo
 */


public class Infiltration implements ProcessSubjects {
    public double recharge;
    private SoilWaterStore soilObs;
    private BaseStore baseObs;
    public double potEvapo;
    private double c;

    public Infiltration(SoilWaterStore soilWaterStore, BaseStore baseStore,double inC, double inPotEvapo ){
        this.soilObs = soilWaterStore;
        this.baseObs = baseStore;
        this.potEvapo = inPotEvapo;
        this.c = inC;

    }

    public void recharge(){ // calc recharge and update observing stores
        this.recharge = (this.potEvapo > this.soilObs.getWaterStore()) ? 0: this.c * (this.soilObs.getWaterStore() - this.potEvapo);
        this.updateObs(this.recharge);
    }

    @Override
    public void updateObs(double recharge) {
        this.soilObs.update(this.soilObs.waterStore - recharge);
        this.baseObs.update(this.baseObs.waterStore + recharge);
    }

    @Override
    public void registerObs(ProcessObserver pObserver) {

    }

    @Override
    public void deleteObs(ProcessObserver pObserver) {

    }

    @Override
    public String toString() {
        return "Infiltration{" +
                "recharge=" + recharge +
                ", soilObs=" + soilObs.getWaterStore() +
                ", baseObs=" + baseObs.getWaterStore() +
                ", potEvapo=" + potEvapo +
                ", c=" + c +
                '}';
    }
}
