import java.io.*;
import java.util.*;

public class YamanoteLineDistance {
    private static String CONFIG_TIME_PATH = "C:/Users/User/Desktop/yamanote/conf/東京駅からの所要時間.txt";
    private static String TARGET_STATION_PATH = "C:/Users/User/Desktop/yamanote/input/山手線駅間所要時間_入力.txt";
    private static String REQUIRE_TIME_OUTPUT = "C:/Users/User/Desktop/yamanote/output/東京駅からの所要時間_出力.txt";
    private static HashMap<String, List<Integer>> fixedTimeMap = new HashMap<String, List<Integer>>();

    public static void main(String args[]) {
        readConfigFile(CONFIG_TIME_PATH);
        readTargetStation(TARGET_STATION_PATH);
    }

    private static void readConfigFile(String configTimePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(configTimePath)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String config[] = line.split(",");
                if (fixedTimeMap.containsKey(config[0])) {
                    ArrayList<Integer> list;
                    list = new ArrayList<>(fixedTimeMap.get(config[0]));
                    fixedTimeMap.put(config[0], list);
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    arrayList.add(Integer.parseInt(config[1]));
                    fixedTimeMap.put(config[0], arrayList);
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
            FileWriter fileWriter = new FileWriter(new File(REQUIRE_TIME_OUTPUT));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(targetStationPath)));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(" ");

                String targetStation[] = line.split(" ", -1);
                int result = searchMinTime(targetStation[0], targetStation[1]);

                stringBuilder.append(result).append(System.lineSeparator());
                fileWriter.write(stringBuilder.toString());
            }
            System.out.println(stringBuilder.toString());
            bufferedReader.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static int searchMinTime(String targetSt, String destinationSt) {
        int minDistance = Integer.MAX_VALUE;
        int minTarget = 0;
        int minDestination = 0;

        List<Integer> targetValue = fixedTimeMap.get(targetSt);
        List<Integer> destinationValue = fixedTimeMap.get(destinationSt);
        for (int val : targetValue) {
            minTarget = val;
            if (minTarget > val) {
                minTarget = val;
            }
        }
        for (int val : destinationValue) {
            minDestination = val;
            if (minDestination > val) {
                minDestination = val;
            }
        }
        int result = Math.abs(minDestination - minTarget);

        return result;
    }
}
