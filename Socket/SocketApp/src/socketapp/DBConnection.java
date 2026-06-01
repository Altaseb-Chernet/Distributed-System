package socketapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = System.getenv().getOrDefault("CHAT_DB_URL", "jdbc:mysql://localhost:3306/chatdb");
    private static final String USER = System.getenv().getOrDefault("CHAT_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("CHAT_DB_PASSWORD", "");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
