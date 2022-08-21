package com.flyhigh.flightsearch.repository;

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 8/21/2022.
 */
@Repository
public interface FlightSearchRepo extends JpaRepository<Flights, String> {

    List<Flights> findByOriginAndDestination(String origin,String destination);
}
