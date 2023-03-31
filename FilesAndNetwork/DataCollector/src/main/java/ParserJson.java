package src.main.java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ParserJson {
    protected static List<Map<String,String>> allJson = new ArrayList<>();

    protected static void parse(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFile =
                Files.readString(
                        Paths.get(path)
                );
        JsonNode jsonData = objectMapper.readTree(jsonFile);
        jsonData.forEach(x -> {
            Map<String,String> temporaryMap = new HashMap<>();
            temporaryMap.put("station_name",x.get("station_name").toString().replaceAll("\"",""));
            temporaryMap.put("depth", x.get("depth").toString().replaceAll("\"",""));
            allJson.add(temporaryMap);

        });
    }

    public static List<Map<String, String>> getAllJson() {
        return allJson;
    }
}
