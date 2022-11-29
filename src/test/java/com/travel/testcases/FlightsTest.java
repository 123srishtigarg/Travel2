package com.travel.testcases;

import com.travel.core.TestBase;
import com.travel.pageObjects.Flights;

import org.testng.annotations.Test;


public class FlightsTest extends TestBase {

    @Test
    public void bookFlight() throws Exception {

        Flights flights = new Flights(driver);
        flights.clickOnMenu(flights.flightMenu);
        flights.selectTypeOfTrip(flights.roundTrip);
        flights.selectFromCity("New Delhi");
        flights.selectCalDate(2);
        flights.selectCalDate(3);
        flights.selectToCity("Bengaluru");
        flights.selectPassengers("adults", 2);
        flights.selectTravelClass("travelClass", "Business");
        String destCity = flights.toInput.getAttribute("value");
        String fromCity = flights.fromInput.getAttribute("value");
        flights.clickOnSearchButton();
        flights.selectOneWayFlight(1,fromCity, destCity);
        flights.selectRoundTripFlight(1,fromCity, destCity);
        flights.clickOnBookButton();
        flights.clickOnContinueButton();

    }
}
