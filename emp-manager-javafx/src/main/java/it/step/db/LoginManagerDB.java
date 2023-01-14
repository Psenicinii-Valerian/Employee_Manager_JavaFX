package it.step.db;

import it.step.model.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginManagerDB {

    // create
    public void create(Login login) {
        try {
            String sql = "INSERT into login(firstName, lastName, username, password, isActive) values (?, ?, ?, ?, ?)";
            Connection connection = getConnection();

            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, login.getFirstName());
                preparedStatement.setString(2, login.getLastName());
                preparedStatement.setString(3, login.getUsername());
                preparedStatement.setString(4, login.getPassword());
                preparedStatement.setBoolean(5, login.isActive());

                int rows = preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Nu am putut face insertul!");
        }
    }

    public int verify(Login login) {

        List<Login> result = new ArrayList<>();
        String sql = "SELECT * from login where username = '" + login.getUsername() + "' and password = '" + login.getPassword() + "'";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            // data
            while (resultSet.next()) {

                int id = resultSet.getInt("login_id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                boolean isActive = resultSet.getBoolean("isActive");

                result.add(new Login(firstName, lastName, login.getUsername(), login.getPassword(), isActive));
            }

            return result.size() > 0 ? 1 : 0;

        } catch (SQLException ex) {
            System.out.println("Nu am putut face selectul!");
        }

        return 0;
    }


    public void updateStatus(Login login) {

        String updateSql = "UPDATE login set isActive = 0 where username = ? and password = ?";
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                preparedStatement.setString(1, login.getUsername());
                preparedStatement.setString(2, login.getPassword());

                int rows = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Nu am putut face update!");
            }
        }
    }

    private Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_manager", "root", "ORA123");
            return connection;

        } catch(SQLException ex) {
            System.out.println("Eroare de conexiune: " + ex.getMessage());
            return null;
        }
    }
}
