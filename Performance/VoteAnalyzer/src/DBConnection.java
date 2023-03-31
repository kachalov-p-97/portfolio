import java.sql.*;

public class DBConnection {

    private static Connection connection;
    private static final String dbName = "learn";
    private static final String dbUser = "root";
    private static final String dbPass = "Piou0897";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                        "?user=" + dbUser + "&password=" + dbPass);
                Statement statement = connection.createStatement();

                statement.execute("DROP TABLE IF EXISTS voter_count");
                statement.execute("CREATE TABLE voter_count(" +
                    "name TINYTEXT NOT NULL, " +
                    "birthDate DATE NOT NULL)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    protected static void databaseWriting(StringBuilder tempBuilder) throws SQLException{
        StringBuilder builder = new StringBuilder()
                .append("INSERT INTO voter_count (name, birthDate)\t\n")
                .append("VALUES \t\n")
                .append(tempBuilder);
        builder.deleteCharAt(builder.length() - 3);
        builder.append(";");
        DBConnection.getConnection().createStatement().execute(builder.toString());
    }
}
