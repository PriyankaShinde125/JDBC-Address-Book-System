package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class AddressBookMain {
    private static final int INSERT_CONTACT = 1;
    public static final int EDIT_CONTACT = 2;
    private static final int EXIT = 0;

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        AddressBookService addressBookService = new AddressBookOperations();
        while (true) {
            System.out.println("Enter your choice : " +
                    "\n1 : Insert contact " +
                    "\n2 : Edit Contact" +
                    "\n0 : Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case INSERT_CONTACT:
                    Contact contact = new Contact();
                    contact.create();
                    addressBookService.insert(contact);
                    break;
                case EDIT_CONTACT:
                    addressBookService.edit();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
                case EXIT:
                    return;
            }
        }
    }
}
