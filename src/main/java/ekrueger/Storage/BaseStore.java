package ekrueger.Storage;

public class BaseStore extends Store implements ProcessObserver {

    public double c;

    public BaseStore(double inputWater, double oldStore, double inC){
        super(inputWater,oldStore);
        this.c = inC;

    }

    public double getRunnOff(){
        return this.runnOff;
    }


    @Override
    public void update(double waterStore) {
        this.waterStore = waterStore;
        this.runnOff = this.waterStore * this.c;


    }


    @Override
    void setOutWater() {

    }
}
