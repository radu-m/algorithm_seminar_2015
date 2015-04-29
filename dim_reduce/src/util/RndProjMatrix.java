package util;

import Jama.Matrix;

/**
 * Created by miul on 4/23/2015.
 */
public class RndProjMatrix {

    public static Matrix getProjectionMatrix(int r, int c){
        Matrix m = Matrix.random(r, c);
        return m;
    }

}
