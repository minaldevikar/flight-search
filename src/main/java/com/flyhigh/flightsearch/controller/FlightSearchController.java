package com.flyhigh.flightsearch.controller;


import com.flyhigh.flightsearch.exception.EndpointNotDefinedException;
import com.flyhigh.flightsearch.service.FlightSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Admin on 8/20/2022.
 */
@RestController
public class FlightSearchController {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchController.class);
    private final String apiVersion = "/v1/flights";
    @Autowired
    private FlightSearchService flightSearchService;

    //Use case - 1: User can be able to find list of flights from Origin - Destination
    @GetMapping(value = { apiVersion + "/{origin}", apiVersion + "/{origin}/{destination}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchRequiredFlights(
            @PathVariable String origin,
            @PathVariable String destination,
            @RequestParam(name = "sortBy", required = false, defaultValue = "arrival") String sortBy,
            @RequestParam(name = "sortType", required = false, defaultValue = "asc") String sortType) {
        logger.info("Inside fetchRequiredFlights...origin: " + origin + "and destination: " + destination);
        return new ResponseEntity<>(flightSearchService.fetchRequiredFlights(origin, destination, sortBy, sortType), HttpStatus.OK);
    }

    //Use Case - 2: User can be able to sort list flights based on departure time, arrival time , price , travel time etc
    @GetMapping(value = apiVersion, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFlightsSortedView(@RequestParam(name = "sortBy", defaultValue = "flightId") String sortBy,
                                                     @RequestParam(name = "sortType", required = false, defaultValue = "asc") String sortType) {
        logger.info("Inside getAllFlightsSortedView...sort by: " + sortBy + "and sort type: " + sortType);
        return new ResponseEntity<>(flightSearchService.getAllFlightsSortedView(sortBy, sortType), HttpStatus.OK);
    }


    @GetMapping(value = {"/error", "/{v1}/*"})
    public ResponseEntity<?> endPointNotDefined() {
        logger.info("Inside /error");
        throw new EndpointNotDefinedException();
    }
}
