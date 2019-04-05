package ekrueger.Storage;

public class SoilWaterStore extends Store implements ProcessObserver{

    /* to do
    add history fun for store?
     */
    private  double inputwater;

    public double b;

    public SoilWaterStore(double inputWater, double oldStore, double inB){
        super(inputWater,oldStore); // oldstore, water which is allreay stored in this layer, what is inital?
        this.inputwater = inputWater;
        this.b = inB;
        this.runnOff = this.waterStore * this.b;
    }

    public double getRunnOff(){
        return this.runnOff;
    }


    @Override
    public void update(double waterStore){  // update on Evapo and infil
        this.waterStore = waterStore;
        this.runnOff = this.waterStore * this.b;

    }

    @Override
    public String toString() {
        return "SoilWaterStore{" +
                "inWater=" + inWater +
                ", outWater=" + outWater +
                ", waterStore=" + waterStore +
                '}';
    }

    @Override
    void setOutWater() {
    }
}
