package src.main.java;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String allStationsToJson = "allJson\\stations.json";
        String allJsonToJson = "allJson\\json.json";
        String allCsvToJson = "allJson\\csv.json";
        String map = "allJson\\map.json";
        String pathForFindFile = "data";


        Map<String,String> pathsToJsonFiles = new HashMap<>();

        pathsToJsonFiles.put("allStationsToJson", allStationsToJson);
        pathsToJsonFiles.put("allJsonToJson", allJsonToJson);
        pathsToJsonFiles.put("allCsvToJson", allCsvToJson);
        pathsToJsonFiles.put("pathForFindFile", pathForFindFile);
        pathsToJsonFiles.put("map", map);





        ObjectMapper mapper = new ObjectMapper();


        //Поиск файлов в каталоге папок
        FileSearch fileSearch = new FileSearch(new File(pathsToJsonFiles.get("pathForFindFile")));

        //Парсер HTML
        String address = "https://skillbox-java.github.io/";
        ParserHtml parserHtml = new ParserHtml(address);

        File outputStation = new File(pathsToJsonFiles.get("allStationsToJson"));
        mapper.writeValue(outputStation,ParserHtml.getStationsFinal());

        File outputMap = new File(pathsToJsonFiles.get("map"));
        Map map1 = new HashMap();
        map1.put("stations", ParserHtml.getStations());
        map1.put("lines", ParserHtml.getLines());
        mapper.writeValue(outputMap,map1);

        //Парсер JSON файлов
        ParserJson parserJson = new ParserJson();

        FileSearch.getListJson().forEach(fileJson ->{
            try {
                ParserJson.parse(fileJson);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        List<Map<String, String>> allJson = ParserJson.getAllJson();
        File outputJson = new File(pathsToJsonFiles.get("allJsonToJson"));
        mapper.writeValue(outputJson,allJson);

        //Парсер CSV файлов
        ParserCsv parserCsv = new ParserCsv();
        FileSearch.getListCsv().forEach(fileCsv ->{
            try {
                ParserCsv.parse(fileCsv);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        List<Map<String,String>> allCsv = ParserCsv.getAllCsv();
        File outputCsv = new File(pathsToJsonFiles.get("allCsvToJson"));
        mapper.writeValue(outputCsv,allCsv);

        //Сбор требуемых данных в JSON файл
        InToFile inToFile = new InToFile();
        inToFile.setPathsToJsonFiles(pathsToJsonFiles);
        InToFile.inToFile();
    }
}
