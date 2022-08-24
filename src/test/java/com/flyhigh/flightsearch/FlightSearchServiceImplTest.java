package com.flyhigh.flightsearch;

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.payload.FlightPojo;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import com.flyhigh.flightsearch.service.FlightSearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightSearchServiceImplTest {

    @InjectMocks
    FlightSearchServiceImpl flightSearchService;

    @Mock
    FlightSearchRepo flightSearchRepo;

    @Test
    public void getAllFlightsSortedViewTest(){
        when(flightSearchRepo.findAll(Sort.by("origin").ascending())).thenReturn(getData());
       List<FlightPojo> flightsList=flightSearchService.getAllFlightsSortedView("origin","asc");
        assertEquals(2, flightsList.size());
        assertEquals("MAA", flightsList.get(0).getOrigin());
    }

    @Test
    public void fetchRequiredFlightsTest(){
        when(flightSearchRepo.findByOriginAndDestination(Mockito.anyString(),Mockito.anyString())).thenReturn(getData());
        List<FlightPojo> flightsList=flightSearchService.fetchRequiredFlights("MAA","NAG","price","asc");
        assertEquals("NAG", flightsList.get(0).getDestination());
    }

    public List<Flights> getData(){
        Flights f1 = new Flights("T101", "MAA", "NAG","11:00","17:00" ,120, "EURO");
        Flights f2 = new Flights("T202", "DEL", "BOM","14:00","21:00" ,210, "EURO");
        List<Flights> flightsList=new ArrayList<>();
        flightsList.add(f1);
        flightsList.add(f2);
        return flightsList;
    }
}
