package com.molvenolakeresort.hotel.repository;

import com.molvenolakeresort.hotel.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

// BEGINdate < ENDdate, ENDdate > BEGINdate

}
