package com.flyhigh.flightsearch;

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FlightSearchApplicationTests {

	@Test
	void contextLoads() {
	}

	public void LoadTestData() {
		Flights f1 = new Flights("test1", "abc", "xyz","11:00","17:00" ,1);
		Flights f2 = new Flights("test2", "abc", "xyz","14:00","21:00" ,2);
		Flights f3 = new Flights("test3", "gogo", "nono","08:00","13:00" ,3);

		Flights f4 = new Flights();
		f4.setFlightId("test4");
		f4.setOrigin("soso");
		f4.setDestination("toto");
		f4.setFare(1);

		flightRepo.save(f1);
		flightRepo.save(f2);
		flightRepo.save(f3);
		flightRepo.save(f4);

	}

	@Autowired
	FlightSearchRepo flightRepo;

	@Test
	public void testFindAllFlights(){
		LoadTestData(); // 14 [from DataLoader] + 4 [hardcoded test data]
		List<Flights> allFlights = flightRepo.findAll();
		assertNotNull(allFlights);
		assertThat(allFlights).size().isGreaterThan(14);
		assertThat(allFlights).size().isLessThan(20);
		assertThat(allFlights).size().isEqualTo(18);
	}

	@Test
	public void testFindByOriginAndDestination(){
		LoadTestData();
		List<Flights> allFlights = flightRepo.findByOriginAndDestination("abc", "xyz");
		assertNotNull(allFlights);
		assertThat(allFlights).size().isEqualTo(2);
	}

	@Test
	public void testFilterFlight(){
		LoadTestData();
		List<Flights> allFlights = flightRepo.findByOriginAndDestination("gogo", "nono");
		assertNotNull(allFlights);
		assertThat(allFlights).size().isEqualTo(1);
	}
}
