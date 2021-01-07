package com.molvenolakeresort.hotel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String roomNumber;
    @Column(name = "available")
    private boolean available;
    private int numberOfSingleBeds;
    private int numberOfDoubleBeds;
    private int numberOfBabyBeds;
    private boolean disabledRoom;
    //@OneToOne
   // private Facilities facilities;
    private int noOfAdults;
    private int noOfChildren;
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;
    private int price;
    //private RoomStatus roomStatus;

    @JsonIgnore
    @ManyToMany (mappedBy = "bookedRooms", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Room() {
        available = true;
        //facilities = new Facilities(this);
    }

    public Room(String roomNumber, RoomType roomType, int noOfAdults, int noOfChildren, int singleBeds, int doubleBeds, int babyBeds, boolean disabled, int price) {
        this.available = true;
        //this.facilities = new Facilities(this);
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.noOfAdults = noOfAdults;
        this.noOfChildren = noOfChildren;
        this.numberOfSingleBeds = singleBeds;
        this.numberOfDoubleBeds = doubleBeds;
        this.numberOfBabyBeds = babyBeds;
        this.disabledRoom = disabled;
        this.price = price;
    }

    //public void setFacilities(Facilities facilities) {
       // this.facilities = facilities;
    //}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getNumberOfSingleBeds() {
        return numberOfSingleBeds;
    }

    public void setNumberOfSingleBeds(int numberOfSingleBeds) {
        this.numberOfSingleBeds = numberOfSingleBeds;
    }

    public int getNumberOfDoubleBeds() {
        return numberOfDoubleBeds;
    }

    public void setNumberOfDoubleBeds(int numberOfDoubleBeds) {
        this.numberOfDoubleBeds = numberOfDoubleBeds;
    }

    public int getNumberOfBabyBeds() {
        return numberOfBabyBeds;
    }

    public void setNumberOfBabyBeds(int numberOfBabyBeds) {
        this.numberOfBabyBeds = numberOfBabyBeds;
    }

    public boolean isDisabledRoom() {
        return disabledRoom;
    }

    public void setDisabledRoom(boolean disabledRoom) {
        this.disabledRoom = disabledRoom;
    }

   // public Facilities getFacilities() {
   //     return facilities;
   // }

    public int getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public int getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }
    
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addBooking(Booking booking) {
        if (!bookings.contains(booking)) {
            bookings.add(booking);
        }
    }

    public void removeBooking(Booking booking) {
        if (bookings.contains(booking)) {
            bookings.remove(booking);
        }
    }
}
