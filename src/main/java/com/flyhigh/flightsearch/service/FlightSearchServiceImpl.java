package com.flyhigh.flightsearch.service;


import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.exception.DataNotAvailableException;
import com.flyhigh.flightsearch.exception.EndpointNotDefinedException;
import com.flyhigh.flightsearch.exception.InvalidDataLengthException;
import com.flyhigh.flightsearch.exception.NumAndSplCharNotAllowedException;
import com.flyhigh.flightsearch.payload.FlightPojo;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 8/21/2022.
 */
@Service
public class FlightSearchServiceImpl implements FlightSearchService {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchServiceImpl.class);
    private static final String airPortCodeRegex = "^[a-zA-Z]*$";
    private static final String[] acceptableSortDirection = new String[]{"asc", "desc"};
    private static final String[] acceptableSortListCase2 = new String[]{"arrival", "departure", "price", "travelTime"};

    @Autowired
    FlightSearchRepo flightSearchRepo;

    //function to fetch all the flights
    @Override
    public List<FlightPojo> getAllFlightsSortedView(String sortBy, String sortType) {
        logger.info("Inside getAllFlights...sortBy: " + sortBy + "sortType");

        //only the allowed sorting direction should be allowed
        boolean isDescending = !sortType.isEmpty() && sortType.equalsIgnoreCase("desc");
        if (!(isDescending || Arrays.stream(acceptableSortDirection).anyMatch(sortType::equalsIgnoreCase)))
            throw new EndpointNotDefinedException("sortType only accepts {"
                    + StringUtils.join(acceptableSortDirection) + "} provided :[" + sortType + "]");

        //get all the objects and populate the flightList
        List<Flights> flightsList = flightSearchRepo.findAll(Sort.by(sortBy).ascending());
        if (isDescending) flightsList = flightSearchRepo.findAll(Sort.by(sortBy).descending());
        if (flightsList.isEmpty()) throw new DataNotAvailableException();
        return generateFlightPojoList(flightsList);
    }

    //function to fetch flights on the basis of origin and destination
    @Override
    public List<FlightPojo> fetchRequiredFlights(String origin, String destination, String sortBy, String sortType) {
        logger.info("Inside fetchRequiredFlights with origin: [" + origin + "] " +
                "and destination: [" + destination + "] with sort by [" + sortBy + "]");


        if (origin.isEmpty() || destination.isEmpty())
            throw new EndpointNotDefinedException("3 Letter Origin and Destination airport code required.");
        if (!origin.matches(airPortCodeRegex) || !destination.matches(airPortCodeRegex))
            throw new NumAndSplCharNotAllowedException();
        if (origin.length() != 3 || destination.length() != 3)
            throw new InvalidDataLengthException();

        //only the allowed sorting direction should be allowed
        boolean isDescending = !sortType.isEmpty() && sortType.equalsIgnoreCase("desc");
        if (!(isDescending || Arrays.stream(acceptableSortDirection).anyMatch(sortType::equalsIgnoreCase)))
            throw new EndpointNotDefinedException("sortType only accepts {"
                    + StringUtils.join(acceptableSortDirection) + "} provided :[" + sortType + "]");

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

        //only the allowed list of sortBy options need to be provided
        boolean isSortingAllowed = Arrays.stream(acceptableSortListCase2).anyMatch(sortBy::equalsIgnoreCase);
        if (!isSortingAllowed)
            throw new EndpointNotDefinedException("sortBy allowed only on : {" + StringUtils.join(acceptableSortListCase2) + "}");
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
                        .sorted(Comparator.comparing(Flights::getPrice))
                        .collect(Collectors.toList());
                if (isDescending)
                    flightsList = flightsList.stream()
                            .sorted(Comparator.comparing(Flights::getPrice).reversed())
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
        }

        return generateFlightPojoList(flightsList);
    }

    //function to extract the difference in time from received departure and arrival
    private String getTravelTimeDifference(LocalTime departure, LocalTime arrival) {
        long hours = ChronoUnit.HOURS.between(arrival, departure);
        long minutes = ChronoUnit.MINUTES.between(arrival, departure) % 60;
        return hours + ":" + minutes;
    }

    /* function to generate the flightPojoList to
     * expose only the required field and the format which is requested
     */
    private List<FlightPojo> generateFlightPojoList(List<Flights> flightsList) {
        List<FlightPojo> flightPojoList = flightsList.stream()
                .map(flights -> new FlightPojo(
                        flights.getFlightId(),
                        flights.getOrigin(),
                        flights.getDestination(),
                        String.valueOf(flights.getArrival()),
                        String.valueOf(flights.getDeparture()),
                        flights.getPrice().toString() + " " + flights.getCurrency()
                )).collect(Collectors.toList());
        return flightPojoList;
    }
}
