package com.flyhigh.flightsearch;

import com.flyhigh.flightsearch.controller.FlightSearchController;
import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightSearchApplicationTests {

	@Autowired
	FlightSearchRepo flightRepo;

	@Test
	@Order(5)
	void contextLoads() {

	}

	@Test
	@Order(1)
	@Rollback(value = false)
	public void LoadTestData() {
		Flights f1 = new Flights("test1", "abc", "xyz","11:00","17:00" ,1);
		Flights f2 = new Flights("test2", "abc", "xyz","14:00","21:00" ,2);
		Flights f3 = new Flights("test3", "gogo", "nono","08:00","13:00" ,3);

		Flights f4 = new Flights();
		f4.setFlightId("test4");
		f4.setOrigin("soso");
		f4.setDestination("toto");
		f4.setDeparture("06:00");
		f4.setArrival("14:00");
		f4.setFare(4);

		flightRepo.save(f1);
		flightRepo.save(f2);
		flightRepo.save(f3);
		flightRepo.save(f4);

		List<Flights> allFlights = flightRepo.findAll();
		assertNotNull(allFlights);
	}

	@Test
	@Order(2)
	public void testFindAllFlights(){
		List<Flights> allFlights = flightRepo.findAll();
		assertNotNull(allFlights);
		assertThat(allFlights).size().isGreaterThan(2);
		assertThat(allFlights).size().isLessThan(5);
		assertThat(allFlights).size().isEqualTo(4);
	}

	@Test
	@Order(3)
	public void testFindByOriginAndDestination(){
		List<Flights> allFlights = flightRepo.findByOriginAndDestination("abc", "xyz");
		assertNotNull(allFlights);
		assertThat(allFlights).size().isEqualTo(2);
	}

	@Test
	@Order(4)
	public void testFilterFlight(){
		List<Flights> allFlights = flightRepo.findByOriginAndDestination("gogo", "nono");
		assertNotNull(allFlights);
		assertThat(allFlights).size().isEqualTo(1);
	}
}
