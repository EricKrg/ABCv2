package ekrueger.Model;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/*
the data from the data reader is put in this class
 */

public class EnvCon {
    public double precip;
    public double minTemp;
    public double meanTemp;
    public double maxTemp;
    public double sunHours;
    public double relHum;
    public double absHum;
    public static final Map<Integer, Float>  haudePineMap = new HashMap<Integer,Float>() {{
        put(0,0.08f);
        put(1,0.04f);
        put(2,0.14f);
        put(3,0.35f);
        put(4,0.39f);
        put(5,0.34f);
        put(6,0.31f);
        put(7,0.25f);
        put(8,0.2f);
        put(9,0.13f);
        put(10,0.07f);
        put(11,0.05f);
    }};
    public double haudeFactor;


    public static Date parseDate(String date){
        try{
            return new SimpleDateFormat("dd.MM.yyyy").parse(date);

        } catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }



    public EnvCon(double inPrecip, double inMinTemp, double inMeanTemp, double inMaxTemp,
                  double inSunHours, double inRelHum, double inAbsHum, String date){
        this.setPrecip(inPrecip);
        this.setMeanTemp(inMeanTemp);
        this.setMinTemp(inMinTemp);
        this.setMaxTemp(inMaxTemp);
        this.setSunHours(inSunHours);
        this.setRelHum(inRelHum);
        this.setAbsHum(inAbsHum);
        this.setHaudeFactor(parseDate(date));
    }

    private double getHaudePineFactor(int month){
        return haudePineMap.get(month);
    }

    private void setHaudeFactor(Date date){
        this.haudeFactor = this.getHaudePineFactor(date.getMonth());
    }
    public double getHaude(){
        return this.haudeFactor;
    }

    // generated getter setter

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMeanTemp() {
        return meanTemp;
    }

    public void setMeanTemp(double meanTemp) {
        this.meanTemp = meanTemp;
    }

    public double getSunHours() {
        return sunHours;
    }

    public void setSunHours(double sunHours) {
        this.sunHours = sunHours;
    }

    public double getRelHum() {
        return relHum;
    }

    public void setRelHum(double relHum) {
        this.relHum = relHum;
    }

    public double getAbsHum() {
        return absHum;
    }

    public void setAbsHum(double absHum) {
        this.absHum = absHum;
    }
}
