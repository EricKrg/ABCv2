package ekrueger.Process;

import ekrueger.Storage.BaseStore;
import ekrueger.Storage.SoilWaterStore;

public class RunOff {
    private SoilWaterStore soilWaterStore;
    private BaseStore baseStore;
    public double runOff;

    public RunOff(SoilWaterStore inSoil, BaseStore inBase){
        this.baseStore = inBase;
        this.soilWaterStore = inSoil;
        this.runOff = this.baseStore.getWaterStore() + this.soilWaterStore.getWaterStore();
    }

    public double getRunOff() {
        return runOff;
    }
}
