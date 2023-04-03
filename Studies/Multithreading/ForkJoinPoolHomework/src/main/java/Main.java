import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.Thread.sleep;

public class Main {

    private static final int MAX_DEPTH = 2;
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool(MAX_THREADS);

    static Map<String, Integer> map = new TreeMap<>();

    public static Map<String, Integer> getMap() {
        return map;
    }

    public static void setMap(Map<String, Integer> map) {
        Main.map = map;
    }
    static String urlStart = "https://www.lenta.ru/";

    public static void main(String[] args) {

        SiteMapParserAction siteMapParserAction = new SiteMapParserAction(urlStart, 1);
        FORK_JOIN_POOL.invoke(siteMapParserAction);
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String,Integer> x : map.entrySet()) {
            stringBuilder
                    .append(x.getKey())
                    .append("\t")
                    .append(x.getValue())
                    .append("\t\n");
        }
        try (FileWriter fileWriter = new FileWriter("final.txt")) {
            fileWriter.write(String.valueOf(stringBuilder));
            System.out.println("Файл записан");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class SiteMapParserAction extends RecursiveAction {
        private final String url;
        private final int depth;

        public SiteMapParserAction(String url, int depth) {
            this.url = url;
            this.depth = depth;
        }
        @Override
        protected void compute() {
            int response1 = 0;
            if (depth > MAX_DEPTH) {
                map.put(url,response1);
                return;
            }
            try {
                if (url.contains(".jpg") ||
                        url.contains(".png") ||
                        url.contains(".jpeg") ||
                        url.contains(".JPG") ||
                        url.contains(".htm")) {
                    return;
                }
                Connection connection = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("https://www.google.com/");
                Document doc = connection.get();
                Elements links = doc.select("a[href]");
                List<SiteMapParserAction> tasks = new ArrayList<>();
                for (Element link : links) {
                    String linkUrl = link.attr("href");
                    if (linkUrl.charAt(0) == '/' &&  !getMap().containsKey(linkUrl)) {
                        linkUrl = urlStart + link.attr("href");
                        response1 = connection.method(Connection.Method.GET).execute().statusCode();
                        sleep(100);
                        SiteMapParserAction task = new SiteMapParserAction(linkUrl, depth + 1);
                        tasks.add(task);
                    }
                }
                invokeAll(tasks);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}