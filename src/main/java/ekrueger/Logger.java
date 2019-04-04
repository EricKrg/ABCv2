package ekrueger;

import ekrueger.Model.ABCv2;
import ekrueger.Process.Evaporation;
import ekrueger.Process.RunOff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    Object loggingFocus;
    Logger history;
    BufferedWriter writer = null;

    // generic logger
    public  Logger(Object logFocus){
        this.loggingFocus = logFocus;
        this.clearScreen();
    }
    public void log(Object someObjLog){
        //System.out.println(someObjLog);
        System.out.println(someObjLog.getClass().getName());
        System.out.println(someObjLog);
        System.out.println("#-----------------------------------------------");
    }
    public void showHisto(Object histoObj){
        System.out.println("Show histo of:  " + histoObj.getClass().getCanonicalName() );
        //System.out.println(this.history.loggingFocus.histoObj.getClass().getCanonicalName());
    }
    public void save(){
        this.history =this;
    }
    public void toLogFile(){
        try{
            DateFormat df = new SimpleDateFormat("HH:mm:ss_dd.MM.yyyy");
            Date date = new Date();
            String suffix =  df.format(date);
            FileWriter fw = new FileWriter("./logs/"+ this.loggingFocus.getClass().getSimpleName() + "_"+ suffix + ".txt");
            this.writer = new BufferedWriter(fw);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void writeLogFile(Object someObjLog){
        try {
            this.writer.newLine();
            this.writer.write(someObjLog.toString());
        } catch(IOException e){
            e.printStackTrace();
        }

    }
    // specific logger
    public void logABCv2(ABCv2 inLog, RunOff runOff, Evaporation evapo, int index){
        DecimalFormat df = new DecimalFormat("###.##");
        String sim = df.format(runOff.runOff);
        String percip = df.format(inLog.envData.get(index).precip);
        String base = df.format(runOff.baseStore.waterStore);
        Date time = inLog.envData.get(index).time;
        String epot = df.format(evapo.getPotEvapo());

        try {
            double real = inLog.calibData.calibList.get(index);
            System.out.println(time + "\t" + percip + "\t"+ sim  + "\t" + real + "\t" + base + "\t" + epot);
        } catch (Exception e){ // specify
            String real = "no data";
            System.out.println(time + "\t" + percip + "\t"+ sim  + "\t" + real + "\t" + base + "\t" + epot);
        }


    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
