package com.travel.testcases;

import com.travel.core.TestBase;
import com.travel.pageObjects.Hotels;
import org.testng.annotations.Test;

public class HotelsTest extends TestBase {


    @Test
    public void bookHotel() throws InterruptedException {
        Hotels hotels = new Hotels(driver);
        hotels.click(hotels.outsideModal, false);
        hotels.clickOnHotelMenu();
        hotels.selectCity("Bengaluru");
        hotels.selectHotelDate(2);
        hotels.selectHotelDate(3);
        hotels.selectRoomsGuests(2, 0);
        Thread.sleep(2000);
        hotels.getFlightsObject().clickOnSearchButton();
        hotels.selectRoomsGuests(3, 0);
        hotels.selectStarCategory(1);
    }

}
