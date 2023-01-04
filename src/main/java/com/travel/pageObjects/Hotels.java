package com.travel.pageObjects;

import com.travel.utilities.WebBrowserUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Hotels extends WebBrowserUtility {

    WebDriver driver;

    public Hotels(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@data-cy='menu_Hotels']//a")
    public WebElement hotelMenu;

    @FindBy(xpath = "//*[@data-cy='outsideModal']")
    public WebElement outsideModal;

    @FindBy(xpath = "//*[@for='city']")
    public WebElement hotelCity;

    @FindBy(xpath = "//*[contains(@placeholder, 'Enter city/ Hotel')]")
    public WebElement cityDropDownSearchBox;

    @FindBy(xpath = "//*[@class='dayPickerHotelWrap']")
    public WebElement dayPicker;

    @FindBy(xpath = "//*[@for='checkin']")
    public WebElement checkInDate;

    @FindBy(xpath = "//*[@for='guest']")
    public WebElement rmsGstDropdown;

    @FindBy(xpath = "//*[@class='rmsGst']")
    public WebElement rmsGstWindow;

    @FindBy(xpath = "//*[@data-testid='room_count']")
    public WebElement roomCount;

    @FindBy(xpath = "//*[@data-testid='adult_count']")
    public WebElement adultCount;

    @FindBy(xpath = "//*[@class='gstSlct__list']//li")
    public List<WebElement> countListItems;

    @FindBy(xpath = "//*[@class='rmsGst__footer']//button")
    public WebElement rmsGstApplyButton;

    @FindBy(xpath = "//*[@id = 'STAR_CATEGORY']")
    public WebElement starCategory;

    @FindBy(xpath = "//*[@id='STAR_CATEGORY']//*[@class='filterList']//li")
    public List<WebElement> starCategoryListOptions;

    //click on flights
    public void clickOnHotelMenu(){
        visibilityOf(hotelMenu);
        click(hotelMenu, false);
    }

public WebElement selectCity(String cityName) throws InterruptedException {
WebElement cityResult;

    visibilityOf(hotelCity);
    click(hotelCity, false);
    String text = clearAndInsertText(cityDropDownSearchBox, cityName);
    Thread.sleep(1000);
    cityResult =getFlightsObject().getListOfCities().stream().filter(option-> getElementAttribute(option, "textContent").contains(text)).findFirst().orElse(null);
    click(cityResult, false);

    return cityResult;
}

    public void selectHotelDate(int index){

        if(visibilityOf(dayPicker)== null){
            visibilityOf(checkInDate);
            click(checkInDate, false);
        }
        WebElement date = getFlightsObject().currentFutureDates.get(index);
        visibilityOf(date);
        click(date, false);
    }

    public int selectRoomsGuests(int roomCount, int adultCount){

        if(visibilityOf(rmsGstWindow)== null){
            visibilityOf(rmsGstDropdown);
            click(rmsGstDropdown, false);
        }

        String selectedRooms = selectRooms(roomCount);
        String selectedAdults = selectAdults(adultCount);
        click(rmsGstApplyButton, false);

        return Integer.parseInt(selectedAdults);
    }

    public String selectRooms(int count){
        visibilityOf(roomCount);
        click(roomCount, false);
        visibilityOf(countListItems);
        click(countListItems.get(count), false);

        return getElementText(roomCount);
    }

    public String selectAdults(int count){
        visibilityOf(adultCount);
        click(adultCount, false);
        visibilityOf(countListItems);
        click(countListItems.get(count), false);

        return getElementText(adultCount);
    }

    public void selectStarCategory(int count){
        visibilityOf(starCategory);
        click(starCategoryListOptions.get(count), true);
    }

    public Flights getFlightsObject() {
        return new Flights(driver);
    }

}
