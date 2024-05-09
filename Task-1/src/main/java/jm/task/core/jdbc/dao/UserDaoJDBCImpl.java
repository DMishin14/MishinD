package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String USER_PASSWORD = "postgres";
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS users"
                + "(id SERIAL PRIMARY KEY,"
                + "name VARCHAR(50) NOT NULL,"
                + "lastname VARCHAR(50) NOT NULL,"
                + "age INTEGER NOT NULL);";
        try (Statement statement = getConnection(URL, USER_NAME, USER_PASSWORD).createStatement()) {
            statement.execute(CREATE_USER_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        final String DROP_USER_TABLE = "DROP TABLE IF EXISTS users";
        try (Statement statement = getConnection(URL, USER_NAME, USER_PASSWORD).createStatement()) {
            statement.execute(DROP_USER_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String ADD_USER = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD).prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeUserById(long id) {
        final String DELETE_USER = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection(URL, USER_NAME, USER_PASSWORD).prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        final String ALL_USERS = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Statement statement = getConnection(URL, USER_NAME, USER_PASSWORD).createStatement()) {
            ResultSet resultSet = statement.executeQuery(ALL_USERS);
            while (resultSet.next()) {
                User newUser = new User();
                newUser.setId(resultSet.getLong("id"));
                newUser.setName(resultSet.getString("name"));
                newUser.setLastName(resultSet.getString("lastname"));
                newUser.setAge(resultSet.getByte("age"));
                users.add(newUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        final String CLEAN_TABLE = "TRUNCATE TABLE users";
        try (Statement statement = getConnection(URL, USER_NAME, USER_PASSWORD).createStatement()) {
            statement.execute(CLEAN_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}