package com.maksystechnologies.maksys.Models;

public class Customer {
    String Id;
    String branchname;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    String branchid;
    String Username;
    String Email;

    String pin;
    String customerId;
    String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Customer(String id, String branchname, String branchid, String username, String email, String pin, String customerId, String contactPerson, String address, String passwrd, String mobil) {
        Id = id;
        this.branchname = branchname;
        this.branchid = branchid;
        Username = username;
        Email = email;

        this.pin = pin;
        this.customerId = customerId;
        this.contactPerson = contactPerson;
        this.address = address;
        this.password=passwrd;
        this.mobile=mobil;

    }

    public String getPin() {

        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String contactPerson;
    String address;

    public Customer() {
    }


    public Customer(String id, String username, String email) {
        Id = id;
        Username = username;
        Email = email;



    }

    public Customer(String id, String branchname, String branchid, String username, String email) {
        Id = id;
        this.branchname = branchname;
        this.branchid = branchid;

        Username = username;
        Email = email;

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


}
