package com.maksystechnologies.maksys.Models;

public class Engineer {
    int id;
    String name,designation,contactno,email,profile_photo,pin_no,address,dob,department,emp_id,password,username;

    public Engineer() {
    }

    public Engineer(String name, String designation, String contactno, String email, String profile_photo, String pin_no, String address, String dob, String department, String emp_id, String password,String username) {
        this.name = name;
        this.designation = designation;
        this.contactno = contactno;
        this.email = email;
        this.profile_photo = profile_photo;
        this.pin_no = pin_no;
        this.address = address;
        this.dob = dob;
        this.department = department;
        this.emp_id = emp_id;
        this.password = password;
        this.username=username;
    }

    public Engineer(int id, String name, String designation, String contactno, String email, String profile_photo, String pin_no, String address, String dob, String department, String emp_id, String password,String username) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.contactno = contactno;
        this.email = email;
        this.profile_photo = profile_photo;
        this.pin_no = pin_no;
        this.address = address;
        this.dob = dob;
        this.department = department;
        this.emp_id = emp_id;
        this.password = password;
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getPin_no() {
        return pin_no;
    }

    public void setPin_no(String pin_no) {
        this.pin_no = pin_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
