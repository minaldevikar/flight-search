package com.flyhigh.flightsearch.service;

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 8/21/2022.
 */
@Service
public class FlightSearchServiceImpl implements FlightSearchService{

    @Autowired
    FlightSearchRepo flightSearchRepo;

    @Override
    public List<Flights> getAllFlights() {
        return flightSearchRepo.findAll();
    }

    @Override
    public List<Flights> fetchRequiredFlights(String origin, String destination) {
        return flightSearchRepo.findByOriginAndDestination(origin,destination);
    }

    @Override
    public List<Flights> sortFlights(String sortParameter,String sortType) {
        List<Flights> sortedFlightsList;
        List<Flights> flightsList=flightSearchRepo.findAll();
        System.out.println("########"+sortParameter);
        Comparator<Flights> flightsComparator ;
        switch (sortParameter){
            case "origin":
                flightsComparator=Comparator.comparing(Flights::getOrigin);
                break;
            case "flightId":
                flightsComparator=Comparator.comparing(Flights::getOrigin);
                break;
            case "destination":
                flightsComparator=Comparator.comparing(Flights::getDestination);
                break;
            case "departure":
                flightsComparator=Comparator.comparing(Flights::getDeparture);
                break;
            case "arrival":
                flightsComparator=Comparator.comparing(Flights::getArrival);
                break;
            case "price":
                flightsComparator=Comparator.comparing(Flights::getFare);
                break;
            default:
                flightsComparator=Comparator.comparing(Flights::getFare);
                break;
        }

        if(!sortType.isEmpty() && sortType.equals("desc"))
            sortedFlightsList = flightsList.stream().sorted(flightsComparator.reversed()).collect(Collectors.toList());
        else
            sortedFlightsList = flightsList.stream().sorted(flightsComparator).collect(Collectors.toList());

        return sortedFlightsList;
    }
}
