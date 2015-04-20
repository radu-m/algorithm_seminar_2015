package raw.images;

import matlabcontrol.*;
import java.util.*;

/**
 * Created by miul on 4/15/2015.
 */
public class ReadMnist {
    /**
     * Don't forget to change these:
     */
    private String matlabLocation = "C:/Program Files/MATLAB/R2014b";
    private String trainingImgPath = "D:/ITU/algorithms_seminar/mnist/train-images.idx3-ubyte";
    private String trainingLabelsPath = "D:/ITU/algorithms_seminar/mnist/train-labels.idx1-ubyte";
    private Integer rowsToRead = 3000;
    private Integer rowsOffest = 0;

    public ReadMnist(){
        //Create a proxy, which we will use to control MATLAB
        MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder().setUsePreviouslyControlledSession(true).build();
        MatlabProxyFactory factory = new MatlabProxyFactory(options);

        try{
            MatlabProxy proxy = factory.getProxy();

            try {
                Object[] result = proxy.returningFeval("mnist_test", 1, this.trainingImgPath, this.trainingLabelsPath, this.rowsToRead, this.rowsOffest);
                Object[] innerRes = (Object[]) result[0];
//                System.out.println(Arrays.deepToString(result));
                System.out.println(Arrays.toString((double[]) innerRes[0]));
            }catch (Exception e){
                e.printStackTrace();
            }
            //Disconnect the proxy from MATLAB
            proxy.disconnect();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
