package com.molvenolakeresort.hotel.repository;

import com.molvenolakeresort.hotel.model.Room;
import com.molvenolakeresort.hotel.model.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
	List<Room> findByAvailable(boolean available);
	
	List<Room> findByRoomType(RoomType roomType);
	
	Optional<Room> findByRoomNumber(String roomNumber);

	
//	QUERY EXAMPLE to add date into method (to find available rooms by check in / check out date):
//	@Query("SELECT b FROM Booking b WHERE Date(checkIn) <= 2020-02-20")
//	public List<Room> find(@Param("checkIn") Date checkIn, @Param("checkOut") Date checkOut);
	
//	@Query("SELECT booking.id, room.id, booking.check_in_date, booking.check_out_date, room.room_type " +
//					"FROM room " +
//					"JOIN booking_booked_rooms ON room.id = booking_booked_rooms.booked_rooms_id " +
//					"JOIN booking ON booking.id = booking_booked_rooms.booking_id " +
//					"WHERE :newCheckIn < booking.check_out_date AND :newCheckOut > booking.check_in_date" +
//					"AND room_type = :roomtype")
//List<Room> findRoomsByDatesAndRoomtype(@Param("newCheckIn") Date newCheckIn,
//									   @Param("newCheckOut") Date newCheckOut,
//									   @Param("roomtype") RoomType roomType);
//
// }
	
//	@Query(value = "SELECT booked_rooms.booking_id, booked_rooms.room_id, booking.check_in_date, booking.check_out_date, room.room_type FROM room JOIN booked_rooms ON room.id = booked_rooms.room_id JOIN booking ON booked_rooms.booking_id = booking.id;", nativeQuery = true)
	@Query("SELECT DISTINCT r FROM Room r INNER JOIN r.bookings b where :newCheckIn < b.checkOutDate AND :newCheckOut > b.checkInDate AND r.roomType = :roomType")
	public List<Room> findBookedRooms(@Param("newCheckIn") Date newCheckIn, @Param("newCheckOut") Date newCheckOut, @Param("roomType") RoomType roomType);

}