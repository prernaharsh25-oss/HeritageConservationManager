import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection.java
 *
 * Single place responsible for creating JDBC connections to the
 * heritage_conservation MySQL database.
 *
 * Update URL, USER, and PASSWORD to match your local MySQL setup
 * before running the application.
 */
public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/heritage_conservation?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = System.getenv("MYSQL_PASSWORD");
    /**
     * Opens and returns a new JDBC Connection to the MySQL database.
     * Callers are responsible for closing the connection
     * (use try-with-resources).
     *
     * @return an open java.sql.Connection
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
