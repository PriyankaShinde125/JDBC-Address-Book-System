package org.example;

import java.util.Scanner;

public class Contact {
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String emailId;
    private String area;
    private String city;
    private String state;
    private int zip;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
    public void create() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your details to create contact : ");
        System.out.print(" First name = ");
        firstName = sc.next().toLowerCase();
        System.out.print("\n Last name = ");
        lastName = sc.next().toLowerCase();
        System.out.print("\n Phone no = ");
        phoneNumber = Long.valueOf(sc.next());
        System.out.print("\n Email Id = ");
        emailId = sc.next();
        System.out.print("\n Area = ");
        area = sc.next();
        System.out.print("\n City = ");
        city = sc.next();
        System.out.print("\n State = ");
        state = sc.next();
        System.out.print("\n Zip = ");
        zip = sc.nextInt();
    }
}
