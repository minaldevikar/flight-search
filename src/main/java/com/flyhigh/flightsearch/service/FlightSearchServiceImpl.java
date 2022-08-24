package com.flyhigh.flightsearch.service;


import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.exception.DataNotAvailableException;
import com.flyhigh.flightsearch.exception.EndpointNotDefinedException;
import com.flyhigh.flightsearch.exception.InvalidDataLengthException;
import com.flyhigh.flightsearch.exception.NumAndSplCharNotAllowedException;
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

/**
 * Created by Admin on 8/21/2022.
 */
@Service
public class FlightSearchServiceImpl implements FlightSearchService {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchServiceImpl.class);

    @Autowired
    FlightSearchRepo flightSearchRepo;

    //function to fetch all the flights
    @Override
    public List<FlightPojo> getAllFlightsSortedView(String sortBy, String sortType) {
        logger.info("Inside getAllFlights...sortBy: " + sortBy + "sortType");

        boolean isDescending = !sortType.isEmpty() && sortType.equalsIgnoreCase("desc");
        if (!(isDescending || sortType.equalsIgnoreCase("asc")))
            throw new EndpointNotDefinedException("sortType only accepts {asc/desc}, provided :[" + sortType + "]");

        //get all the objects and populate the filghtList
        List<Flights> flightsList = flightSearchRepo.findAll(Sort.by(sortBy).ascending());
        if (isDescending) flightsList = flightSearchRepo.findAll(Sort.by(sortBy).descending());
        if (flightsList.isEmpty()) throw new DataNotAvailableException();
        return generateFlightPojoList(flightsList);
    }

    //function to fetch flights on the basis of origin and destination
    @Override
    public List<FlightPojo> fetchRequiredFlights(String origin, String destination, String sortBy, String sortType) {
        logger.info("Inside fetchRequiredFlights with origin: " + origin + " and destination: " + destination + "with sort by " + sortBy);

        if (origin.isEmpty() || destination.isEmpty())
            throw new EndpointNotDefinedException("3 Letter Origin and Destination airport code required.");
        if (!origin.matches("^[a-zA-Z]*$") || !destination.matches("^[a-zA-Z]*$"))
            throw new NumAndSplCharNotAllowedException();
        if (origin.length() != 3 || destination.length() != 3)
            throw new InvalidDataLengthException();
        boolean isDescending = !sortType.isEmpty() && sortType.equalsIgnoreCase("desc");
        if (!(isDescending || sortType.equalsIgnoreCase("asc")))
            throw new EndpointNotDefinedException("sortType only accepts {asc/desc}, provided :[" + sortType + "]");

        List<Flights> flightsList = flightSearchRepo.findByOriginAndDestination(origin.toUpperCase(), destination.toUpperCase());
        if (flightsList.isEmpty())
            throw new DataNotAvailableException();
        else {
            flightsList = flightsList.stream()
                    .map(flights -> {
                        flights.setTravelTime(getTravelTimeDifference(flights.getArrival(),
                                flights.getDeparture()));
                        return flights;
                    }).collect(Collectors.toList());

        }

        switch (sortBy) {
            //sortBy departure
            case "departure":
                flightsList = flightsList.stream()
                        .sorted(Comparator.comparing(Flights::getDeparture))
                        .collect(Collectors.toList());
                if (isDescending)
                    flightsList = flightsList.stream()
                            .sorted(Comparator.comparing(Flights::getDeparture).reversed())
                            .collect(Collectors.toList());
                break;

            //sortBy arrival
            case "arrival":
                flightsList = flightsList.stream()
                        .sorted(Comparator.comparing(Flights::getArrival))
                        .collect(Collectors.toList());
                if (isDescending)
                    flightsList = flightsList.stream()
                            .sorted(Comparator.comparing(Flights::getArrival).reversed())
                            .collect(Collectors.toList());
                break;

            //sortBy price
            case "price":
                flightsList = flightsList.stream()
                        .sorted(Comparator.comparing(Flights::getprice))
                        .collect(Collectors.toList());
                if (isDescending)
                    flightsList = flightsList.stream()
                            .sorted(Comparator.comparing(Flights::getprice).reversed())
                            .collect(Collectors.toList());
                break;

            //sortBy travelTime
            case "travelTime":
                flightsList = flightsList.stream()
                        .sorted(Comparator.comparing(Flights::getTravelTime))
                        .collect(Collectors.toList());
                if (isDescending)
                    flightsList = flightsList.stream()
                            .sorted(Comparator.comparing(Flights::getTravelTime).reversed())
                            .collect(Collectors.toList());
                break;
            default:
                flightsList = flightsList.stream()
                        .sorted(Comparator.comparing(Flights::getFlightId))
                        .collect(Collectors.toList());

        }

        return generateFlightPojoList(flightsList);
    }

    //function to extract the difference in time from received departure and arrival
    public static String getTravelTimeDifference(String departure, String arrival) {
        LocalTime arrivalTime = LocalTime.parse(arrival, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime departureTime = LocalTime.parse(departure, DateTimeFormatter.ofPattern("HH:mm"));
        long hours = ChronoUnit.HOURS.between(arrivalTime, departureTime);
        long minutes = ChronoUnit.MINUTES.between(arrivalTime, departureTime) % 60;
        String timeDiff = hours + ":" + minutes;
        return timeDiff;
    }

    /* function to generate the flightPojoList so as
     * to expose only the required field and the format which is requested
     */
    private List<FlightPojo> generateFlightPojoList(List<Flights> flightsList) {
        List<FlightPojo> flightPojoList = flightsList.stream()
                .map(flights -> {
                    FlightPojo pojo = new FlightPojo(
                            flights.getFlightId(),
                            flights.getOrigin(),
                            flights.getDestination(),
                            flights.getArrival(),
                            flights.getDeparture(),
                            flights.getprice().toString() + " " + flights.getCurrency()
                    );
                    return pojo;
                }).collect(Collectors.toList());
        return flightPojoList;
    }
}
