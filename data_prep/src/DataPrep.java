import raw.images.ReadMatricesFile;
import raw.images.ReadMnist;

import java.util.ArrayList;

/**
 * Created by miul on 4/16/2015.
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

    protected ArrayList vectors;

    public void main(final String[] args){
//        ReadMnist mnist = new ReadMnist();
        ReadMatricesFile rmf = new ReadMatricesFile(rmfPath, 4);
        this.vectors = rmf.readAsVector();

        for(int i = 0; i < this.vectors.size(); i++) {
            System.out.println(this.vectors.get(i));
        }
    }
    public ArrayList getVectors(){
        return this.vectors;
    }
}
