package com.molvenolakeresort.hotel.controller;

import com.molvenolakeresort.hotel.model.Room;
import com.molvenolakeresort.hotel.model.RoomType;
import com.molvenolakeresort.hotel.repository.RoomRepository;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RoomControllerTest {
    @InjectMocks
    private RoomController roomController;

    @Mock
    private RoomRepository roomRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }

    @Test
    public void getSingleRoomById() throws Exception {
        //String roomNumber, RoomType roomType, int noOfAdults, int noOfChildren, int singleBeds, int doubleBeds, int babyBeds, boolean disabled, int price
        Room room = new Room("100", RoomType.doubleRoom,2,0,0,1,1, false, 500);
        room.setId(15);
        when(roomRepository.findById((long)15)).thenReturn(java.util.Optional.of(room));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomNumber").value(room.getRoomNumber()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    public void getRoomList() {
//        List<Room> list = new ArrayList<>();
//
//        //Copy whole roomController list to temporary list to test length
//        for(Room room : roomController.getRoomList()) {
//            list.add(room);
//        }
//
//        assertEquals(4, list.size());
//        if(!list.isEmpty()) {
//            System.out.println("Lijst van kamers:");
//            for(Room room : list) {
//                System.out.println("Kamer " + room.getRoomNumber() + " is " + (room.isAvailable() ? "Available" : "Not available."));
//            }
//        }
//    }
//
//    @Test
//    void getAvailableRooms() {
//        List<Room> list = new ArrayList<>();
//
//        //Copy whole roomController list to temporary list to test length
//        for(Room room : roomController.getAvailableRooms().getBody()) {
//            list.add(room);
//        }
//
//        assertEquals(3, list.size());
//        if(!list.isEmpty()) {
//            System.out.println("Lijst van beschikbare kamers:");
//            for(Room room : list) {
//                System.out.println("Kamer " + room.getRoomNumber() + " is " + (room.isAvailable() ? "Available" : "Not available."));
//            }
//        }
//    }
//
//    @Test
//    public void putRoom() {
//        //Modify a room
//        Room room = roomController.getRoom(1).getBody();
//        System.out.println("Kamer met nummer " + room.getRoomNumber() + " wordt aangepast naar kamer 105");
//        room.setRoomNumber("105");
//        roomController.putRoom(room);
//        assertEquals("105", roomController.getRoom(1).getBody().getRoomNumber());
//
//        List<Room> list = new ArrayList<>();
//
//        //Copy whole roomController list to temporary list to test length
//        for(Room roomIt : roomController.getRoomList()) {
//            list.add(roomIt);
//        }
//
//        if(!list.isEmpty()) {
//            System.out.println("Lijst van kamers:");
//            for(Room roomIt : list) {
//                System.out.println("Kamer " + roomIt.getRoomNumber() + " is " + (roomIt.isAvailable() ? "Available" : "Not available."));
//            }
//        }
//    }
//
//    @Test
//    public void postRoom() {
//        System.out.println("Nieuwe kamer toevoegen:");
//        Room room = new Room();
//        room.setNumberOfSingleBeds(1);
//        room.setRoomNumber("201");
//        room.setAvailable(false);
//        roomController.postRoom(room);
//
//        List<Room> list= roomController.getRoomList();
//        assertEquals(5, list.size());
//        if(!list.isEmpty()) {
//            System.out.println("Lijst van kamers:");
//            for(Room roomIt : list) {
//                System.out.println("Kamer " + roomIt.getRoomNumber() + " is " + (roomIt.isAvailable() ? "Available" : "Not available."));
//            }
//        }
//    }
//
//    @Test
//    public void deleteRoom() {
//        System.out.println("Kamer 101 wordt verwijderd...");
//
//        try {
//            Room room = roomController.getRoom("101");
//            roomController.deleteRoom(room.getId());
//        } catch(EntityNotFoundException e) {
//            System.out.println(e);
//            assertEquals(e.toString(), "");
//        }
//
//        List<Room> list= roomController.getRoomList();
//        assertEquals(3, list.size());
//        if(!list.isEmpty()) {
//            System.out.println("Lijst van kamers:");
//            for(Room roomIt : list) {
//                System.out.println("Kamer " + roomIt.getRoomNumber() + " is " + (roomIt.isAvailable() ? "Available" : "Not available."));
//            }
//        }
//    }
}
