package com.molvenolakeresort.hotel.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Facilities implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean miniBar = true;
    private boolean airConditioning = true;
    private boolean television = true;
    private boolean sonos = true;
    private boolean bath = true;
    private boolean toilet = true;
    private boolean safe = true;
    private boolean wifi = true;
    private boolean phone = true;

//    @OneToOne
//    private Room room;
//
//    Facilities(Room room) {
//        setRoom(room);
//    }

    @Override
    public String toString() {
        return "Facilities{" +
                "miniBar=" + miniBar +
                ", airConditioning=" + airConditioning +
                ", television=" + television +
                ", sonos=" + sonos +
                ", bath=" + bath +
                ", toilet=" + toilet +
                ", safe=" + safe +
                ", wifi=" + wifi +
                ", phone=" + phone +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }

    public boolean isMiniBar() {
        return miniBar;
    }

    public void setMiniBar(boolean miniBar) {
        this.miniBar = miniBar;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public boolean isSonos() {
        return sonos;
    }

    public void setSonos(boolean sonos) {
        this.sonos = sonos;
    }

    public boolean isBath() {
        return bath;
    }

    public void setBath(boolean bath) {
        this.bath = bath;
    }

    public boolean isToilet() {
        return toilet;
    }

    public void setToilet(boolean toilet) {
        this.toilet = toilet;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

}
