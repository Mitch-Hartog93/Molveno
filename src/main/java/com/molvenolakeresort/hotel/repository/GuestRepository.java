package com.molvenolakeresort.hotel.repository;

import com.molvenolakeresort.hotel.model.Guest;
import com.molvenolakeresort.hotel.model.Room;
import com.molvenolakeresort.hotel.model.RoomType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends CrudRepository<Guest,Long> {
	
	Optional<Guest> findByName(String name);
}