import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/skillbox";
        String  user = "root";
        String pass = "Piou0897";
        try {
            Connection connection = DriverManager.getConnection(url, user,pass);
            Statement statement = connection.createStatement();

            Map<String, ArrayList<Integer>> temporaryMap = new TreeMap<>();
            for (int i = 1; i <= 12; i++) {
                ResultSet resultSet = statement.executeQuery(
                    "SELECT " +
                            "course_name CourseName, " +
                            "SUM(price) SumPrice, " +
                            "COUNT(*) 'Count' " +
                        "FROM `PurchaseList` " +
                        "WHERE YEAR(`subscription_date`) = 2018 AND MONTH(`subscription_date`) = " + i +
                        " GROUP BY course_name, MONTH(`subscription_date`) = " + i);

                while (resultSet.next()) {
                    ArrayList<Integer> list = new ArrayList<>();
                    if (temporaryMap.containsKey(resultSet.getString("CourseName"))){
                         list = temporaryMap.get(resultSet.getString("CourseName"));
                    }
                    list.add(resultSet.getInt("Count"));
                    temporaryMap.put(resultSet.getString("CourseName"),list);
                }
            }
            HashMap<String,Double> hashMap = new HashMap<>();

            for (Map.Entry<String, ArrayList<Integer>> x : temporaryMap.entrySet()){
                AtomicInteger count = new AtomicInteger();
                int month = x.getValue().size();
                x.getValue().forEach(count::addAndGet);
                double coefficient = count.doubleValue() / month;
                hashMap.put(x.getKey(),coefficient);
            }
            for (Map.Entry<String,Double> z : hashMap.entrySet()){
                String print = z.getKey() + " - " + z.getValue();
                System.out.println(print);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}