package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressBookMain {
    public static void main(String[] args) throws SQLException {
        Connection con = Constants.getConnection();
        if (con != null){
            System.out.println("Connected to database");
            Statement stmt = con.createStatement();
            stmt.executeUpdate(Constants.CREATE_ADDRESS_BOOK);
        }
        else
            System.out.println("Connection couldn't establish");
    }

}
