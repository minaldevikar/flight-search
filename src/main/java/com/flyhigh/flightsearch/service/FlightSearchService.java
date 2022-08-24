package com.flyhigh.flightsearch.service;

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.payload.FlightPojo;

import java.util.List;

/**
 * Created by Admin on 8/21/2022.
 */
public interface FlightSearchService {

    public List<FlightPojo> getAllFlightsSortedView(String sortBy, String sortType);

    public List<FlightPojo> fetchRequiredFlights(String origin,String destination, String sortBy);

}
