package ekrueger.Storage;

public class DepStore extends Store {
    /*
    simple depository store
     */
    public DepStore(double inputWater, double oldStore){
        super(inputWater,oldStore);
        this.setOutWater();
    }


    @Override
    public void setOutWater(){
        this.outWater = this.inWater * 0.5; // 50% recharges to soil 50% stays
        this.waterStore = this.waterStore - this.outWater;
    }
}
