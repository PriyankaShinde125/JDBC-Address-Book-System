package org.example;

import java.sql.ResultSet;

public interface AddressBookService {

    void insert(Contact contact);

    void edit();

    int isExist(String firstname, String lastName);

    void delete();

    void getCityWiseOrStateWiseContacts();

    void printResultSet(ResultSet rs);

    void getCityWiseOrStateWiseContactCount();

    void getSortedContactsForGivenCity();
    int selectAddressBook();
    boolean isExistAddressBook(int id);

    void getTypeWiseCount();
}
