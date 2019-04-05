package ekrueger.Storage;

public class DepStore extends Store implements ProcessObserver {
    /*
    simple depository store
     */
    double a;
    public DepStore(double inputWater, double oldStore, double inA){
        super(inputWater,oldStore);
        this.a = inA;
        this.setOutWater();
        this.runnOff = this.waterStore * inA;
    }

    public double getRunnOff(){
        return this.runnOff;
    }

    @Override
    public void setOutWater(){
        this.outWater = this.inWater * this.a; //  recharges to soil
        this.waterStore = this.waterStore - this.outWater;

    }

    @Override
    public void update(double waterStore) {
        this.setWaterStore(waterStore);
        this.inWater = this.waterStore;

    }
}
