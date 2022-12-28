package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Constants {
    public static final String BASE_URL = "jdbc:mysql://localhost:3307/";
    public static final String DB_NAME = "dbaddressbookservice";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "Root@123";
    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(DRIVER_NAME);
            con = DriverManager.getConnection(BASE_URL + DB_NAME, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {

        } catch (SQLException e) {

        }
        return con;
    }
}
