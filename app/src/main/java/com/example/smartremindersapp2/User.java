package com.example.smartremindersapp2;

import java.util.ArrayList;
import java.util.List;

public class User {
    /*
     variables
    */
    private String Name;
    private String UserName;
    private String Email;
    private String Password;
    private String Status;

    /*
     Constructors
    */
    public User(String name, String userName, String email, String password) {
        Name = name;
        UserName = userName;
        Email = email;
        Password = password;
        Status="0";

    }
    public User() {}

    /*
     getters
    */
    public String getStatus() {
        return Status;
    }
    public String getName() {
        return Name;
    }
    public String getUserName() {
        return UserName;
    }
    public String getEmail() {
        return Email;
    }
    public String getPassword() {
        return Password;
    }

    /*
      setters
    */
    public void setName(String name) {
        Name = name;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public void setStatus(String status) {
        Status = status;
    }
}
