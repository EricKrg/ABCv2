package ekrueger.Process;

import ekrueger.Storage.BaseStore;
import ekrueger.Storage.DepStore;
import ekrueger.Storage.SoilWaterStore;
import ekrueger.Storage.Store;

public class RunOff {
    public SoilWaterStore soilWaterStore;
    public BaseStore baseStore;
    public DepStore depStore;
    public double runOff;

    public RunOff(double a, double b, double c, DepStore inDeptStore, SoilWaterStore inSoil, BaseStore inBase){
        this.baseStore = inBase;
        this.soilWaterStore = inSoil;
        this.depStore = inDeptStore;
        this.runOff = a * this.depStore.getWaterStore() + b * this.soilWaterStore.getWaterStore() + c * this.baseStore.getWaterStore();
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
                "Fast runoff: " + depStore.getWaterStore() +
                ", Intermediate runoff: " + soilWaterStore.getWaterStore() +
                ", Slow runoff: " + baseStore.getWaterStore() +
                ", total runOff: " + runOff +
                '}';
    }
}
