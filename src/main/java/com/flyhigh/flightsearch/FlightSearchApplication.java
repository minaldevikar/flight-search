package com.flyhigh.flightsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = {"com.flyhigh.flightsearch.repository"})
@SpringBootApplication
public class FlightSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightSearchApplication.class, args);
	}

}
