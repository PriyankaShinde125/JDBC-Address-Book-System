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
    public static final String CREATE_ADDRESS_BOOK = "create table tbl_addressbook(id int not null primary key auto_increment, firstname varchar(50) not null,lastname varchar(50) not null,phonenumber long not null,emailid varchar(100) not null, area varchar(50) not null,city varchar(30) not null,state varchar(30) not null, zip int not null)";
    public static final String SQL_INSERT_CONTACT = "insert into tbl_addressbook VALUES ( default , ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_CONTACT_BY_NAME = "select * from tbl_addressbook where firstname = ? and lastname = ?";
    public static final String SQL_DELETE_CONTACT = "delete from tbl_addressbook where id = ?";

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
