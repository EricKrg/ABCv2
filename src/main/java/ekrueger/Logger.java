package ekrueger;

/**
 * @author eric.krueger@uni-jena.de
 *
 */

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
    public BufferedWriter writer = null;

    // generic logger
    /**
     * the generic log takes an obj. prints its class name and the toString method, was used for debbuging
     */
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
    public void toLogFile(boolean replaceOld){
        try{
            DateFormat df = new SimpleDateFormat("HH:mm:ss_dd.MM.yyyy");
            Date date = new Date();
            String suffix =  df.format(date);

            FileWriter fw;
            if(replaceOld){
                fw = new FileWriter("./logs/newLog.txt");
            } else {
                fw = new FileWriter("./logs/"+ this.loggingFocus.getClass().getSimpleName() + "_"+ suffix + ".txt");
            }
            this.writer = new BufferedWriter(fw);  // with replaceOld == true it is easier to visualize the log with the python script
            // header
            String header = "DATE" + "\t" + "PERCIP" +"\t"+ "SIM_runoff" +"\t" + "REAL_runoff" +"\t" + "BASESTORE " +"\t" + "POTEVO" +"\t";
            this.writer.write(header);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void writeLogFile(Object someObjLog) {
        try {
            this.writer.newLine();
            this.writer.write(someObjLog.toString());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    // specific logger
    public void logABCv2(ABCv2 inLog, RunOff runOff, Evaporation evapo, int index, Boolean ... textout){
        DecimalFormat df = new DecimalFormat("###.##");
        String sim = df.format(runOff.runOff);
        String percip = df.format(inLog.envData.get(index).precip);
        String base = df.format(runOff.baseStore.waterStore);
        String epot = df.format(evapo.getPotEvapo());
        // date
        DateFormat dateFormat= new SimpleDateFormat("dd.MM.yyyy");
        String time = dateFormat.format(inLog.envData.get(index).time);
        String line = null;
        try {
            double real = inLog.calibData.calibList.get(index);
            line = time + "\t" + percip + "\t"+ sim  + "\t" + real + "\t" + base + "\t" + epot;
        } catch (Exception e){ // specify
            String real = "no data";
            line = time + "\t" + percip + "\t"+ sim  + "\t" + real + "\t" + base + "\t" + epot;
        } finally {
            if(textout.length > 0 && textout[1]) {
                System.out.println(line);
            }
            if(textout.length > 0 && textout[0]){
                this.writeLogFile(line);
            }
        }
    }
    // helper functions, which dont function really good
    public static void clearScreen() {
        System.out.print("\033[H\033[2J"); // this wont work because of the cross-plattform shenanigans
        System.out.flush();
    }

    public static void progressPercentage(int remain, int total,String name) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append(name +"[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(remainProcent, bare.length());
        System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
        if (remain == total) {
            System.out.print("\n");
        }
    }

}
