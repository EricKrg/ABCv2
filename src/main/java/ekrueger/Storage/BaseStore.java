package ekrueger.Storage;

public class BaseStore extends Store implements ProcessObserver {

    @Override
    public void update(double waterStore) {
        this.setWaterStore(waterStore);
        this.inWater = this.waterStore;

    }

    public BaseStore(double inputWater, double oldStore){
        super(inputWater,oldStore);
    }

    @Override
    public void setInWater(double inWater) {
        super.setInWater(inWater);
    }

    @Override
    public void setWaterStore(double waterStore) {
        super.setWaterStore(waterStore);
    }

    @Override
    void setOutWater() {

    }
}
