import raw.images.ReadMatricesFile;
import raw.images.ReadMnist;

import java.util.ArrayList;

/**
 * Created by miul on 4/16/2015.
 */

/**
 *
 *                              NOT USED
 *
 * I duplicated this functionality in ctr_panel tests.mnist.DimReduceRandom for integration.
 * This is here only for providing a main() if you want to run this module stand-alone.
 *
 */


/**
 * README
 *
 * 1. install MATLAB R2014b (other versions could work but will probably give you some issues)
 * 2. find your installation dir and change the paths in ReadMnist.java
 * 3. don't forget to add the .m files to your Matlab path (right-click on the file tab)
 * 4. run this module and then tell me what's wrong, 'cause something totally will and I can't remember anything else to write here -_-
 * 5. rehearse all the foul constructs of your native language
 *
 */

public class DataPrep {
    private static String rmfPath = "D:/ITU/algorithms_seminar/algorithms_seminar_2015/matlab_stuff/mnist_to_matrices.txt";

    protected ArrayList<ArrayList> vectors;
    protected int[] originalMatrixDim;

    public DataPrep(){
        readMnistFromMatricesFile();
    }

    public void readMnistFromMatricesFile(){
        ReadMatricesFile rmf = new ReadMatricesFile(this.rmfPath);
        rmf.setMergeIntoPoints(3);
        this.vectors = rmf.readAsVector();
        this.originalMatrixDim = rmf.getMatrixDim();

        System.out.println("total of points" + vectors.size());
        System.out.println("point dimensions" + vectors.get(0).size());
    }

    public void readMnistFromMatlab(){
        ReadMnist mnist = new ReadMnist();
    }

    public static void setRmfPath(String path){
        rmfPath = path;
    }

    public ArrayList getVectors(){
        return this.vectors;
    }
}
