package it.step.db;

import it.step.model.Gender;
import it.step.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerDB {
    public EmployeeManagerDB() {}

    public Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_manager", "root", "ORA123");
            return connection;

        } catch(SQLException ex) {
            System.out.println("Eroare de conexiune: " + ex.getMessage());
            return null;
        }
    }

    public int getPersonID() {

        String getID = "SELECT MAX(id) as max from employee ";
        int id = -1;

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getID) ) {

            while(resultSet.next()) {
                id = resultSet.getInt("max");
            }

        } catch (SQLException ex) {
            System.out.println("Nu am putut obtine ID-ul!");
        }

        return id;
    }

    // create
    public void create(Person pers) {
        try {
            String sql = "INSERT into employee(name, surname, gender, email, birthdate) values (?, ?, ?, ?, ?)";
            Connection connection = getConnection();

            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, pers.getName());
                preparedStatement.setString(2, pers.getSurname());
                preparedStatement.setString(3, pers.getGender().toString().equalsIgnoreCase("MALE") ? "Male" : "Female");
                preparedStatement.setString(4, pers.getEmail());
                preparedStatement.setDate(5, Date.valueOf(pers.getBirthdate()));

                int rows = preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Nu am putut face insertul!");
        }
    }

    // selectAll
    public List<Person> selectAll() {
        List<Person> result = new ArrayList<>();
        String sql = "SELECT id, name, surname, gender, email, birthdate from employee ORDER BY id";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql) ) {

            // data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                Gender gender = resultSet.getString("gender").equalsIgnoreCase("MALE") ? Gender.MALE : Gender.FEMALE ;
                String email = resultSet.getString("email");
                Date birthdate = resultSet.getDate("birthdate");

                result.add(new Person(id, name, surname, gender, email, birthdate.toLocalDate()));
            }


        } catch (SQLException ex) {
            System.out.println("Nu am putut face selectul!");
        }

        return result;
    }

    // update
    public void update(Person pers) {

        String updateSql = "UPDATE employee set name = ?, surname = ?, gender = ?, email = ?, birthdate = ? where id=?";
        Connection connection = getConnection();

        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                preparedStatement.setString(1, pers.getName());
                preparedStatement.setString(2, pers.getSurname());
                preparedStatement.setString(3, pers.getGender().equalsIgnoreCase("MALE") ? "Male" : "Female");
                preparedStatement.setString(4, pers.getEmail());
                preparedStatement.setDate(5, Date.valueOf(pers.getBirthdate()));
                preparedStatement.setInt(6, pers.getId());

                int rows = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Nu am putut face update!");
            }
        }

    }

    // delete employee
    public void delete(int id) {

        try {
            String deleteSql = "DELETE from employee where id=?";
            Connection connection = getConnection();

            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);

                int rows = preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Nu am putut face delete!");
        }
    }

}

