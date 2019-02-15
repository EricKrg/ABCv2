package ekrueger.Storage;

public class GroundWaterStore extends Store {

    public GroundWaterStore(double inputWater, double oldStore){
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
