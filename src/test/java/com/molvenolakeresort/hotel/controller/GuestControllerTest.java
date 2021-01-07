package com.molvenolakeresort.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.molvenolakeresort.hotel.model.Guest;
import com.molvenolakeresort.hotel.repository.GuestRepository;
import org.jboss.jandex.JandexAntTask;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
public class GuestControllerTest {
	@InjectMocks
	private GuestController guestController;

	@Mock
	private GuestRepository guestRepository;

	private MockMvc mockMvc;

	private Guest guest;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
	}

	@Test
	public void testGetGuestList() throws Exception {
		List<Guest> guests = new ArrayList<>();

		guests.add(new Guest("Piet"));
		guests.add(new Guest("Klaas"));

		when(guestRepository.findAll()).thenReturn(guests);


		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/guests"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(guests.get(0).getName()))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}


	@Test
	public void testGetGuestById() throws Exception {
//		List<Guest> guests = new ArrayList<>();

		Guest guest = new Guest("Piet");
		guest.setId(1);

		when(guestRepository.findById((long)1)).thenReturn(Optional.of(guest));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(guest.getName()))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void addGuestTest() throws Exception {
		Guest newGuest = new Guest("Piet");
		when(guestRepository.save(ArgumentMatchers.any(Guest.class))).thenReturn(newGuest);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(newGuest);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/guests")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newGuest.getName()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}


//	GuestController guestController;
//	Iterable<Guest> guestList = guestController.findAll();
//
//	@BeforeEach
//	void init() {
//		guestController = new GuestController();
//
////		TEST GUEST LIST :
//		guestController.postGuest(new Guest("Jan Janssen"));
//		guestController.postGuest(new Guest("Alice"));
//		guestController.postGuest(new Guest("Bob"));
//	}
//
//	@Test
//	void findById() throws EntityNotFoundException {
//		// Gastinformatie ophalen:
//			System.out.println("Informatie wordt opgehaald van guest met ID number 1...\n");
//			Guest guest;
//			guest = guestController.findById(1).getBody();
//			System.out.println("Gastinformatie: \nNaam: " + guest.getName() + "\nGeboortedatum: " + guest.getBirthDate() + "\nStad: " + guest.getCity() + "\nBoekingen: " + guest.getBookings().toArray().length);
//
//			assertEquals("Jane Doe", guestController.findById(3).getBody().getName());
//	}
//
//	@Test
//	void getGuestList() {
//		System.out.println("Gastenlijst wordt opgehaald...");
//		for (Guest guest : guestList) {
//			System.out.println(guest.getId() + ". " + guest.getName() + ", " + guest.getCity() + ", " + guest.getBookings().toArray().length + " boekingen.");
//		}
//		List<Guest> newList = ((List<Guest>)guestList);
//		assertEquals();
//	}
//
//	@Test
//	void postGuest() {
//		try {
//			System.out.println("Registreer nieuwe gast: Jane Appleseed, geboren in 2002, afkomstig uit New York. \n");
//			guestController.postGuest(new Guest("Jane Appleseed", "January 24th, 2002", "Jane@email.com", "0316 - 23454321", "AB1234DE", "Chicago Street", "New York"));
//
//			System.out.print("Nieuwe gast geregistreerd. ");
////			Guest guest = guestController.getGuest(4);
////			System.out.println("Gastinformatie: \nNaam: " + guest.getName() + "\nGeboortedatum: " + guest.getBirthDate() + "\nStad: " + guest.getCity() + "\nBoekingen: " + guest.getBookings().length);
////			System.out.println(guestController.getGuest(4).toString());
//
//			System.out.println("\nNieuwe gastenlijst:");
////			for (Guest guestIterator : guestController.getGuestList()) {
////				System.out.println(guestIterator.getId() + ". " + guestIterator.getName() + ", " + guestIterator.getCity() + ", " + guestIterator.getBookings().length + " boekingen.");
////			}
////			assertEquals("Jane Appleseed", guestController.getGuestList().get(3).getName());
////		} catch (EntityNotFoundException e) {
////			assertEquals(e.toString(),"");
////		}
////	}
//
//	@Test
//	void putGuest() {
//		try {
//			System.out.println("Current guest (ID no. 2):");
//			System.out.println(guestController.getGuest(2).toString());
//			System.out.println("\nUpdating guest and requesting new information...\n");
//
//			guestController.putGuest(2,"Alice Smith", "February 20th, 1990", "asmith@email.com", "031-12345678", "", "Veemarkt 1, 5678 CD", "");
//			System.out.println("\n" + guestController.getGuest(2).toString());
//
//			assertEquals(guestController.getGuest(2).getName(), "Alice Smith");
//
//		} catch (EntityNotFoundException e) {
//			assertEquals(e.toString(), "");
//		}
//	}
//
//	@Test
//	void deleteGuest() {
//		try {
//			System.out.println("Guest to be deleted (ID no. 3):");
//			System.out.println(guestController.getGuest(3).toString());
//
//			System.out.println("\nDeleting guest... Requesting guest information again: ");
//			guestController.deleteGuest(3);
//			System.out.println(guestController.getGuest(3).toString());
//
//		} catch (EntityNotFoundException e) {
//			System.out.println(e);
//			assertEquals("com.molvenolakeresort.hotel.controller.EntityNotFoundException: Guest was not found for ID: 3",e.toString());
//		}
//
//	}
}
