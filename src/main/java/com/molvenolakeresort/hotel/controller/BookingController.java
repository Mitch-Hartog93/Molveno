package com.molvenolakeresort.hotel.controller;

import com.molvenolakeresort.hotel.model.*;
import com.molvenolakeresort.hotel.repository.BookingRepository;
import com.molvenolakeresort.hotel.repository.GuestRepository;
import com.molvenolakeresort.hotel.repository.RoomRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.xml.ws.Response;
import java.awt.print.Book;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bookings")
public class BookingController {
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public BookingController(){
    }

    @GetMapping("{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable long id) throws EntityNotFoundException {
        Optional<Booking> optionalBooking =  bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            System.out.println("Booking guest: " + booking.getGuest());
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Iterable<Booking> getBookings(){
        Iterable<Booking> booking = bookingRepository.findAll();
        return booking;
    }

    @GetMapping("{id}/guest")
    public ResponseEntity<Guest> getBookingGuest(@PathVariable long id) throws EntityNotFoundException {
        Optional<Booking> optionalBooking =  this.bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            if (booking.getGuest().getName() == null) {
                Guest foundGuest = this.guestRepository.findById(booking.getGuest().getId()).get();
                booking.setGuest(foundGuest);
                this.bookingRepository.save(booking);
            }

            return ResponseEntity.ok(booking.getGuest());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/rooms")
    public ResponseEntity<List> getBookingRooms(@PathVariable long id) throws EntityNotFoundException {
        Optional<Booking> optionalBooking = this.bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            if (booking.getBookedRooms().size() != 0) {
                List<Room> foundRooms = new ArrayList<Room>();
                for (Room room : booking.getBookedRooms()) {
                    Room foundRoom = this.roomRepository.findById(room.getId()).get();
                    foundRooms.add(foundRoom);
                }
                return ResponseEntity.ok(foundRooms);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Booking> postBooking(@RequestBody Booking booking){
    
//        Check if booking guest info is entered, and retrieve existing guest information:
        if (booking.getGuest() != null) {
            Optional<Guest> optionalGuest = this.guestRepository.findById(booking.getGuest().getId());
            Optional<Guest> optionalGuestName = this.guestRepository.findByName(booking.getGuest().getName());

            if (optionalGuest.isPresent()) {
                Guest foundGuest = optionalGuest.get();
                booking.setGuest(foundGuest);
            } else if (optionalGuestName.isPresent()){
                Guest foundGuestName = optionalGuestName.get();
                booking.setGuest(foundGuestName);
            } else {
                booking.setGuest(booking.getGuest());
            }
        }

//         Check if booking rooms are entered, and retrieve existing room information
        if(booking.getBookedRooms().size() > 0) {
            List<Room> foundRooms = new ArrayList<Room>();

            for (Room room : booking.getBookedRooms()) {
                Optional<Room> optionalRoom = this.roomRepository.findById(room.getId());
                Optional<Room> optionalRoomNumber = this.roomRepository.findByRoomNumber(room.getRoomNumber());
                if (optionalRoom.isPresent()) {
                    Room foundRoom = optionalRoom.get();
                    foundRooms.add(foundRoom);
                } else if (optionalRoomNumber.isPresent()) {
                    Room foundRoomNumber = this.roomRepository.findById(optionalRoomNumber.get().getId()).get();
                    foundRooms.add(foundRoomNumber);
                } else {
                    foundRooms.add(room);
                }
            }
            booking.setBookedRooms(foundRooms);
        }
        else {
            booking.setBookedRooms(null);
        }

        // In the end, save booking:
            this.bookingRepository.save(booking);
            return ResponseEntity.ok(booking);
        }


    @DeleteMapping()
    public ResponseEntity<?> deleteBooking(@RequestBody Booking booking) {
//        if (booking.getGuest() != null) {
//            booking.getGuest().removeBooking(booking);
//        }
//        if (booking.getBookedRooms() != null) {
//            for (Room room : booking.getBookedRooms()) {
//                room.removeBooking(booking);
//            }
//        }
        bookingRepository.delete(booking);
        return ResponseEntity.ok(booking);
    }

//    @PostConstruct
//    public void init() throws ParseException {
//
//        this.guestRepository.save(new Guest("Jane Doe", "31-01-1992", "janedoe@email.com", "+31 6 1234 5678",
//				"AB9381B39", "Main Street 99", "New York"));
//        this.guestRepository.save(new Guest("Jan Janssen", "02-10-1990", "jjanssen@email.com", "+31 72 5712345",
//				"KH9274027", "Dorpsstraat 83", "Dordrecht"));
//		this.guestRepository.save(new Guest("Nicholas Wiley", "09-01-1959", "nicwiley@email.com", "042 2934813",
//				"IT392K382", "Ellsworth Summit", "Howemouth"));
//		this.guestRepository.save(new Guest("Ervin Howell", "19-05-1978", "ervinh@email.com", "010 9320 592",
//				"ABE382915", "Victor Plains 391", "Gwenborough"));
//        this.guestRepository.save(new Guest("Patricia Lebsack", "29-11-1993", "patleb@email.com", "063 298 492143",
//				"IW938G913", "Hager Mall", "Corkshire"));//
//
//		roomRepository.save(new Room("101", RoomType.singleRoom,1,1,1,0,1,false,849));
//		roomRepository.save(new Room("102", RoomType.doubleRoom, 2, 1, 2, 0, 1,  false,1199));
//		roomRepository.save(new Room("103", RoomType.familyRoom, 4, 1,2,1,1,false,1649));
//		Room newRoom = new Room("104", RoomType.penthouse, 8, 2, 4, 2, 2, false,2399);
//		newRoom.setAvailable(false);
//		roomRepository.save(newRoom);
//		roomRepository.save(new Room("105", RoomType.singleRoom, 1, 1,1,0,1,false,1199));
//		roomRepository.save(new Room("106", RoomType.singleRoom,1,1,1,0,1,false,849));
//		roomRepository.save(new Room("107", RoomType.doubleRoom, 2, 1, 2, 0, 1,  false,1199));
//		roomRepository.save(new Room("108", RoomType.familyRoom, 4, 1,2,1,1,false,1649));
//		roomRepository.save(new Room("109", RoomType.singleRoom, 1, 1,1,0,1,false,1199));
//		roomRepository.save(new Room("110", RoomType.penthouse, 8, 2, 4, 2, 2, false,2399));
//
//        bookingRepository.save(new Booking(1, Status.booked, "20/02/2020", "23/02/2020"));
//        bookingRepository.save(new Booking(2, Status.booked, "26/02/2020", "28/02/2020"));
//        bookingRepository.save(new Booking(1, Status.booked, "21/02/2020", "25/02/2020"));
//        bookingRepository.save(new Booking(3, Status.booked, "18/02/2020", "22/02/2020"));
//
//    }

}
