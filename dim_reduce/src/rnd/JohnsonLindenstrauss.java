package rnd;

import Jama.Matrix;

/**
 * Created by miul on 4/23/2015.
 */
public class JohnsonLindenstrauss {
    private static int points = 3;
    private static double errorMargin = 0.1;

    public JohnsonLindenstrauss(int numberOfPoints, double precision){
        points = numberOfPoints;
        errorMargin = precision;
    }

    public JohnsonLindenstrauss(int numberOfPoints){
        points = numberOfPoints;
    }

    public static int minDim(){
        return (int) Math.ceil((4 * Math.log(points)) / (Math.pow(errorMargin, 2.0) / 2 - Math.pow(errorMargin, 3.0) / 3));
    }

    public static void setErrorMargin(double e){
        errorMargin = e;
    }

    public double getErrorMargin(){
        return errorMargin;
    }
}

/**

 using Distributions

 function projection(
 X::Matrix,
 ε::Real,
 k::Integer = mindim(size(X, 2), ε)
 )
 d, n = size(X)
 A = rand(Normal(0, 1 / sqrt(k)), k, d)
 return A, k, A * X
 end

 */