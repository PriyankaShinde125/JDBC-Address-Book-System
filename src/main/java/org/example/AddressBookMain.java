package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class AddressBookMain {
    private static final int INSERT_CONTACT = 1;
    public static final int EDIT_CONTACT = 2;
    public static final int DELETE_CONTACT = 3;
    private static final int EXIT = 0;
    public static final int CITYWISE_OR_STATEWISE_CONTACTS = 4;
    public static final int CITYWISE_OR_STATEWISE_CONTACT_COUNT = 5;
    public static final int GET_SORTED_CONTACTS_FOR_GIVEN_CITY = 6;
    public static final int GET_TYPEWISE_COUNT = 7;

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        AddressBookService addressBookService = new AddressBookOperations();
        while (true) {
            System.out.println("Enter your choice : " +
                    "\n1 : Insert contact " +
                    "\n2 : Edit Contact" +
                    "\n3 : Delete Contact " +
                    "\n4 : Get citywise or statewise contacts" +
                    "\n5 : Get citywise or statewise contact count" +
                    "\n6 : Get sorted contacts for given city" +
                    "\n7 : Get number of contacts by type" +
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
                case DELETE_CONTACT:
                    addressBookService.delete();
                    break;
                case CITYWISE_OR_STATEWISE_CONTACTS:
                    addressBookService.getCityWiseOrStateWiseContacts();
                    break;
                case CITYWISE_OR_STATEWISE_CONTACT_COUNT:
                    addressBookService.getCityWiseOrStateWiseContactCount();
                    break;
                case GET_SORTED_CONTACTS_FOR_GIVEN_CITY:
                    addressBookService.getSortedContactsForGivenCity();
                    break;
                case GET_TYPEWISE_COUNT:
                    addressBookService.getTypeWiseCount();
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
