package org.example;

import java.sql.Connection;

public class AddressBookMain {
    public static void main(String[] args) {
        Connection con = Constants.getConnection();
        if (con != null)
            System.out.println("Connected to database");
        else
            System.out.println("Connection couldn't establish");
    }
}
