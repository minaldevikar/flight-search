/*
package com.flyhigh.flightsearch;

import com.flyhigh.flightsearch.controller.FlightSearchController;
import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.service.FlightSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Admin on 8/23/2022.
 *//*

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlightSearchController.class)
public class FlightSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightSearchService flightSearchService;

    @Test
    public void getAllFlightsSortedViewTest() throws Exception {
        List<Flights> flightsList=getData();
        Mockito.when(
                flightSearchService.getAllFlightsSortedView(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(flightsList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/v1/flights?sortBy=origin&sortType=asc").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = flightsList.toString();
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }


    @Test
    public void fetchRequiredFlightsTest()throws Exception{
        List<Flights> flightsList=getData();
        Mockito.when(
                flightSearchService.getAllFlightsSortedView(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(flightsList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/v1/flights/BOM/DEL").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = flightsList.toString();
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void fetchRequiredFlightsTestException()throws Exception{
        List<Flights> flightsList=new ArrayList<>();
        Mockito.when(
                flightSearchService.fetchRequiredFlights(Mockito.anyString(),
                        Mockito.anyString(),Mockito.anyString())).thenReturn(flightsList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/v1/flights/BOM/DEL").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "Data not available";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    List<Flights> getData(){
        Flights f1 = new Flights("test1", "abc", "xyz","11:00","17:00" ,120);
        Flights f2 = new Flights("test2", "kbc", "pyz","14:00","21:00" ,210);
        List<Flights> flightsList=new ArrayList<>();
        flightsList.add(f1);
        flightsList.add(f2);
        return flightsList;
    }

}
*/
