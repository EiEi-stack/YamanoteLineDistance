import java.io.*;
import java.util.*;

public class YamanoteLineDistance {
    private static String DISTANCE_LIST = "C:/Users/User/Desktop/yamanote/conf/東京駅からの所要時間.txt";
    private static String TARGET_ROUTE = "C:/Users/User/Desktop/yamanote/input/山手線駅間所要時間_入力.txt";
    private static String DISPLAY_ROUTE = "C:/Users/User/Desktop/yamanote/output/東京駅からの所要時間_出力.txt";
    private static HashMap<String, List<Integer>> distanceMap = new HashMap<String, List<Integer>>();

    public static void main(String args[]) {
        readConfigFile(DISTANCE_LIST);
        readTargetStation(TARGET_ROUTE);
    }

    private static void readConfigFile(String distanceList) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(distanceList)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String distance[] = line.split(",");
                //東京駅からの所要時間をMapに入れる
                if (distanceMap.containsKey(distance[0])) {
                    ArrayList<Integer> list;
                    list = new ArrayList<>(distanceMap.get(distance[0]));
                    list.add(Integer.parseInt(distance[1]));
                    distanceMap.put(distance[0], list);
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    arrayList.add(Integer.parseInt(distance[1]));
                    distanceMap.put(distance[0], arrayList);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void readTargetStation(String targetStationPath) {
        try {
            FileWriter fileWriter = new FileWriter(new File(DISPLAY_ROUTE));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(targetStationPath)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(line).append(" ");
                String targetStation[] = line.split(" ", -1);
                //最短ルートを検索する
                int result = searchMinTime(targetStation[0], targetStation[1]);
                stringBuilder.append(result).append(System.lineSeparator());
                fileWriter.write(stringBuilder.toString());
            }
            bufferedReader.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static int searchMinTime(String targetSt, String destinationSt) {
        int minRoute = Integer.MAX_VALUE;
        int minResult = 0;

        List<Integer> targetValue = distanceMap.get(targetSt);
        List<Integer> destinationValue = distanceMap.get(destinationSt);
        for (int start : targetValue) {
            for (int end : destinationValue) {
                minResult = Math.abs(end - start);
                if (minRoute > minResult) {
                    minRoute = minResult;
                }
            }
        }
        return minRoute;
    }
}
