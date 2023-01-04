package com.travel.testcases;

import com.travel.core.TestBase;
import com.travel.pageObjects.Flights;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.*;

public class FlightsTest extends TestBase {

    Object object = new Object();
    Flights flights;

    @BeforeMethod
    public void preconditions() {
        flights = new Flights(driver);
    }

    @DataProvider
    public Object[][] getData(){
        object= getDataMap("passengerDetails");
        return new Object[][] { {object}, {object}, {object} };
    }

    @Test()
    public void bookFlight() throws Exception {

        flights.click(flights.outsideModal, false);
        flights.clickOnFlightMenu();
        flights.selectTypeOfTrip(flights.roundTrip);
        flights.selectFromCity("New Delhi");
        flights.selectToCity("Bengaluru");
        flights.selectCalDate(2);
        flights.selectCalDate(3);
        flights.selectPassengers("adults", 2);
        flights.selectTravelClass("travelClass", "Business");
        String destCity = flights.toInput.getAttribute("value");
        String fromCity = flights.fromInput.getAttribute("value");
        flights.clickOnSearchButton();
        flights.selectOneWayFlight(1, fromCity, destCity);
        flights.selectRoundTripFlight(1, fromCity, destCity);
        flights.clickOnBookButton();
        flights.clickOnContinueButton();
        flights.validatePageHeader();
    }

    @Test(dataProvider = "getData", dependsOnMethods = {"bookFlight"})
    public void addPassengers(String firstName, String lastName, String gender){
        flights.addNewAdult(firstName, lastName, gender);
    }

}