package ekrueger;

import ekrueger.Model.ABCv2;
import ekrueger.Model.Calibrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *  main
 * @author eric.krueger@uni-jena.de
 *
 */
public class App {
    /**
     * Main method, calls the data reader first to have an data input (of data to calibrate and simulate).
     * In the next step the model is calibrate, the calibrator class is called, all calibration takes place there
     * finally the calibrate a,b and c values are used to run the simulation
     * the ABCv2.properties file is used to set parameters of the calibration-model und simulation-model
     */


    public static void main( String[] args ) {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("src/main/ABCv2.properties");
            // load a properties file
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Data reader
        DataReader data = new DataReader(prop.getProperty("model.datapath"));
        DataReader calibData = new DataReader(prop.getProperty("calib.datapath"));
        // Calibration
        ABCv2 calibModel = new ABCv2(0,data.getEnvData(), Boolean.parseBoolean(prop.getProperty("calib.verbose")),
                Boolean.parseBoolean(prop.getProperty("calib.textout")), 0.9,0.4,0.6);
        Calibrator calibrator = new Calibrator(calibModel,calibData, Integer.parseInt(prop.getProperty("calib.iterations")));
        // Final Model
        ABCv2 myModel = new ABCv2(0, data.getEnvData(),Boolean.parseBoolean(prop.getProperty("model.verbose")),
                Boolean.parseBoolean(prop.getProperty("model.textout")),
                calibrator.getA(), calibrator.getB(), calibrator.getC(), calibData);
        myModel.calibFit = calibrator.model.calibFit;
        myModel.execute();
    }
}
