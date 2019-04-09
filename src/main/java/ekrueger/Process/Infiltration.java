package ekrueger.Process;

import ekrueger.Storage.BaseStore;
import ekrueger.Storage.ProcessObserver;
import ekrueger.Storage.SoilWaterStore;

/**
 * @author eric.krueger@uni-jena.de
 */

public class Infiltration implements ProcessSubjects {
    /**
     * this class is a process Subject for the SoilWaterStore and the BaseStore
     * it calculates the infiltration between the SoilStore and BaseStore, it thus lowers the Waterstore
     * of the Soilwaterstore and adds Water to the Waterstore of the BaseStore
     */
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
    public void updateObs(double recharge) { // increase and decrease Observing stores
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
