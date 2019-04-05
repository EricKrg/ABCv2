package ekrueger.Process;

import ekrueger.Storage.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class RunOff implements ProcessSubjects {
    public SoilWaterStore soilWaterStore;
    public BaseStore baseStore;
    public DepStore depStore;
    private ProcessObserver genericStore;
    public double runOff;
    private ArrayList<ProcessObserver> obsList;

    public RunOff(double a, double b, double c, DepStore inDeptStore, SoilWaterStore inSoil, BaseStore inBase){
        this.baseStore = inBase;
        this.soilWaterStore = inSoil;
        this.depStore = inDeptStore;
        this.obsList = new ArrayList(Arrays.asList(this.baseStore, this.soilWaterStore, this.depStore));
        this.runOff = a * this.depStore.getWaterStore() + b * this.soilWaterStore.getWaterStore() + c * this.baseStore.getWaterStore();
        for(ProcessObserver obs : this.obsList){
            double store = 0;
            try {
                String methodName = "getRunnOff";
                Method invokeRunnOff = obs.getClass().getMethod(methodName);
                store = (double) invokeRunnOff.invoke(obs); // pass arg
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.genericStore = obs;
            this.updateObs(store);
        }
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
    @Override
    public void deleteObs(ProcessObserver pObserver) {

    }
    @Override
    public void registerObs(ProcessObserver pObserver) {

    }
    @Override
    public void updateObs(double store) {
        this.genericStore.update(store);

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
