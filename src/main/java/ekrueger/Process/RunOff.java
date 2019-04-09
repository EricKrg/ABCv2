package ekrueger.Process;

import ekrueger.Storage.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author eric.krueger@uni-jena.de
 */

public class RunOff implements ProcessSubjects {
    /**
     * this class is a process Subject for all Storage classes because all Storages are contributing to the
     * final runOff, the runOff-Class calculates the final-runOff for the given time-step.
     * After that it calculates the final Waterstores for all Storage-Classes, which are used as input for the
     * next time step
     */
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
        for(ProcessObserver obs : this.obsList){ // calculate storage after runnoff and inform storage classes, with fancy invoking
            double run = 0;
            try {
                String getRun = "getRunnOff";
                Method invokeRunnOff = obs.getClass().getMethod(getRun);
                run = (double) invokeRunnOff.invoke(obs); // pass arg
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.genericStore = obs;
            this.updateObs(run);
        }
    }

    public double getTotalRunOff() {
        return runOff;
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
