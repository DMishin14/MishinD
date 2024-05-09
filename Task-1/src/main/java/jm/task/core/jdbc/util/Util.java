package jm.task.core.jdbc.util;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;
        String password="postgres";
        String username="postgres";
        String url="jdbc:postgresql://localhost:5432/postgres";
        try (var connection = DriverManager.getConnection(url, username, password)) {
            System.out.println(connection.getTransactionIsolation()); }
    }
} // реализуйте настройку соеденения с БД

