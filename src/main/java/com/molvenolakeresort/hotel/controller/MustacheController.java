package com.molvenolakeresort.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MustacheController {
	
	@GetMapping
	public String getHome() {
		return "index";
	}
	
	@GetMapping ("index")
	public String getHomeIndex() {
		return "index";
	}
	
	@GetMapping("/public/rooms/room-overview")
	public String getRoomOverview() {
		return "/public/rooms/room-overview";
	}

	@GetMapping("/public/rooms/book-room")
	public String getBookRoom() {
		return "/public/rooms/book-room";
	}
	
	@GetMapping("/admin")
	public String getEmployeeIndex() {
		return "/admin/index";
	}
	
	@GetMapping("/admin/guests/guests")
	public String getGuests() {
		return "/admin/guests/guests";
	}

	@GetMapping("/admin/rooms/rooms")
	public String getRooms() {
		return "/admin/rooms/rooms";
	}

	@GetMapping("/admin/bookings/bookings")
	public String getBookings() {
		return "/admin/bookings/bookings";
	}
	
}
