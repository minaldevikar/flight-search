package com.flyhigh.flightsearch.controller;


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
@RequestMapping("v1/flights")
public class FlightSearchController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchController.class);

    @Autowired
    private FlightSearchService flightSearchService;

    @GetMapping()
    public ResponseEntity<?> getAllFlightsSortedView(@RequestParam(name = "sortBy", defaultValue = "flightId") String sortBy,
    @RequestParam(name= "sortType",required = false,defaultValue = "asc") String sortType) {
        logger.info("Inside getAllFlightsSortedView...sort by: "+sortBy+"and sort type: "+sortType);
            try{
                return new ResponseEntity<>(flightSearchService.getAllFlightsSortedView(sortBy,sortType), HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @GetMapping(value = "/{origin}/{destination}")
    public ResponseEntity<?> fetchRequiredFlights(
            @PathVariable String origin,
            @PathVariable String destination,
            @RequestParam(name = "sortBy",defaultValue = "fare") String sortBy){
            try{
                return new ResponseEntity<>(flightSearchService.fetchRequiredFlights(origin,destination,sortBy), HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

}
