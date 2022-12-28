package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class AddressBookOperations implements AddressBookService {
    public static final int FIRST_NAME = 1;
    public static final int LAST_NAME = 2;
    public static final int PHONE_NUMBER = 3;
    public static final int EMAIL_ID = 4;
    public static final int AREA = 5;
    public static final int CITY = 6;
    public static final int STATE = 7;
    public static final int ZIP = 8;

    @Override
    public int insert(Contact contact) {
        Connection con = Constants.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(Constants.SQL_INSERT_CONTACT);
            stmt.setString(FIRST_NAME, contact.getFirstName());
            stmt.setString(LAST_NAME, contact.getLastName());
            stmt.setLong(PHONE_NUMBER, contact.getPhoneNumber());
            stmt.setString(EMAIL_ID, contact.getEmailId());
            stmt.setString(AREA, contact.getArea());
            stmt.setString(CITY, contact.getCity());
            stmt.setString(STATE, contact.getState());
            stmt.setInt(ZIP, contact.getZip());
            int result = stmt.executeUpdate();
            if(result > 0)
            System.out.println("contact added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
