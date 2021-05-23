package com.restaurant_reservation_system.database;

public class User {
    String name;
    String ID;
    String phoneNumber;
    String pw;
    String penalty;
    String admin;

    public User(String ID, String pw,String name, String phoneNumber, String penalty, String admin){
        this.name = name;
        this.ID = ID;
        this.phoneNumber = phoneNumber;
        this.pw = pw;
        this.penalty = penalty;
        this.admin = admin;
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

    public String getPenalty() {return penalty;}

    public String getAdmin() {return admin;}

    public void setName(String name){
        this.name = name;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setPenalty(String penalty) {this.penalty = penalty;}

    public void setAdmin(String admin) {this.admin = admin;}

}
