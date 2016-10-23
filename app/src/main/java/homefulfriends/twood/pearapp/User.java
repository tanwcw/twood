package com.homeful.jayne.pearapp;


public class User {
    private String name;
    private int bankDetails;
    private String email;
    private Boolean isParent;

    public User(){}

    public User(int bankDetails){
        this.bankDetails = bankDetails;
    }

    public User(String username, int bankDetails, String email, Boolean isParent) {
        this.name = username;
        this.bankDetails = bankDetails;
        this.email = email;
        this.isParent = isParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public int getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(int bankDetails) {
        this.bankDetails = bankDetails;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", bankDetails=" + bankDetails +
                ", email='" + email + '\'' +
                ", isParent=" + isParent +
                '}';
    }
}
