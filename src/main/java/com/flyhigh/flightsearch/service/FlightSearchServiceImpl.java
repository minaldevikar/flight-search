package com.flyhigh.flightsearch.service;


import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.exception.DataNotAvailableException;
import com.flyhigh.flightsearch.exception.EndpointNotDefinedException;
import com.flyhigh.flightsearch.exception.InvalidDataLengthException;
import com.flyhigh.flightsearch.payload.FlightPojo;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Admin on 8/21/2022.
 */
@Service
public class FlightSearchServiceImpl implements FlightSearchService{

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchServiceImpl.class);

    @Autowired
    FlightSearchRepo flightSearchRepo;

    @Override
    public List<FlightPojo> getAllFlightsSortedView(String sortBy,String sortType) {
        logger.info("Inside getAllFlights...sortBy: "+sortBy + "sortType");
        List<FlightPojo> flightPojoList = new ArrayList<>();
        List<Flights> flightsList=flightSearchRepo.findAll(Sort.by(sortBy).ascending());
        if(flightsList.isEmpty()){
            throw new DataNotAvailableException();
        }
        else{
            if(sortType.equals("desc"))
                flightsList=flightSearchRepo.findAll(Sort.by(sortBy).descending());
            flightPojoList=flightsList.stream()
                    .map(flights -> {
                        FlightPojo pojo=new FlightPojo(flights.getFlightId(),flights.getOrigin(),
                                flights.getDestination(),flights.getArrival(),flights.getDeparture(),flights.getprice().toString()+" EURO");
                        //flights.setTravelTime(getTravelTimeDifference(flights.getArrival(),flights.getDeparture()));
                        return pojo;
                    }).collect(Collectors.toList());
        }

        return flightPojoList;
    }

    @Override
    public List<FlightPojo> fetchRequiredFlights(String origin, String destination, String sortBy) {
        logger.info("Inside fetchRequiredFlights with origin: " +origin +" and destination: "+destination +"with sort by "+sortBy);
        if(origin.isEmpty() || destination.isEmpty()){
            throw new EndpointNotDefinedException("Origin and Destination airport code required.");
        }
        if(origin.length()!=3 || destination.length()!=3){
            throw new InvalidDataLengthException();
        }

        List<Flights> flightsList=flightSearchRepo.findByOriginAndDestination(origin,destination);
        if(flightsList.isEmpty())
            throw new DataNotAvailableException();
        else{
            flightsList=flightsList.stream()
                    .map(flights -> {
                        flights.setTravelTime(getTravelTimeDifference(flights.getArrival(),
                                flights.getDeparture()));
                                return flights;
                    }).collect(Collectors.toList());

        }
        switch (sortBy){
            case "departure":
                flightsList=flightsList.stream().sorted(Comparator.comparing(Flights::getDeparture)).collect(Collectors.toList());
                 break;
            case "arrival":
                flightsList=flightsList.stream().sorted(Comparator.comparing(Flights::getArrival)).collect(Collectors.toList());
                 break;
            case "price":
                flightsList=flightsList.stream().sorted(Comparator.comparing(Flights::getprice)).collect(Collectors.toList());
                break;
            case "travelTime":
                flightsList=flightsList.stream().sorted(Comparator.comparing(Flights::getTravelTime)).collect(Collectors.toList());
                break;
            default:
                flightsList=flightsList.stream().sorted(Comparator.comparing(Flights::getFlightId)).collect(Collectors.toList());
        }
        List<FlightPojo> flightPojoList=flightsList.stream()
                .map(flights -> {
                    FlightPojo pojo=new FlightPojo(flights.getFlightId(),flights.getOrigin(),
                            flights.getDestination(),flights.getArrival(),flights.getDeparture(),flights.getprice().toString()+" EURO");
                    //flights.setTravelTime(getTravelTimeDifference(flights.getArrival(),flights.getDeparture()));
                    return pojo;
                }).collect(Collectors.toList());
        return  flightPojoList;
    }

    public static String getTravelTimeDifference(String departure,String arrival){

        LocalTime arrivalTime= LocalTime.parse(arrival, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime departureTime = LocalTime.parse(departure, DateTimeFormatter.ofPattern("HH:mm"));
        long hours = ChronoUnit.HOURS.between(arrivalTime, departureTime);
        long minutes
                = ChronoUnit.MINUTES.between(arrivalTime, departureTime) % 60;
        String timeDiff=hours+":"+minutes;
        System.out.println(
                "Difference is " + hours + " hours " + minutes
                        + " minutes " );
        return timeDiff;
    }

}
