package org.example;

public interface AddressBookService {
    void insert(Contact contact);

    void edit();

    int isExist(String firstname, String lastName);

    void delete();
}
