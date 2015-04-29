package raw.images;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by miul on 4/21/2015.
 */

public class ReadMatricesFile {
    private int readRecordsLimit = Integer.MAX_VALUE;
    private int mergeIntoPoints;
    /**
     * needs to be divider of readRecordsLimit if that is set
     */
    private String filePath;
    private int[] matrixDim = {0, 0}; // [rows, cols]

    public ReadMatricesFile(String fp, int limit, int pointsLimit) {
        this.readRecordsLimit = limit;
        this.mergeIntoPoints = pointsLimit;
        this.filePath = fp;
    }

    public ReadMatricesFile(String fp, int limit) {
        this.readRecordsLimit = limit;
        this.filePath = fp;
    }

    public ReadMatricesFile(String fp) {
        this.filePath = fp;
    }

    public ArrayList readAsVector() {
        int recordsCnt = 0; /** for some reason, the first line is always empty ??? */
        ArrayList<ArrayList<Double>> vectors = new ArrayList<>();
        int linePartsLength = 1;

        // make sure the index exists
        vectors.add(new ArrayList<>());

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine().trim()) != null && recordsCnt < this.readRecordsLimit) {
                // empty lines are separators between records (image matrices)
                if (!line.isEmpty()) {
                    // move all individual values on this line to the vector
                    String[] parts = line.split(" ");
                    ArrayList vector = vectors.get(recordsCnt);
                    // set only the cols as number of parts in line; rows can be calculated later
//                    this.matrixDim[1] = parts.length;
//                    linePartsLength = parts.length;
                    for (int i = 0; i < parts.length; i++) {
                        vector.add(Double.parseDouble(parts[i]));
                    }

                } else {
                    // if empty line reached, initialize a new vector
                    if (vectors.get(recordsCnt).size() > 0) { /** first line in br is always empty */
                        if (recordsCnt + 1 != this.readRecordsLimit) {
                            vectors.add(new ArrayList<>());
                        }
                        recordsCnt += 1;
                    }
                }
            }
//            this.matrixDim[0] = vectors.get(0).size() / this.matrixDim[1];
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        /** need at least 3 points for dim_reduce to function properly */
        /**
         * NB: the last index of this.vectors is empty!
         * this might cause one of the merged vectors to be shorter than the others
         */
        if (this.mergeIntoPoints > 2) {
            ArrayList mergedVectors = new ArrayList<>(this.mergeIntoPoints);
            // all vectors should be equal in size, so make sure mergeIntoPoints is a divider of vectors.size()
            // discard all remaining records
            int mvSize = (int) Math.floor(vectors.size() / this.mergeIntoPoints);

            for (int i = 0; i < this.mergeIntoPoints; i++) {
                ArrayList mv = new ArrayList(mvSize);
                for (int j = 0; j < mvSize; j++) {
                    mv.addAll(vectors.get(j));
                }
                mergedVectors.add(i, mv);
            }
            this.matrixDim[0] = this.mergeIntoPoints;//  mergedVectors.size() / matrixDim[1];
            this.matrixDim[1] = vectors.get(0).size() * mvSize;
            return mergedVectors;
        }

        this.matrixDim[0] = vectors.size(); // vectors.get(0).size() / matrixDim[1];
        this.matrixDim[1] = vectors.get(0).size();
        return vectors;
    }

    public boolean readAsMatrix() {

        return false;
    }

    public int[] getMatrixDim() {
        return this.matrixDim;
    }

    public void setMergeIntoPoints(int rpp) {
        this.mergeIntoPoints = rpp;
    }

}
