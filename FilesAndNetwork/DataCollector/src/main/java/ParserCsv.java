package src.main.java;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserCsv {
    protected static List<Map<String,String>> allCsv = new ArrayList<>();

    public static void parse (String path) throws IOException {
        List<String> temporaryList = Files.readAllLines(Paths.get(path));

        temporaryList.forEach(t -> {
            Map<String,String> temporaryMap = new HashMap<>();
            if (!t.split(",")[0].equals("name")){
                temporaryMap.put("station_name",t.split(",")[0]);
                temporaryMap.put("date",t.split(",")[1]);
                allCsv.add(temporaryMap);
            }
        });
    }

    public static List<Map<String, String>> getAllCsv() {
        return allCsv;
    }
}



