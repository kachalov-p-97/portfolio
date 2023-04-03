package src.main.java;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;


public class ParserHtml {
    protected static Map<String, List<Map<String, String>>> stations = new TreeMap<String, List<Map<String, String>>>();
    protected static List<Map<String,String>> lines = new ArrayList<>();
    protected static Map <String, List<Map<String,String>>> stationsFinal = new HashMap<>();


    public ParserHtml(String address) throws IOException {
        Document doc = Jsoup.connect(address).get();
        Element body = doc.body();
        parseStations(body);
        parseLines(body);
        List<Map<String,String>> temporaryList = new ArrayList<>();
        for (var s : stations.keySet()){

            stations.get(s).forEach(x -> {
                Map<String,String> temporaryMap = new HashMap<>();
                temporaryMap.put("hasConnection",x.get("connection"));
                lines.forEach(l -> {
                    if (l.containsKey(s)){
                        temporaryMap.put("line",l.get(s));
                    }
                });

                temporaryMap.put("name",x.get("name"));
                temporaryList.add(temporaryMap);
            });

        }
        stationsFinal.put("stations",temporaryList);
    }

    private static void parseStations(Element body) {
        Elements stations = body.getElementsByClass("js-metro-stations t-metrostation-list-table");
        stations.forEach(x -> {
            List<Map<String, String>> temporaryList = new ArrayList<>();

            Elements name = x.getElementsByClass("single-station");
            name.forEach(y -> {
                Map<String,String> temporaryMap = new HashMap<String, String>();
                boolean trigger =
                    y.getElementsByTag("span").
                    attr("title").
                    contains("переход");

                temporaryMap.put(
                    "name",y.getElementsByClass("name").text()
                );
                temporaryMap.put("connection",
                    String.valueOf(trigger)
                );
                temporaryList.add(temporaryMap);

            });
            ParserHtml.stations.put(x.attr("data-line"),temporaryList);
        });
    }

    private static void parseLines(Element body) {
        Elements lines = body.getElementsByClass("js-toggle-depend s-depend-control-single  s-depend-control-active").select("span");
        Elements linesNoOne = body.getElementsByClass("js-toggle-depend s-depend-control-single  ").select("span");
        lines.addAll(linesNoOne);
        lines.forEach(x -> {
            Map<String,String> temporaryMap = new HashMap<>();
            temporaryMap.put(
                    x.attr("data-line"),
                    x.text()
            );
            ParserHtml.lines.add(temporaryMap);
        });
    }

    public static Map<String, List<Map<String, String>>> getStationsFinal() {
        return stationsFinal;
    }

    public static Map<String, List<Map<String, String>>> getStations() {
        return stations;
    }

    public static List<Map<String, String>> getLines() {
        return lines;
    }
}
