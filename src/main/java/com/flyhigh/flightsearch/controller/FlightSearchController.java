package com.flyhigh.flightsearch.controller;

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.service.FlightSearchService;
import com.flyhigh.flightsearch.util.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Admin on 8/20/2022.
 */
@RestController
@RequestMapping("v1/flights")
@Validated
public class FlightSearchController {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchController.class);

    @Autowired
    private FlightSearchService flightSearchService;

    @Autowired
    private ErrorResponse errorResponse;

    /*@GetMapping()
    public ResponseEntity<List<Flights>> getAllFlights() {
        return (ResponseEntity<List<Flights>>) flightSearchService.getAllFlights();
    }*/

    @GetMapping()
    public ResponseEntity<?> getAllFlights() {
        try{
                        return new ResponseEntity<List<Flights>>(flightSearchService.getAllFlights(), HttpStatus.OK);
        }
        catch(Exception e){
            errorResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        //return flightSearchService.getAllFlights();
    }

    @GetMapping(value = "/{origin}/{destination}")
    public ResponseEntity<?> fetchRequiredFlights(
            @PathVariable("origin")
            @NotBlank(message="Origin required")
            @Size(max = 3, message = "Please provide 3 digit airport code")
            @Pattern(regexp="^[a-zA-Z]*$",message="Acceptable format: a-zA-Z")
                    String origin,
            @PathVariable("destination")
            @NotBlank(message="Destination required")
            @Size(max = 3, message = "Please provide 3 digit airport code")
            @Pattern(regexp="^[a-zA-Z]*$",message="Acceptable format: a-zA-Z")
                    String destination){

        try{
            /*if (origin.isEmpty() || destination.isEmpty() || origin.length()!=3 || destination.length()!=3){
                errorResponse.setMessage("Please provide 3 digit airport code");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            else if (origin.matches("[a-zA-Z]") || destination.matches("[a-zA-Z]")) {
                errorResponse.setMessage("Only characters allowed");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            else*/
                return new ResponseEntity<List<Flights>>(flightSearchService.fetchRequiredFlights(origin,destination), HttpStatus.OK);
        }
        catch (Exception e){
            errorResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/sort")
    public ResponseEntity<?> sortFlights (@RequestParam(name = "sortParameter", required = true) String sortParameter,
                                           @RequestParam(name= "sortType",required = false) String sortType) {
        try{
            return new ResponseEntity<List<Flights>>(flightSearchService.sortFlights(sortParameter,sortType), HttpStatus.OK);
        }
        catch(Exception e){
            errorResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        //return flightSearchService.getAllFlights();
    }




}
