package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface AddressBookService {
    void listContacts();

    void insert(Contact contact,List<Integer> addressBooks) throws SQLException;

    void edit();

    int isExist(String firstname, String lastName);

    void delete();

    void getCityWiseOrStateWiseContacts();

    void printResultSet(ResultSet rs);

    void getCityWiseOrStateWiseContactCount();

    void getSortedContactsForGivenCity();
    List<Integer> selectAddressBook();
    void getTypeWiseCount();
}
