package com.restaurant_reservation_system.database;

public class Booking {

    String reservation_num;
    String covers;
    String date;
    String time;
    String table_id;
    String customer_id;
    String arrivalTime;

    public Booking(String reservation_num, String covers, String date, String time, String table_id, String customer_id, String arrivalTime) {
        this.reservation_num = reservation_num;
        this.covers = covers;
        this.date = date;
        this.time = time;
        this.table_id = table_id;
        this.customer_id = customer_id;
        this.arrivalTime = arrivalTime;
    }

    public String getReservation_num() {
        return reservation_num;
    }

    public void setReservation_num(String reservation_num) {
        this.reservation_num = reservation_num;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
