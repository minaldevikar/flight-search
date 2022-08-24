package com.flyhigh.flightsearch;

import com.flyhigh.flightsearch.controller.FlightSearchController;
import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.payload.FlightPojo;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import com.flyhigh.flightsearch.service.FlightSearchService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Admin on 8/23/2022.
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlightSearchController.class)
public class FlightSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightSearchService flightSearchService;
    @MockBean
    FlightSearchRepo flightSearchRepo;

    @Test
    public void getAllFlightsSortedViewTest() throws Exception {
        List<FlightPojo> flightsList=getData();
        Mockito.when(
                flightSearchService.getAllFlightsSortedView(Mockito.anyString(),Mockito.anyString())).thenReturn(flightsList);

        mockMvc.perform(get("/v1/flights?sortBy=origin&sortType=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    public void fetchRequiredFlightsTest() throws Exception{
        List<FlightPojo> flightsList=getData();
        Mockito.when(
                flightSearchService.getAllFlightsSortedView(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(flightsList);

        mockMvc.perform(get("/v1/flights/BOM/DEL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    List<FlightPojo> getData(){
        FlightPojo f1 = new FlightPojo("T101", "MAA", "NAG","11:00","17:00" ,"120 EURO");
        FlightPojo f2 = new FlightPojo("T202", "DEL", "BOM","14:00","21:00" ,"210 EURO");
        List<FlightPojo> flightsList=new ArrayList<>();
        flightsList.add(f1);
        flightsList.add(f2);
        return flightsList;
    }


}