package com.restaurant_reservation_system.database;

public class SingleItem {

    String id;
    String arrival;
    int resId;

    // Generate > Constructor
    public SingleItem( String id, String arrival,int resId) {
        this.id = id;
        this.arrival = arrival;
        this.resId = resId;
    }

    // Generate > Getter and Setter
       /* public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId; }

    // Generate > toString() : 아이템을 문자열로 출력

    @Override
    public String toString() {
        return "SingleItem{" +
                ", id='" + id + '\'' +
                ", arrival_time"+arrival+'\''+
                '}';
    }
}
