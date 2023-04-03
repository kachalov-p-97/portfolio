import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

public class Loader {
    static StringBuilder builder = new StringBuilder();
    static int votersCount;
    public static void main(String[] args) throws Exception {
        String fileName = "C:\\Users\\Laptop\\IdeaProjects\\Performance\\VoteAnalyzer\\res\\data-18M.xml";
        var start = System.currentTimeMillis();

        //  Узнаём номер последнего голосовавшего
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        votersCount = db
                .parse(new File(fileName))
                .getElementsByTagName("voter")
                .getLength();

        //  Реализуем SAX-механизм чтения XML-файлов
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        CustomHandler handler = new CustomHandler();
        saxParser.parse(new InputSource(new FileInputStream(fileName)), handler);
        short end = (short) ((System.currentTimeMillis() - start) / 1000);
        System.out.println("Program execution time: " + end + " sec");
    }

    //  Класс для реализации DefaultHandler
    static class CustomHandler extends DefaultHandler {

        //  Счетчик записей
        private int voterCount = 0;
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equalsIgnoreCase("voter")) {
                String name = attributes.getValue("name");
                String birthDay = attributes.getValue("birthDay");
                //  Собираем в StringBuilder часть запроса для множественных INSERT’ов
                builder
                        .append("('")
                        .append(name)
                        .append("', '")
                        .append(birthDay)
                        .append("'),\t\n");
                voterCount++;
                //  Чтобы не переполнить StringBuilder,
                //  каждые 100_000 пользователей будут записываться в MySQL одним запросом
                if (voterCount % 100_000 == 0) {
                    writingDB();
                }
            }
        }

        private static void writingDB() {
            try {
                DBConnection.databaseWriting(builder);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            builder = new StringBuilder();
        }

        @Override
        public void endDocument() {
            writingDB();
            System.out.println("Total voters: " + voterCount);
        }
    }
}