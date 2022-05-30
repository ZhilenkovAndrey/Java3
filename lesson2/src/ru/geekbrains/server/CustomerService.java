package ru.geekbrains.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public List<Customer> findAll() {
        Connection connection = DatabaseConnector.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT user_name, login, password FROM users");

            List<Customer> customers = new ArrayList<>();

            while (rs.next()) {
                customers.add(
                        new Customer(
                                rs.getString("user_name"),
                                rs.getString("login"),
                                rs.getString("password")
                        )
                );
            }

            return customers;
        } catch (SQLException e) {
            throw new RuntimeException("SWW during a fetching operation.", e);
        } finally {
            DatabaseConnector.close(connection);
        }
    }
}

