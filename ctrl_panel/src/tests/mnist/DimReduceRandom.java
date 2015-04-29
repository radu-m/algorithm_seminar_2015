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

    public DimReduceRandom(String dataFilePath) {
        rmfPath = dataFilePath;
        // get the data
        readMnistFromMatricesFile();
        // calculate minDim for dataSet
        JohnsonLindenstrauss jl = new JohnsonLindenstrauss(vectors.size());
        System.out.println("JL for " + vectors.size() + " points and err +- " + jl.getErrorMargin() + " : " + jl.minDim());

        projMatrix = RndProjMatrix.getProjectionMatrix(jl.minDim(), originalMatrixDim[1]);
        System.out.println("projMatrix rows " + projMatrix.getColumnDimension());
        System.out.println("projMatrix cols " + projMatrix.getRowDimension());

        Matrix vMatrix = new Matrix(arrayListToDouble2D(vectors), originalMatrixDim[0], originalMatrixDim[1]);
        System.out.println("vMatrix rows " + vMatrix.getColumnDimension());
        System.out.println("vMatrix cols " + vMatrix.getRowDimension());

        Matrix reducedMatrix = projMatrix.arrayTimes(vMatrix);
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

    public void readMnistFromMatricesFile() {
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
        this.originalMatrixDim = rmf.getMatrixDim();

        System.out.println("originalMatrixDim " + originalMatrixDim[0] + ", " + originalMatrixDim[1]);
        System.out.println("total of points = " + vectors.size());
        System.out.println("point dimensions = " + vectors.get(0).size());
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
