package com.molvenolakeresort.hotel.controller;

import com.molvenolakeresort.hotel.model.Booking;
import com.molvenolakeresort.hotel.model.Guest;
import com.molvenolakeresort.hotel.model.Room;
import com.molvenolakeresort.hotel.model.RoomType;
import com.molvenolakeresort.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/rooms")
public class RoomController {
	@Autowired
	RoomRepository roomRepository;

	public RoomController() {

	}

	//Get one Room object with the id of the Room
	@GetMapping("{id}")
	public ResponseEntity<Room> getRoom(@PathVariable long id) {
		Optional<Room> foundRoom = roomRepository.findById(id);
		if(foundRoom.isPresent()) {
			Room room = foundRoom.get();
			return ResponseEntity.ok(room);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public Iterable<Room> getRoomList() {
		return roomRepository.findAll();
	}

	@GetMapping(value = "/availableRoomList")
	public ResponseEntity<List<Room>> getAvailableRooms() {
		List<Room> availableRooms = roomRepository.findByAvailable(true);
		return ResponseEntity.ok(availableRooms);
	}
	
	@GetMapping("/roomtype/{roomType}")
	public ResponseEntity<?> getRoomTypeRooms(@PathVariable String roomType) {
		
		if (roomType.equals("single")) {
			List<Room> roomList = roomRepository.findByRoomType(RoomType.singleRoom);
			return ResponseEntity.ok(roomList);
		} else if (roomType.equals("double")) {
			List<Room> roomList = roomRepository.findByRoomType(RoomType.doubleRoom);
			return ResponseEntity.ok(roomList);
		} else if (roomType.equals("family")) {
			List<Room> roomList = roomRepository.findByRoomType(RoomType.familyRoom);
			return ResponseEntity.ok(roomList);
		} else if (roomType.equals("penthouse")) {
			List<Room> roomList = roomRepository.findByRoomType(RoomType.penthouse);
			return ResponseEntity.ok(roomList);
		} else {
			String error = "Room type not recognised, roomtype entered: " + roomType;
			return ResponseEntity.badRequest().body(error);
		}
	}

	//	 Get room bookings
	@GetMapping(value="{id}/bookings", produces = "application/json")
	public ResponseEntity<List> getRoomBookings(@PathVariable long id) {
		Optional<Room> optionalRoom = this.roomRepository.findById(id);

		if (optionalRoom.isPresent()) {
			Room room = optionalRoom.get();
			return ResponseEntity.ok(room.getBookings());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("available-rooms")
	public ResponseEntity<List<Room>> getAvailableRooms(@RequestParam("newCheckIn") String newCheckIn, @RequestParam("newCheckOut") String newCheckOut, @RequestParam("roomType") String roomTypeString) throws ParseException {
		Date checkIn = new SimpleDateFormat("yyyy-MM-dd").parse(newCheckIn);
		Date checkOut = new SimpleDateFormat("yyyy-MM-dd").parse(newCheckOut);
		RoomType roomType = RoomType.singleRoom;
		
		if (roomTypeString.equals("single")) {
			roomType = RoomType.singleRoom;
		} else if (roomTypeString.equals("double")) {
			roomType = RoomType.doubleRoom;
		} else if (roomTypeString.equals("family")) {
			roomType = RoomType.familyRoom;
		} else if (roomTypeString.equals("penthouse")) {
			roomType = RoomType.penthouse;
		}
		
		List<Room> bookedRooms = this.roomRepository.findBookedRooms(checkIn, checkOut, roomType);
		
		List<Room> availableRooms = new ArrayList<Room>();
		Iterable<Room> allRooms = this.roomRepository.findByRoomType(roomType);
		
		for (Room room : allRooms) {
			if (!bookedRooms.contains(room)) {
				availableRooms.add(room);
			}
		}
		
		return ResponseEntity.ok(availableRooms);
	}
	

	//Modify existing room with id with all the new info of newRoomInfo
	@PostMapping
	public ResponseEntity<?> postRoom(@RequestBody Room newRoomInfo) {
		List<Room> rooms = (List<Room>) roomRepository.findAll();

		for (Room room : rooms) {
			// check if room number is equal to an existing room number
			if (room.getRoomNumber().equals(newRoomInfo.getRoomNumber())) {
				// check if the equal room numbers are from different rooms (different ID)
				if (room.getId() != newRoomInfo.getId()) {
					String error = "There is already a room with that name.";
					return ResponseEntity.badRequest().body(error);
				}
			}
		}
		
		return ResponseEntity.ok(this.roomRepository.save(newRoomInfo));

	}

	//Delete room
	@DeleteMapping("{id}")
	public void deleteRoom(@PathVariable long id) {
		roomRepository.deleteById(id);
	}


}

