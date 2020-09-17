import java.io.*;
import java.util.*;

public class YamanoteLineDistance {
    private static String STATION_TABLE = "C:/Users/User/Desktop/yamanote/conf/東京駅からの所要時間.txt";
    private static String TARGET_ROUTE = "C:/Users/User/Desktop/yamanote/input/山手線駅間所要時間_入力.txt";
    private static String DISPLAY_ROUTE = "C:/Users/User/Desktop/yamanote/output/東京駅からの所要時間_出力.txt";
    private static ArrayList<YamanoteRoute> yamanoteList = new ArrayList<YamanoteRoute>();

    public static void main(String args[]) {
        readYamanoteRoute(STATION_TABLE);
        readTargetStation(TARGET_ROUTE);
    }

    private static void readYamanoteRoute(String yamanoteRoute) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(yamanoteRoute)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String distance[] = line.split(",");
                yamanoteList.add(new YamanoteRoute(distance[0], distance[1]));
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
                int result = searchShortestRout(targetStation[0], targetStation[1]);
                stringBuilder.append(result).append(System.lineSeparator());
                fileWriter.write(stringBuilder.toString());
                System.out.println(stringBuilder.toString());
            }
            bufferedReader.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static int searchShortestRout(String targetSt, String destinationSt) {
        int shortestRoute = 0;
        int minInnerRoute = Integer.MAX_VALUE;
        int minOuterRoute = Integer.MAX_VALUE;
        ArrayList<Integer> outerRouteValue = new ArrayList<>();
        ArrayList<Integer> innerRouteValue = new ArrayList<>();
        ArrayList<Integer> startIndex = new ArrayList<>();
        ArrayList<Integer> endIndex = new ArrayList<>();
        for (int i = 0; i < yamanoteList.size(); i++) {
            if (targetSt.equals(yamanoteList.get(i).station)) {
                startIndex.add(i);
            }
            if (destinationSt.equals(yamanoteList.get(i).station)) {
                endIndex.add(i);
            }
        }
        for (int start : startIndex) {
            for (int end : endIndex) {
                int innerValue = 0;
                outerRouteValue.add(Math.abs(Integer.parseInt(yamanoteList.get(end).time) - Integer.parseInt(yamanoteList.get(start).time)));
                innerValue = Math.abs(Integer.parseInt(yamanoteList.get(start).time) - Integer.parseInt(yamanoteList.get(0).time));
                innerValue += Math.abs(Integer.parseInt(yamanoteList.get(end).time) - Integer.parseInt(yamanoteList.get(yamanoteList.size() - 1).time));
                innerRouteValue.add(innerValue);
            }
        }
        for (int inner : innerRouteValue) {
            if (minInnerRoute > inner) {
                minInnerRoute = inner;
            }
        }
        for (int outer : outerRouteValue) {
            if (minOuterRoute > outer) {
                minOuterRoute = outer;
            }
        }
        if (minInnerRoute > minOuterRoute) {
            shortestRoute = minOuterRoute;
        } else {
            shortestRoute = minInnerRoute;
        }
        return shortestRoute;
    }
}

class YamanoteRoute {
    public String station;
    public String time;

    public YamanoteRoute(String station, String time) {
        this.station = station;
        this.time = time;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
