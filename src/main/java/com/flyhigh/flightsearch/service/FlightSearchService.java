package com.flyhigh.flightsearch.service;

import com.flyhigh.flightsearch.entity.Flights;

import java.util.List;

/**
 * Created by Admin on 8/21/2022.
 */
public interface FlightSearchService {

    public List<Flights> getAllFlightsSortedView(String sortBy,String sortType);

    public List<Flights> fetchRequiredFlights(String origin,String destination, String sortBy);

}
