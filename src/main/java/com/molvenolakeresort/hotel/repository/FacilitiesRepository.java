package com.molvenolakeresort.hotel.repository;

import com.molvenolakeresort.hotel.model.Facilities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilitiesRepository extends CrudRepository<Facilities, Long> {
}
