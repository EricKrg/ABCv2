package ekrueger.Storage;

public class SoilWaterStore extends Store implements ProcessObserver{

    /* to do
    add history fun for store?
     */
    private  double inputwater;

    public SoilWaterStore(double inputWater, double oldStore){
        super(inputWater,oldStore); // oldstore, water which is allreay stored in this layer, what is inital?
        this.inputwater = inputWater;
    }
    @Override
    public void update(double waterStore){  // update on Evapo and infil
        this.outWater = this.inputwater - waterStore;
        this.setWaterStore(waterStore);

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
