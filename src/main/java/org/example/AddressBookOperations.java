package org.example;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class AddressBookOperations implements AddressBookService {
    public static final int FIRST_NAME = 1;
    public static final int LAST_NAME = 2;
    public static final int PHONE_NUMBER = 3;
    public static final int EMAIL_ID = 4;
    public static final int AREA = 5;
    public static final int CITY = 6;
    public static final int STATE = 7;
    public static final int ZIP = 8;
    public static final int ID = 1;

    @Override
    public void insert(Contact contact) {
        Connection con = Constants.getConnection();
        PreparedStatement stmt = null;
        if (isExist(contact.getFirstName(), contact.getLastName()) > 0) {
            System.out.println("Contact is already exist");
            return;
        }
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
            if (result > 0)
                System.out.println("contact added successfully");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter firstname and lastname");
        String firstName = sc.next();
        String lastname = sc.next();
        int contactId;
        if ((contactId = isExist(firstName, lastname)) > 0) {
            String updateSql = "Update tbl_addressbook set ";
            String updateWhereCondition = " where id = " + contactId;
            String updateField = null;
            System.out.println("Enter which field do you want to edit");
            System.out.println("1 : Firstname " +
                    "\n2 : Lastname " +
                    "\n3 : Phone number " +
                    "\n4 : EmailId " +
                    "\n5 : Area" +
                    "\n6 : City" +
                    "\n7 : State" +
                    "\n8 : Zip");
            int fieldToEdit = sc.nextInt();
            switch (fieldToEdit) {
                case FIRST_NAME:
                    System.out.println("Enter firstname :");
                    updateField = "firstname = '" + sc.next() + "'";
                    break;
                case LAST_NAME:
                    System.out.println("Enter lastname :");
                    updateField = "lastname = '" + sc.next() + "'";
                    break;
                case PHONE_NUMBER:
                    System.out.println("Enter phonenumber :");
                    updateField = "phonenumber = " + sc.nextLong();
                    break;
                case EMAIL_ID:
                    System.out.println("Enter Email Id :");
                    updateField = "emailid = '" + sc.next() + "'";
                    break;
                case AREA:
                    System.out.println("Enter area :");
                    updateField = "area = '" + sc.next() + "'";
                    break;
                case CITY:
                    System.out.println("Enter city :");
                    updateField = "city = '" + sc.next() + "'";
                    break;
                case STATE:
                    System.out.println("Enter state :");
                    updateField = "state = '" + sc.next() + "'";
                    break;
                case ZIP:
                    System.out.println("Enter zip :");
                    updateField = "zip = '" + sc.next() + "'";
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            try {
                String updateWholeSql = updateSql + updateField + updateWhereCondition;
                Connection con = Constants.getConnection();
                Statement stmt = con.createStatement();
                int result = stmt.executeUpdate(updateWholeSql);
                if (result > 0) {
                    System.out.println("Contact updated successfully");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else System.out.println("Contact is not present in addressbook");
    }

    @Override
    public int isExist(String firstname, String lastName) {
        int contactId = 0;
        Connection con = Constants.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(Constants.SQL_SELECT_CONTACT_BY_NAME);
            pst.setString(FIRST_NAME, firstname);
            pst.setString(LAST_NAME, lastName);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                contactId = Integer.parseInt(resultSet.getString("id"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactId;
    }

    @Override
    public void delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter firstname and lastname");
        String firstName = sc.next();
        String lastname = sc.next();
        int contactId;
        if ((contactId = isExist(firstName, lastname)) == 0) {
            System.out.println("Contact not exist");
            return;
        }
        Connection con = Constants.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(Constants.SQL_DELETE_CONTACT);
            pst.setInt(ID, contactId);
            int result = pst.executeUpdate();
            if (result > 0)
                System.out.println("Contact deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getCityWiseOrStateWiseContacts() {
        System.out.println("Enter your choice : " +
                "\n1 : Get given city contact " +
                "\n2 : Get given state contact ");
        Scanner sc = new Scanner(System.in);

        int choice = sc.nextInt();
        String getContactSql = "select * from tbl_addressbook ";
        if (choice == 1)
            getContactSql = getContactSql + "where city = ?";
        else
            getContactSql = getContactSql + "where state = ?";

        System.out.println("Enter value : ");
        String input = sc.next();
        try (Connection con = Constants.getConnection()) {
            PreparedStatement pst = con.prepareStatement(getContactSql);
            pst.setString(1, input);
            ResultSet rs = pst.executeQuery();
            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printResultSet(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println("First name = " + rs.getString("firstname"));
                System.out.println("Last name = " + rs.getString("lastname"));
                System.out.println("Phone number = " + rs.getString("phonenumber"));
                System.out.println("Email id = " + rs.getString("emailid"));
                System.out.println("Area = " + rs.getString("area"));
                System.out.println("City = " + rs.getString("city"));
                System.out.println("State = " + rs.getString("state"));
                System.out.println("Zip = " + rs.getString("zip"));
                System.out.println("-----------------------------------------------------------");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}