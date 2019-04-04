package ekrueger;

import ekrueger.Model.ABCv2;
import ekrueger.Model.Calibrator;

import java.util.Map;

/**
 *  main tester
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        DataReader data = new DataReader("./data/klima_schmuecke.txt");
        DataReader calibData = new DataReader("./data/abfluss_gehlberg.txt");
        ABCv2 calibModel = new ABCv2(0,data.getEnvData(), false, false,
                0.9,0.4,0.6);
        Calibrator calibrator = new Calibrator(calibModel,calibData, 1000);


        ABCv2 myModel = new ABCv2(0, data.getEnvData(),true,true,
                calibrator.getA(), calibrator.getB(), calibrator.getC());

        myModel.excute();



    }
}
