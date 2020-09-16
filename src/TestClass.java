import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class TestClass {
    public static void main(String args[]) throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        Map<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
        int temp=0;
        while (temp==0){
            String key= bufferReader.readLine();
            String value=bufferReader.readLine();
            if(map.containsKey(key)){
                ArrayList<String> list;
                list=map.get(key);
                list.add(value);
                map.put(key,list);
            }else {
                ArrayList<String> secondList= new ArrayList<String>();
                secondList.add(value);
                map.put(key,secondList);
                secondList=null;
            }
            System.out.println("Do you want to continue?If yes,enter 0 or else 1");
            temp =Integer.parseInt(bufferReader.readLine());
        }
        System.out.println(map);
    }
}
