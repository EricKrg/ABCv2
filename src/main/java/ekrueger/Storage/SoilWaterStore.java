package ekrueger.Storage;

/**
 * @author eric.krueger@uni-jena.de
 */

public class SoilWaterStore extends Store implements ProcessObserver{

    /* to do
    add history fun for store?
     */
    private  double inputwater;

    public double b;

    public SoilWaterStore(double inputWater, double oldStore, double inB){
        super(inputWater,oldStore);
        this.inputwater = inputWater;
        this.b = inB;
        this.runnOff = this.waterStore * this.b;
    }

    public double getRunnOff(){
        return this.runnOff;
    }

    @Override
    public void update(double waterStore){  // update on Evapo and infil
        /**
         * set new Waterstore and recalc. runOff
         */
        this.waterStore = waterStore;
        this.runnOff = this.waterStore * this.b;

    }

    @Override
    public String toString() {
        return "SoilWaterStore{" +
                "inWater=" + inWater +
                ", outWater=" + outWater +
                ", waterStore=" + waterStore +
                ", runOff=" + runnOff +
                '}';
    }

    @Override
    void setOutWater() {
    }
}
