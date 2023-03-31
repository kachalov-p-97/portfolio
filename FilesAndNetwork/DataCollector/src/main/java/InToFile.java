package src.main.java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class InToFile {

    protected static Map<String,String> pathsToJsonFiles = new HashMap<>();


    protected static void inToFile () throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String allStationsToJson =
                Files.readString(
                        Paths.get(pathsToJsonFiles.get("allStationsToJson"))
                );
        String allJsonToJson =
                Files.readString(
                        Paths.get(pathsToJsonFiles.get("allJsonToJson"))
                );
        String allCsvToJson =  Files.readString(
                Paths.get(pathsToJsonFiles.get("allCsvToJson"))
        );

        JsonNode allStationJson = objectMapper.readTree(allStationsToJson);
        JsonNode allJsonJson = objectMapper.readTree(allJsonToJson);
        JsonNode allCsvJson = objectMapper.readTree(allCsvToJson);

        allStationJson.forEach(x -> x.forEach(y -> {
            ObjectNode objectNode =(ObjectNode) y;
            String nameStation = y.get("name").asText();

            allJsonJson.forEach(j ->{

                if (j.get("station_name").asText().equals(nameStation)){
                    objectNode.put("depth",j.get("depth"));
                }
            });

            allCsvJson.forEach(j -> {
                if (j.get("station_name").asText().equals(nameStation)){
                    objectNode.put("date",j.get("date"));
                }
            });
        }));
        ObjectMapper mapper = new ObjectMapper();
        File output =new File("allJson\\finalStation.json");
        mapper.writeValue(output,allStationJson);
    }
    public void setPathsToJsonFiles(Map<String, String> pathsToJsonFiles) {
        InToFile.pathsToJsonFiles = pathsToJsonFiles;
    }
}
