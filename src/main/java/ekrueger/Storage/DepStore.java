package ekrueger.Storage;

public class DepStore extends Store {
    /*
    simple depository store
     */
    double a;
    public DepStore(double inputWater, double oldStore, double inA){
        super(inputWater,oldStore);
        this.a = inA;
        this.setOutWater();
    }


    @Override
    public void setOutWater(){
        this.outWater = this.inWater * this.a; //  recharges to soil
        this.waterStore = this.waterStore - this.outWater;
    }
}
