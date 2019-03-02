package ekrueger.Storage;

import ekrueger.Model.EnvCon;

public class SoilWaterStore extends Store implements ProcessObserver{

    /* to do
    add history fun for store?
     */
    private double evaporated; // how much water was evaporated in this store ?
    private double infiltraded; // how much water was infiltrated from this store?

    public SoilWaterStore(double inputWater, double oldStore){
        super(inputWater,oldStore); // oldstore, water which is allreay stored in this layer, what is inital?
    }
    @Override
    public void update(double waterStore){
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
        /*
        some magic to calc this
         */
    }
}
