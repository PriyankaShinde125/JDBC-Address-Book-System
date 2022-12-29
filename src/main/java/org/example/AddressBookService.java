package org.example;

import java.sql.ResultSet;
import java.util.List;

public interface AddressBookService {

    void insert(Contact contact,List<Integer> addressBooks);

    void edit();

    int isExist(String firstname, String lastName);

    void delete();

    void getCityWiseOrStateWiseContacts();

    void printResultSet(ResultSet rs);

    void getCityWiseOrStateWiseContactCount();

    void getSortedContactsForGivenCity();
    List<Integer> selectAddressBook();
    boolean isExistAddressBook(int id);

    void getTypeWiseCount();
}
