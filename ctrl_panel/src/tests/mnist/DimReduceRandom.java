package tests.mnist;

import Jama.Matrix;
import raw.images.ReadMatricesFile;
import raw.images.ReadMnist;
import rnd.JohnsonLindenstrauss;
import util.RndProjMatrix;

import java.util.ArrayList;

/**
 * Created by miul on 4/23/2015.
 */
public class DimReduceRandom {
    private static String rmfPath;
    // how many images should it read in total
    private static int maxRecords = 0; // 0 == disabled
    // merge images to get at most this many data-points
    private static int mergeRecordsIntoPoints = 0; // (0 == disabled)

    protected ArrayList<ArrayList<Double>> vectors;
    protected int[] originalMatrixDim; // [0: number of vectors, 1: length of vector]
    protected Matrix projMatrix;

    ///

    public DimReduceRandom(String dataFilePath) {
        rmfPath = dataFilePath;

        // get the data
        this.originalMatrixDim = readMnistFromMatricesFile();
        System.out.println("originalMatrixDim " + this.originalMatrixDim[0] + ", " + this.originalMatrixDim[1]);

        // calculate minDim for dataSet
        JohnsonLindenstrauss jl = new JohnsonLindenstrauss(vectors.size());
        System.out.println("JL for " + vectors.size() + " points and err +- " + jl.getErrorMargin() + " : " + jl.minDim());

        //////
        projMatrix = RndProjMatrix.getProjectionMatrix(jl.minDim(), originalMatrixDim[0]); // A = rand(Normal(0, 1 / sqrt(k)), k, d)
        System.out.println("projMatrix rows " + projMatrix.getColumnDimension());
        System.out.println("projMatrix cols " + projMatrix.getRowDimension());
//        System.out.println("projMatrix rank: " + projMatrix.rank());

        //////
        Matrix vMatrix = new Matrix(arrayListToDouble2D(vectors), originalMatrixDim[0], originalMatrixDim[1]);
        System.out.println("vMatrix rows " + vMatrix.getColumnDimension());
        System.out.println("vMatrix cols " + vMatrix.getRowDimension());
//        System.out.println("vMatrix rank: " + vMatrix.rank());

        //////
//        Matrix vMatrixT = vMatrix.transpose();
//        System.out.println("vMatrixT rows " + vMatrixT.getColumnDimension());
//        System.out.println("vMatrixT cols " + vMatrixT.getRowDimension());
//        System.out.println("vMatrix rank: " + vMatrix.rank());

        ////
        Matrix reducedMatrix = projMatrix.times(vMatrix);
        System.out.println("reducedMatrix rows " + reducedMatrix.getColumnDimension());
        System.out.println("reducedMatrix cols " + reducedMatrix.getRowDimension());
    }

    private static double[][] arrayListToDouble2D(ArrayList<ArrayList<Double>> vectors){
        double[][] dd = new double[vectors.size()][];
        for (int i = 0; i < vectors.size(); i++) {
            dd[i] = vectors.get(i).stream().mapToDouble(d -> d).toArray();
        }
        return dd;
    }

    public int[] readMnistFromMatricesFile() {
        ReadMatricesFile rmf;
        if (maxRecords > 0) {
            rmf = new ReadMatricesFile(rmfPath, maxRecords);
        } else {
            rmf = new ReadMatricesFile(rmfPath);
        }

        if (mergeRecordsIntoPoints > 0) {
            rmf.setMergeIntoPoints(mergeRecordsIntoPoints);
        }
        this.vectors = rmf.readAsVector();
//        this.originalMatrixDim = rmf.getMatrixDim();

        System.out.println("total of points = " + vectors.size());
        System.out.println("point dimensions = " + vectors.get(0).size());

        return rmf.getMatrixDim();
    }

    public void readMnistFromMatlab() {
        ReadMnist mnist = new ReadMnist();
    }

    public static void setJLprecision(double e) {
        JohnsonLindenstrauss.setErrorMargin(e);
    }

    public static void setMaxRecords(int r) {
        maxRecords = r;
    }

    public static void setMergeRecordsIntoPoints(int p) {
        mergeRecordsIntoPoints = p;
    }

    public ArrayList getVectors() {
        return this.vectors;
    }


}
