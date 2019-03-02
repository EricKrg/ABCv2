package ekrueger.Storage;

import ekrueger.Model.EnvCon;

/*
base construction of a store, specific functions are implemented in the specific class
 */

public abstract class Store {
    public double inWater; // input
    public double outWater; // abfluss
    public double waterStore;  // state



    public Store(double inputWater, double oldStore){
        this.setInWater(inputWater);
        this.setWaterStore(oldStore + this.inWater);
    }

    abstract void setOutWater();

    // generated getter setter

    public double getInWater() {
        return inWater;
    }

    public void setInWater(double inWater) {
        this.inWater = inWater;
    }

    public double getOutWater() {
        return outWater;
    }

    public double getWaterStore() {
        return waterStore;
    }

    public void setWaterStore(double waterStore) {
        this.waterStore = waterStore;
    }
}
