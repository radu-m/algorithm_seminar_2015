package raw.images;

import java.util.Vector;

/**
 * Created by miul on 4/19/2015.
 */
public class Image {
    private Double[] vector = null;

    public Image(Double[] points){
        this.vector = points;
    }

    public Double[] getVector(){
        return this.vector;
    }


}
