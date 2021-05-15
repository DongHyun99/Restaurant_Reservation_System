package com.restaurant_reservation_system.database;

public class User {
    String name;
    String ID;
    String phoneNumber;
    String pw;

    public User(String ID, String pw,String name, String phoneNumber){
        this.name = name;
        this.ID = ID;
        this.phoneNumber = phoneNumber;
        this.pw = pw;
    }

    public String getName(){
        return name;
    }

    public String getID(){
        return ID;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getPw() {
        return pw;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void setPhone_number(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

}
