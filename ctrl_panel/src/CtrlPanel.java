import tests.mnist.DimReduceRandom;

import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by miul on 4/23/2015.
 */
public class CtrlPanel {
    // config stuff
    private static String dataSetPath = "D:/ITU/algorithms_seminar/algorithms_seminar_2015/matlab_stuff/mnist_to_matrices.txt";

    // tests stuff
    private Dictionary<String, ArrayList> testResults;

    // local stuff


    /***************************/
    public static void main(String[] param){
        // run DimReduce with JohnsonLindenstrauss with matrix file
        DimReduceRandom.setMaxRecords(3000); // optional (default: number of matrices in file)
//        DimReduceRandom.setMergeRecordsIntoPoints(3); // optional (default: number of matrices in file)
//        DimReduceRandom.setJLprecision(0.01); // optional (default: 0.1)
        DimReduceRandom drr = new DimReduceRandom(dataSetPath);
    }

    private void writeResultsToFile(){

    }

    private void plotResults(){

    }
}
