package Server;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AuthenticationService {

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
            e.printStackTrace();
        }

        return Optional.empty();
    }
}