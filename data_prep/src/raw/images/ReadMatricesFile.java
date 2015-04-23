package raw.images;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by miul on 4/21/2015.
 */

public class ReadMatricesFile {
    private int readRecordsLimit = Integer.MAX_VALUE;
    private String filePath;

    public ReadMatricesFile(String fp, int limit){
        this.readRecordsLimit = limit;
        this.filePath = fp;
    }

    public ReadMatricesFile(String fp){
        this.filePath = fp;
    }

    public ArrayList readAsVector(){
        int recordsCnt = 0; /** for some reason, the first line is always empty ??? */
        ArrayList<ArrayList<Double>> vectors = new ArrayList<>();

        vectors.add(new ArrayList<>());

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine().trim()) != null && recordsCnt < this.readRecordsLimit) {
//                System.out.println(recordsCnt);
//                System.out.println(line);
                if(!line.isEmpty()){
                    String[] parts = line.split(" ");
//                    ArrayList<Double> points = new ArrayList<>();
                    ArrayList vector = vectors.get(recordsCnt);
                    for(int i = 0; i < parts.length; i++){
//                        points.add(Double.parseDouble(parts[i]));
                        vector.add(Double.parseDouble(parts[i]));
                    }
//                    vectors.get(recordsCnt).addAll(points);
                }else{
//                    System.out.println(vectors.get(recordsCnt).size());

                    if(vectors.get(recordsCnt).size() > 0) {
                        if( recordsCnt + 1 != this.readRecordsLimit) {
                            vectors.add(new ArrayList<>());
                        }
                        recordsCnt += 1;
                    }
//                    vectors.set(recordsCnt, new ArrayList<>());
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return vectors;
    }

    public boolean readAsMatrix(){

        return false;
    }

}
