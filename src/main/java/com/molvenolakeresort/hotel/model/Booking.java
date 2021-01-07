package com.molvenolakeresort.hotel.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {
    //static int guestIDgenerator = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private int totalGuests;
    private Status status;
    @Column(name = "check_in_date")
    private Date checkInDate;
    @Column(name = "check_out_date")
    private Date checkOutDate;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "guestID")
    private Guest guest;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name = "booked_rooms",
            joinColumns = {
                @JoinColumn(name = "booking_id", referencedColumnName = "id"),
//                @JoinColumn(name = "check_in_date", referencedColumnName = "check_in_date"),
//                @JoinColumn(name = "check_out_date", referencedColumnName = "check_out_date"),
            },
            inverseJoinColumns = {
            @JoinColumn(name = "room_id", referencedColumnName = "id"),
//            @JoinColumn(name = "room_type", referencedColumnName = "room_type")
            })
    private List<Room> bookedRooms;

    @OneToOne
    private Invoice invoice;

//    @Override
//    public String toString(){
//        String rooms = printRooms(bookedRooms);
//        return  " Guest: " + guest.getName() + " Total Guests: " + totalGuests + " Check in date : "
//                + checkInDate.getDate()+ "-" + checkInDate.getMonth()+ "-"  + (checkInDate.getYear()+1900)
//                + " Check out date: " + checkOutDate.getDate()+ "-" + checkOutDate.getMonth()+ "-"  + (checkOutDate.getYear()+1900)
//                + " Status: " + status + "rooms: " +  rooms;
//    }

    private String printRooms(List<Room> bookedRooms) {
        String rooms = "";
        for(Room r :bookedRooms ){
            rooms = rooms.concat(r.getRoomNumber() + ", ");
        }
        return rooms;
    }

    public Booking() {
    }
    
    public Booking(int totalGuests, Status status, String checkInDate, String checkOutDate) throws ParseException {
        this.totalGuests = totalGuests;
        this.status = status;
        this.checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkInDate);
        this.checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkOutDate);
    }

    public Booking(Guest guest, List<Room> bookedRoom) throws ParseException {
        //this.bookingNumber = guestIDgenerator++;
        this.guest = guest;
        this.totalGuests = 3;
        this.bookedRooms = bookedRoom;
        this.status = Status.booked;
        this.checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2020");
        this.checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse("19/05/2020");
        //this.roomBooking = ;
    }

    public Booking(Guest guest, int totalGuests, List<Room> bookedRooms, String checkInDate, String checkOutDate) throws ParseException {
        //this.bookingNumber = guestIDgenerator++;
        this.guest = guest;
        this.totalGuests = totalGuests;
        this.bookedRooms = bookedRooms;
        this.status = Status.booked;
        this.checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkInDate);
        this.checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkOutDate);
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getTotalGuests() {
        return totalGuests;
    }

    public void setTotalGuests(int totalGuests) {
        this.totalGuests = totalGuests;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Room> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(List<Room> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    
    public void addRoom(Room room) {
        if (!bookedRooms.contains(room)) {
            bookedRooms.add(room);
        }
    }
    
    public void removeBooking(Room room) {
        if (bookedRooms.contains(room)) {
            bookedRooms.remove(room);
        }
    }
    
    
    // for one-to-many associations: https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() !=obj.getClass()) return false;
        Booking booking = (Booking) obj;
        return id == booking.id;
    }
}
