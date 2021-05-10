package com.example.theclothingapplication.API.Model;

public class LoginApiModel
{
    private String firstName, lastName, email, contact, password, token ;

    public LoginApiModel(String firstName, String lastName, String email, String contact, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

    public LoginApiModel(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
