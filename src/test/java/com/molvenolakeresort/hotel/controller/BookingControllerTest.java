/*
package com.molvenolakeresort.hotel.controller;

import com.molvenolakeresort.hotel.model.Booking;
import com.molvenolakeresort.hotel.model.Guest;
import com.molvenolakeresort.hotel.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerTest {
    private BookingController bookingController;
    private RoomController roomController;
    private GuestController guestcontroller;

    @BeforeEach
    void init() throws EntityNotFoundException, ParseException {
        bookingController = new BookingController();
        roomController = new RoomController();
        guestcontroller = new GuestController();
        Room[] rooms1 = roomController.getRoomList().subList(0,2).toArray(new Room[0]);
        Room[] rooms2 = roomController.getRoomList().subList(2,3).toArray(new Room[0]);
        Room[] rooms3 = roomController.getRoomList().subList(3,4).toArray(new Room[0]);

        List<Guest> guests = guestcontroller.getGuestList();

        bookingController.postBooking(new Booking( guests.get(0), 4, rooms1,"12/02/2020" , "20/02/2020"));
        bookingController.postBooking(new Booking( guests.get(1), 1, rooms2, "20/03/2020", "23/02/2020"));
        bookingController.postBooking(new Booking( guests.get(2),2, rooms3, "06/07/2020", "15/07/2020"));
}

    @Test
    void getBooking() throws EntityNotFoundException {
        try {
            Booking booking = bookingController.getBooking(1);

            System.out.println("Boeking 1 wordt opgehaald.");
            assertEquals("101", booking.getBookedRooms()[0].getRoomNumber());
            assertEquals(1, booking.getBookingNumber());
            assertEquals(1, booking.getGuest().getGuestID());
            System.out.println("De boeking die is opgehaald heeft kamernummer " + booking.getBookedRooms()[0].getRoomNumber()
                    + ", geboekt door de klant " + booking.getGuest().getName());

        }
     catch(EntityNotFoundException e) {
        System.out.println(e);
        assertEquals(e.toString(), "");
    }
    }


    @Test
    void getBookings() {
        System.out.println("Alle boekingen worden opgehaald.");
        List<Booking> bookingList = bookingController.GetBookings();
        assertEquals(3, bookingList.size());
        if(!bookingList.isEmpty()) {
            System.out.println("Lijst van Boekingen:");
            for(Booking booking : bookingList) {
                System.out.println(booking.toString());
            }
        }
    }

    @Test
    void putBooking() throws EntityNotFoundException {
        System.out.println("Boeking van Jan Janssen wordt opgehaald. ");
        Booking booking = bookingController.getBooking(1);
        Guest guest = new Guest("Niet Jan");
        System.out.println(booking.toString());
        System.out.println("Boeking van Jan Janssen wordt gewijzigd. Gast wordt gewijzigd en booking voor kamer 102 wordt geannuleerd. ");
        Room[] list = booking.getBookedRooms();
        Room[] NList = {list[0]};
        booking.setBookedRooms(NList);
        booking.setGuest(guest);

        bookingController.putBooking(booking);
        Booking NBooking = bookingController.getBooking(1);

        assertEquals("Niet Jan", NBooking.getGuest().getName() );
        assertEquals(1, NBooking.getBookedRooms().length );
        System.out.println(NBooking.toString());

    }

    @Test
    void postBooking() throws ParseException {
        System.out.println("Er wordt een nieuwe booking aangemaakt voor Willem Alexander");
        Guest guest = new Guest("Willem Alexander");
        Room[] rooms = roomController.getRoomList().toArray(new Room[0]);

        Booking nBooking = new Booking(guest,8 ,rooms,"18/12/2020","23/12/2020");
        bookingController.postBooking(nBooking);


        List<Booking> bookingList= bookingController.GetBookings();
        assertEquals(4, bookingList.size());
        if(!bookingList.isEmpty()) {
            System.out.println("Lijst van Boekingen:");
            for(Booking booking : bookingList) {
                System.out.println(booking.toString());
            }
        }
    }

    @Test
    void deleteBooking() {
        System.out.println("Boeking van Jan Janssen wordt verwijderd.");

        try {
            bookingController.deleteBooking(1);
        } catch(EntityNotFoundException e) {
            System.out.println(e);
            assertEquals(e.toString(), "");
        }

        List<Booking> list= bookingController.GetBookings();
        assertEquals(2, list.size());
        if(!list.isEmpty()) {
            System.out.println("Lijst van boekingen:");
            for(Booking booking : list) {
                System.out.println(booking.toString());
            }
        }
    }
}
*/