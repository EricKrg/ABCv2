package ekrueger.Process;

import ekrueger.Storage.BaseStore;
import ekrueger.Storage.SoilWaterStore;
import ekrueger.Storage.Store;

public class RunOff {
    private SoilWaterStore soilWaterStore;
    private BaseStore baseStore;
    public double runOff;

    public RunOff(SoilWaterStore inSoil, BaseStore inBase){
        this.baseStore = inBase;
        this.soilWaterStore = inSoil;
        this.runOff = this.baseStore.getWaterStore() + this.soilWaterStore.getWaterStore();
    }

    public double getTotalRunOff() {
        return runOff;
    }
    /*
    todo:
        - a * deptstor + b * soilstore + c * basestore
        - deptstore?
        - a,b,c???
     */

    public  double getSimRunOff(){
        return 0.0;

    }

    public double getSpecificRunoff(Store inStore){
        return inStore.waterStore;
    }

    @Override
    public String toString() {
        return "RunOff{" +
                "Intermediate runoff: " + soilWaterStore.getWaterStore() +
                ",Slow runoff: " + baseStore.getWaterStore() +
                ", total runOff: " + runOff +
                '}';
    }
}
