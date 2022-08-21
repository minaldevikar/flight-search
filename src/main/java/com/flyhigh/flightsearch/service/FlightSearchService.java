package com.flyhigh.flightsearch.service;

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.entity.Flights;

import java.util.List;

/**
 * Created by Admin on 8/21/2022.
 */
public interface FlightSearchService {

    public List<Flights> getAllFlights();

    public List<Flights> fetchRequiredFlights(String origin,String destination);

    public List<Flights> sortFlights(String sortParameter,String sortType);
}
