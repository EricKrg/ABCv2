package ekrueger;

import ekrueger.Model.ABCv2;

/**
 *  main tester
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        DataReader data = new DataReader("./data/klima_schmuecke.txt");

        ABCv2 myModel = new ABCv2(0, data.getEnvData(),true,true);

        myModel.excute();



    }
}
