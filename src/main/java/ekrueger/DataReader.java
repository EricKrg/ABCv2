package ekrueger;

import ekrueger.Model.EnvCon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
main purpose of this dataReader is to create a list of Environment data from a given text file
this list is used as input for the climate model.
 */

public class DataReader {
    private String filePath;
    private File data;
    public List<EnvCon> envList = new ArrayList<EnvCon>();
    public ArrayList<Double> calibList = new ArrayList<>();


    public DataReader(String inPath){
        data = new File(inPath);
        try {
            BufferedReader dataReader = new BufferedReader(new FileReader(inPath));
            this.read(dataReader);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void read(BufferedReader dataReader){
        String line;
        try {
            boolean go = false;
            while ((line = dataReader.readLine()) != null) {
                if(line.contains("#start") | line.contains("#end")){ // end start conditions
                    go = go ? false : true; // trenary switch
                    continue;
                }
                if(go){
                    //System.out.println(line);
                    String[] lineElements = line.split("\t"); // split by seperator
                    if(lineElements.length > 2) {
                        EnvCon env = new EnvCon(  // create single environment
                                Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2]),
                                Double.parseDouble(lineElements[3]), Double.parseDouble(lineElements[4]),
                                Double.parseDouble(lineElements[5]), Double.parseDouble(lineElements[6]),
                                Double.parseDouble(lineElements[7]), lineElements[0]);
                        this.envList.add(env); // added to list
                    } else {
                        this.calibList.add(Double.parseDouble(lineElements[1]));
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public List<EnvCon> getEnvData(){
        return this.envList;
    }
    public ArrayList<Double> getCalibData(){
        return this.calibList;
    }

}
