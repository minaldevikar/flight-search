package com.flyhigh.flightsearch.controller;


import com.flyhigh.flightsearch.exception.EndpointNotDefinedException;
import com.flyhigh.flightsearch.service.FlightSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 8/20/2022.
 */
@RestController
public class FlightSearchController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchController.class);
    private final String apiVersion = "/v1/flights";

    @Autowired

    private FlightSearchService flightSearchService;

    @GetMapping(value = {"/error", "/{v1}/*", apiVersion + "/{origin}"})
    public ResponseEntity<?> endPointNotDefined() {
        logger.info("Inside /error");
        throw new EndpointNotDefinedException();
    }

    @GetMapping(value = apiVersion)
    public ResponseEntity<?> getAllFlightsSortedView(@RequestParam(name = "sortBy", defaultValue = "flightId") String sortBy,
                                                     @RequestParam(name = "sortType", required = false, defaultValue = "asc") String sortType) {
        logger.info("Inside getAllFlightsSortedView...sort by: " + sortBy + "and sort type: " + sortType);
        return new ResponseEntity<>(flightSearchService.getAllFlightsSortedView(sortBy, sortType), HttpStatus.OK);
    }

    @GetMapping(value = apiVersion + "/{origin}/{destination}")
    public ResponseEntity<?> fetchRequiredFlights(
            @PathVariable String origin,
            @PathVariable String destination,
            @RequestParam(name = "sortBy", defaultValue = "arrival") String sortBy) {
        return new ResponseEntity<>(flightSearchService.fetchRequiredFlights(origin, destination, sortBy), HttpStatus.OK);
    }
}
