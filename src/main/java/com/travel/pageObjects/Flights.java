package com.travel.pageObjects;

import com.travel.utilities.WebBrowserUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Flights extends WebBrowserUtility {
    WebDriver driver;

    public Flights(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@data-cy='menu_Flights']//a")
    public WebElement flightMenu;

    @FindBy(xpath = "//*[@data-cy='roundTrip']")
    public WebElement roundTrip;

    @FindBy(xpath = "//*[@for='fromCity']")
    public WebElement fromCity;

    @FindBy(xpath = "//*[@placeholder='From']")
    public WebElement fromDropDownSearchBox;

    @FindBy(xpath = "//ul[@role='listbox']/descendant::li")
    public List<WebElement> cityDropDownOptions;

    @FindBy(xpath = "//*[@id='fromCity']")
    public WebElement fromInput;

    @FindBy(xpath = "//*[@for='toCity']")
    public WebElement toCity;

    @FindBy(xpath = "//*[@placeholder='To']")
    public WebElement toDropDownSearchBox;

    @FindBy(xpath = "//*[@id='toCity']")
    public WebElement toInput;

    @FindBy(xpath = "//*[contains(@class, 'DayPicker') and @aria-disabled='false']")
    public List<WebElement> currentFutureDates;

    @FindBy(xpath = "//*[@for='travellers']")
    public WebElement countAndClass;

    @FindBy(xpath = "//*[@class='travellers gbTravellers']")
    public WebElement travellersWindow;

    @FindBy(xpath = "//*[@data-cy='travellerApplyBtn']")
    public WebElement applyButton;

    @FindBy(xpath = "//*[contains(@class, 'SearchBtn')]")
    public WebElement searchButton;

    @FindBy(xpath = "//*[contains(@class, 'journey-title')]")
    public WebElement journeyTitle;

    @FindBy(xpath = "//*[contains(@id, 'bookbutton')]")
    public WebElement bookButton;

    @FindBy(xpath = "//*[contains(@class, 'lato-black button')]")
    public WebElement continueButton;

    @FindBy(xpath = "//*[@class='pageHeader']//h2")
    public WebElement flightSummaryHeaderTitle;

    @FindBy(xpath = "//*[@class='otherList']//button")
    public WebElement addNewAdultButton;


    //click on flights
    public void clickOnMenu(WebElement menu){
        visibilityOf(menu);
        click(menu, false);
    }

    //click on round trip
    public void selectTypeOfTrip(WebElement TypeOfTrip){
        visibilityOf(TypeOfTrip);
        click(TypeOfTrip, false);
    }

    //select from sourceCity
    public WebElement selectFromCity(String fromCity) throws InterruptedException {
        WebElement cityResult;

        visibilityOf(this.fromCity);
        click(this.fromCity, false);
        String text = clearAndInsertText(fromDropDownSearchBox, fromCity);
        Thread.sleep(1000);
        cityResult =getListOfCities().stream().filter(option-> getElementAttribute(option, "textContent").contains(text)).findFirst().orElse(null);
        click(cityResult, false);

        return cityResult;
    }

    public List<WebElement> getListOfCities(){
        List<WebElement> citiesResult = visibilityOf(cityDropDownOptions);

        return citiesResult;
    }

    //datepicker
    public void selectCalDate(int index){
        WebElement date = currentFutureDates.get(index);
        visibilityOf(date);
        click(date, false);
    }

    //select destination city
    public WebElement selectToCity(String destCity){
        WebElement cityResult = null;
        String destInputText = getElementAttribute(toInput, "value");
        String fromInputText = getElementAttribute(fromInput, "value");

        while(destInputText.equalsIgnoreCase(fromInputText)){
            visibilityOf(toCity);
            click(toCity, false);
            String text = clearAndInsertText(toDropDownSearchBox, destCity);
            cityResult =getListOfCities().stream().filter(option-> getElementAttribute(option, "textContent").contains(text)).findFirst().orElse(null);
            click(cityResult, false);
            destInputText = getElementAttribute(toInput, "value");
        }

        return cityResult;
    }

    //select passengers and class
    public int selectPassengers(String typeOfTraveller, int noOfPassengers){
        click(countAndClass, false);
        visibilityOf(travellersWindow);
        List<WebElement> listOfTravellers = driver.findElements(By.xpath( "//*[contains(@data-cy, '" +typeOfTraveller+ "-')]"));

        if(typeOfTraveller.equalsIgnoreCase("adults")){
            if(noOfPassengers > 10){
                noOfPassengers = listOfTravellers.size()-1;
            }
            click(listOfTravellers.get(noOfPassengers), false);
            click(applyButton, false);
        }

        return noOfPassengers;
    }

    public void selectTravelClass(String travelAttributeValue, String travelClass){
        click(countAndClass, false);
        List<WebElement> listOfTravelClasses = driver.findElements(By.xpath("//*[contains(@data-cy, '" +travelAttributeValue+ "-')]"));
        WebElement classResult = listOfTravelClasses.stream().filter(classType-> getElementText(classType).equalsIgnoreCase(travelClass)).findFirst().orElse(null);
        click(classResult, false);
        click(applyButton, false);
    }

    //click on search button
    public void clickOnSearchButton(){
        visibilityOf(searchButton);
        click(searchButton, false);
    }

    //select one way flight
    public void selectOneWayFlight(int index, String fromInputText, String destInputText) {
        String xpathResult = "//*[text() = '" +fromInputText+ " → " +destInputText+ "']//parent::p//following-sibling::*[@class='listingCardWrap']//child::*[contains(@id, 'flightCard-')]";

        visibilityOf(journeyTitle);
        List<WebElement> availableFlights = driver.findElements(By.xpath(xpathResult));
        visibilityOf(availableFlights);
        click(availableFlights.get(index), false);
    }

    //select round trip flight
    public void selectRoundTripFlight(int index, String fromInputText, String destInputText) {
        String xpathResult = "//*[text() = '" +destInputText+ " → " +fromInputText+ "']//parent::p//following-sibling::*[@class='listingCardWrap']//child::*[contains(@id, 'flightCard-')]";

        visibilityOf(journeyTitle);
        List<WebElement> availableFlights = driver.findElements(By.xpath(xpathResult));
        visibilityOf(availableFlights);
        click(availableFlights.get(index), false);
    }

    //click on book button
    public void clickOnBookButton(){
        visibilityOf(bookButton);
        click(bookButton, false);
    }

    //click on continue button
    public void clickOnContinueButton(){
        visibilityOf(continueButton);
        click(continueButton, false);
    }

}
