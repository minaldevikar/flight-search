package com.flyhigh.flightsearch.service;


import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 8/21/2022.
 */
@Service
public class FlightSearchServiceImpl implements FlightSearchService{

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchServiceImpl.class);

    @Autowired
    FlightSearchRepo flightSearchRepo;

    @Override
    public List<Flights> getAllFlights(String field) {
        logger.info("Inside getAllFlights...");
        return flightSearchRepo.findAll(Sort.by(field).ascending());
    }

    @Override
    public List<Flights> fetchRequiredFlights(String origin, String destination, String sortBy) {
        logger.info("Inside fetchRequiredFlights with origin: " +origin +" and destination: "+destination );
        List<Flights> flightsList=flightSearchRepo.findByOriginAndDestination(origin,destination);
        switch (sortBy){
            case "departure":
                return flightsList.stream().sorted(Comparator.comparing(Flights::getDeparture)).collect(Collectors.toList());

            case "arrival":
                return flightsList.stream().sorted(Comparator.comparing(Flights::getArrival)).collect(Collectors.toList());

            case "price":
                return flightsList.stream().sorted(Comparator.comparing(Flights::getFare)).collect(Collectors.toList());

            default:
                return flightsList.stream().sorted(Comparator.comparing(Flights::getFlightId)).collect(Collectors.toList());
        }
    }

    @Override
    public List<Flights> sortFlights(String sortBy,String sortType) {
        logger.info("Inside sortFlights...sort by "+sortBy);
        List<Flights> flightsList=flightSearchRepo.findAll(Sort.by(sortBy).ascending());
        if(sortType.equals("desc"))
            flightsList=flightSearchRepo.findAll(Sort.by(sortBy).descending());

        return flightsList;
    }
}
