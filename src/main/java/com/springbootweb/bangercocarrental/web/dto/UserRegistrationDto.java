package com.springbootweb.bangercocarrental.web.dto;

import java.time.LocalDate;
import java.util.Date;

public class UserRegistrationDto {

    private String username;
    private String password;
    private String user_fName;
    private String user_lName;
    private String user_email;
    private Date birthday;
    private int user_phoneNo;
    private String user_address;
    private String documents;
    private String userRole;
    private boolean enabled;

    public UserRegistrationDto(String username, String password, String user_fName, String user_lName, String user_email, Date birthday, int user_phoneNo, String user_address, String documents, String userRole, boolean enabled) {
        this.username = username;
        this.password = password;
        this.user_fName = user_fName;
        this.user_lName = user_lName;
        this.user_email = user_email;
        this.birthday = birthday;
        this.user_phoneNo = user_phoneNo;
        this.user_address = user_address;
        this.documents = documents;
        this.userRole = userRole;
        this.enabled = enabled;
    }

    public UserRegistrationDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_fName() {
        return user_fName;
    }

    public void setUser_fName(String user_fName) {
        this.user_fName = user_fName;
    }

    public String getUser_lName() {
        return user_lName;
    }

    public void setUser_lName(String user_lName) {
        this.user_lName = user_lName;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getUser_phoneNo() {
        return user_phoneNo;
    }

    public void setUser_phoneNo(int user_phoneNo) {
        this.user_phoneNo = user_phoneNo;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enable) {
        this.enabled = enable;
    }
}
