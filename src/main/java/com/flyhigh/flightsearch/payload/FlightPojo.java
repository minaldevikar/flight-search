package com.flyhigh.flightsearch.payload;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Created by Admin on 8/21/2022.
 */
@Entity
public class FlightPojo {

    @Id
    private String flightId;
    private String origin;
    private String destination;
    private String departure;
    private String arrival;
    private String price;

    public FlightPojo(String id, String origin, String destination, String departure, String arrival, String price) {
        super();
        this.flightId = id;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }


    @Override
    public String toString() {

        return "Flight [flightNumber=" + flightId + ", origin=" + origin + ", destination=" + destination + ", departureTime="
                + departure + ", arrivalTime=" + arrival + ", price in EURO =" + price + "]";
    }
}

