package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnector {

    private static final Logger log = LogManager.getLogger(DatabaseConnector.class);

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:C:/SQLite/chat.db");
        } catch (SQLException e) {
            log.throwing(e);
            log.error("Something went wrong during  getting a connection to DataBase");
            throw new RuntimeException("SWW during getting a connection.", e);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.throwing(e);
            log.error("Something went wrong during closing DataBase connection");
            throw new RuntimeException("SWW during closing a connection.", e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            log.throwing(e);
            log.error("Something went wrong during DataBase a rollback operation.");
            throw new RuntimeException("SWW during a rollback operation.", e);
        }
    }
}