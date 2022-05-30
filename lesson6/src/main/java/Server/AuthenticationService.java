package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AuthenticationService {
    private final Logger log = LogManager.getLogger(AuthenticationService.class);

    public Optional<String> findUsernameByLoginAndPassword(String login, String password) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            st.setString(1, login);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getString("user_name"));
            }
        } catch (SQLException e) {
            log.throwing(e);
            log.info("Something went wrong during reading SQL.");
        }

        return Optional.empty();
    }
}