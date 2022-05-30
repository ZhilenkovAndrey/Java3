package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnector {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:C:/SQLite/chat.db");
        } catch (SQLException e) {
            throw new RuntimeException("SWW during getting a connection.", e);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("SWW during closing a connection.", e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("SWW during a rollback operation.", e);
        }
    }
}