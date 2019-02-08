package ekrueger.Storage;

public abstract class Store {
    public double inWater;
    public double outWater;

    public double getInWater() {
        return inWater;
    }

    public void setInWater(double inWater) {
        this.inWater = inWater;
    }

    public double getOutWater() {
        return outWater;
    }

    public void setOutWater(double outWater) {
        this.outWater = outWater;
    }
}
