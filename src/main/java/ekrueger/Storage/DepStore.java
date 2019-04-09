package ekrueger.Storage;

/**
 * @author eric.krueger@uni-jena.de
 */

public class DepStore extends Store implements ProcessObserver {
    /**
     * simple depression store class
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
        this.outWater = this.runnOff; //  recharges to soil
        this.waterStore = this.waterStore - this.outWater;

    }

    @Override
    public void update(double waterStore) {
        /**
         * set new Waterstore and recalc. runOff
         */
        this.setWaterStore(waterStore);
        this.inWater = this.waterStore;
        this.runnOff = this.waterStore * this.a;
    }
}
