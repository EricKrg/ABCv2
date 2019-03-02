package ekrueger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    Object loggingFocus;
    Logger history;
    BufferedWriter writer = null;


    public  Logger(Object logFocus){

        this.loggingFocus = logFocus;
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
}
