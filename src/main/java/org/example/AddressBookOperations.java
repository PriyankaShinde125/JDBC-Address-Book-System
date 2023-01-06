package org.example;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public void listContacts() {
        String sqlListContacts = "select * from tbl_contact";
        try (Connection con = Constants.getConnection()) {
            Statement pst = con.createStatement();
            ResultSet resultSet = pst.executeQuery(sqlListContacts);
            printResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Contact contact, List<Integer> addressBooks) throws SQLException {
        PreparedStatement stmt = null;
        if (isExist(contact.getFirstName(), contact.getLastName()) > 0) {
            System.out.println("Contact is already exist");
            return;
        }

        int contactId;
        Connection con = Constants.getConnection();
        Savepoint savepoint1 = null;
        try {
            con.setAutoCommit(false);
            savepoint1 = con.setSavepoint("Savepoint1");
            String sqlInsertContact = "insert into tbl_contact VALUES ( default , ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sqlInsertContact, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(FIRST_NAME, contact.getFirstName());
            stmt.setString(LAST_NAME, contact.getLastName());
            stmt.setLong(PHONE_NUMBER, contact.getPhoneNumber());
            stmt.setString(EMAIL_ID, contact.getEmailId());
            stmt.setString(AREA, contact.getArea());
            stmt.setString(CITY, contact.getCity());
            stmt.setString(STATE, contact.getState());
            stmt.setInt(ZIP, contact.getZip());
            stmt.executeUpdate();
            ResultSet contactIds = stmt.getGeneratedKeys();
            if (contactIds.next()) {
                contactId = contactIds.getInt(1);
            } else {
                throw new SQLException("Creating contact failed, no ID obtained.");
            }
            String sqlInsertIntoAddressContact = "insert into tbl_contact_addressbook values(default,?,?)";
            PreparedStatement statement = con.prepareStatement(sqlInsertIntoAddressContact);
            for (Integer integer : addressBooks) {
                statement.setInt(1, integer);
                statement.setInt(2, contactId);
                statement.addBatch();
            }
            int[] result = statement.executeBatch();
            if (result.length > 0) {
                System.out.println("Insertion successful");
                con.commit();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Contact creation failed");
            con.rollback(savepoint1);
            con.close();
        }
    }

    @Override
    public void edit() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter firstname and lastname");
        String firstName = sc.next();
        String lastname = sc.next();
        int contactId;
        if ((contactId = isExist(firstName, lastname)) == 0) {
            System.out.println("Contact is not present in addressbook");
            return;
        }
        String updateSql = "Update tbl_contact set ";
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

        String updateWholeSql = updateSql + updateField + updateWhereCondition;
        try (Connection con = Constants.getConnection()) {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(updateWholeSql);
            if (result > 0) {
                System.out.println("Contact updated successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int isExist(String firstname, String lastName) {
        String sqlSelectContactByName = "select * from tbl_contact where firstname = ? and lastname = ?";
        int contactId = 0;
        try (Connection con = Constants.getConnection()) {
            PreparedStatement pst = con.prepareStatement(sqlSelectContactByName);
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
        try (Connection con = Constants.getConnection()) {
            String sqlDeleteContact = "delete from tbl_contact where id = ?";
            PreparedStatement pst = con.prepareStatement(sqlDeleteContact);
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
        int choice = getCityOrState();
        String cityOrState;
        if (choice == 1)
            cityOrState = "city";
        else
            cityOrState = "state";
        String sqlGetContact = "select * from tbl_contact where " + cityOrState + " = ?";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter value : ");
        String input = sc.next();
        try (Connection con = Constants.getConnection()) {
            PreparedStatement pst = con.prepareStatement(sqlGetContact);
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

    @Override
    public void getCityWiseOrStateWiseContactCount() {
        int choice = getCityOrState();
        String cityOrState;
        if (choice == 1)
            cityOrState = "city";
        else
            cityOrState = "state";
        String sqlGetContactCount = "select count(*) as totalcontacts, " + cityOrState + " from tbl_contact group by " + cityOrState;
        try (Connection con = Constants.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetContactCount);
            System.out.println("Count \t " + cityOrState);
            while (rs.next()) {
                System.out.println(rs.getString("totalcontacts") + " \t " + rs.getString(cityOrState));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getSortedContactsForGivenCity() {
        String sqlGetContact = "select * from tbl_contact where city = ? order by firstname,lastname";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter value : ");
        String input = sc.next();
        try (Connection con = Constants.getConnection()) {
            PreparedStatement pst = con.prepareStatement(sqlGetContact);
            pst.setString(1, input);
            ResultSet rs = pst.executeQuery();
            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> selectAddressBook() {
        String sqlListAddressBook = "select * from tbl_addressbook";
        List<Integer> addressBookIds = new ArrayList<>();
        try (Connection con = Constants.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlListAddressBook);
            Scanner sc = new Scanner(System.in);
            System.out.println("Select from following");
            System.out.println("id \t type");
            while (rs.next()) {
                System.out.println(rs.getString("id") + " \t " + rs.getString("type"));
            }
            String[] inputIds = sc.nextLine().split("[\\s \\- ,. :]");
            Arrays.stream(inputIds).forEach(s -> addressBookIds.add(Integer.parseInt(s)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return addressBookIds;
    }

    @Override
    public void getTypeWiseCount() {
        String sqlTypeWiseCount = "SELECT count(*) as totalcontacts,type FROM tbl_contact c JOIN tbl_contact_addressbook ca ON c.id = ca.contactid JOIN tbl_addressbook a ON ca.addressbookid = a.id group by a.type";
        try (Connection con = Constants.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlTypeWiseCount);
            System.out.println("Count \t " + "Type");
            while (rs.next()) {
                System.out.println(rs.getString("totalcontacts") + " \t " + rs.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    int getCityOrState() {
        System.out.println("Enter your choice : " +
                "\n1 : Get given city contact " +
                "\n2 : Get given state contact ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        return choice;
    }
}
