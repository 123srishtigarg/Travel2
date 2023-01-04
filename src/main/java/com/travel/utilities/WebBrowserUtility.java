package com.travel.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WebBrowserUtility {

    public static final String SCROLL_INTO_VIEW_TRUE = "arguments[0].scrollIntoView(true);";
    WebDriver driver;
    public WebDriverWait wait;
    public WebBrowserUtility(WebDriver driver){
        this.driver = driver;
    }

    /**
     * Click on web element using Selenium.
     * Before clicking is checking for visibility and if the element is clickable.
     *
     * @param element           web element to be clicked
     * @param autoScrollEnabled enables the auto-scrolling to the element
     */
    public WebElement click(WebElement element, boolean autoScrollEnabled) {
        String myElementText = formatString(getElementText(element));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement result = null;

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            if (autoScrollEnabled) {
                ((JavascriptExecutor) driver).executeScript(SCROLL_INTO_VIEW_TRUE, element);
            }
            element.click();
            result = element;
            System.out.println("Successfully clicked on: { " + myElementText + " }.");
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println("{ click } " + " : " + e.getClass().getSimpleName() + myElementText);
        } catch (Exception e) {
            System.out.println("{ click } error message: " + e.getMessage());
        }

        return result;
    }

    /**
     * Clear the input field by back-space and insert text by sendKeys.
     *
     * @param element web element
     * @param text       string
     * @return text that was inserted to the input field
     */
    public String clearAndInsertText(WebElement element, String text) {
        String myElementText = formatString(getElementText(element));
        String result = null;

        try {
            click(element, false);
            clearInputFieldByBackSpace(element);
            element.sendKeys(text);
            result = getElementAttribute(element, "value");
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println("{ clearAndInsertText } " + " : " + e.getClass().getSimpleName() + " : "+ myElementText);
        }

        return result;
    }

    /**
     * Checks for visibility of given web element.
     *
     * @param element web element
     * @return visible web element
     */
    public WebElement visibilityOf(WebElement element){
        WebElement expectedElement = null;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try{
            expectedElement = wait.until(ExpectedConditions.visibilityOf(element));
            if(expectedElement != null){
                String myElementText = formatString(getElementText(element));
                System.out.println("Web element { " + myElementText + " } is visible.");
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println("{ visibilityOf } error message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("{ visibilityOf } error message: " + e.getMessage());
        }

        return expectedElement;
    }

    /**
     * Checks for visibility of given web elements list.
     *
     * @param elements web elements list
     * @return visible web elements list
     */
    public List<WebElement> visibilityOf(List<WebElement> elements) {
        List<WebElement> webElementListResult = null;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            webElementListResult = wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            if (webElementListResult != null) {
                String elementsText = elements.stream()
                        .map(WebElement::getText)
                        .collect(Collectors.joining(", ", "[", "]"));
                System.out.println("Web elements list { " + formatString(elementsText) + " } is visible.");
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println("{ visibilityOf } " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("{ visibilityOf } error message: " + e.getMessage());
        }

        return webElementListResult;
    }

    /**
     * Gets the web element text.
     *
     * @param element web element
     * @return text as string
     */
    public String getElementText(WebElement element) {
        String result = null;

        try {
            result = element.getText();
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println("{ getElementText } : " + e.getClass().getSimpleName());
        }

        return result;
    }

    /**
     * Replaces CR (Carriage Return) and LF (Line Feed) with SPACE in the given String text.
     *
     * @param inputText as String
     * @return new text as String without special characters CR and LF
     */
    public String formatString(String inputText) {
        return inputText.replace("\n", "").replace("\r", " ");
    }

    //Clear input field by BACK_SPACE and checking 'value' attribute.
    public void clearInputFieldByBackSpace(WebElement webElement) {
       // logInfo("Clear input field by BACK_SPACE and checking 'value' attribute.");

        try {
            do {
                click(webElement, false); // select all
                webElement.sendKeys(Keys.BACK_SPACE);
            } while (!getElementAttribute(webElement, "value").isEmpty());
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println("{ getElementText } " + e.getClass().getSimpleName());
        }

    }

    /**
     * Get the web element attribute.
     *
     * @param element   web element
     * @param attribute as string
     * @return attribute value as string
     */
    public String getElementAttribute(WebElement element, String attribute) {
        String result = null;

        try {
            result = element.getAttribute(attribute);
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println("{ getElementAttribute } " + e.getClass().getSimpleName());
        }

        return result;
    }

    public String getMainWindowHandle(){
        String mainWindow = driver.getWindowHandle();
        System.out.println("Main window handle is " + mainWindow);

        return mainWindow;
    }

    public String childWindowHandles() {
        String childWindow = null;
        try {

            Set<String> s1 = driver.getWindowHandles();
            System.out.println("Child window handles are: " + s1);
            Iterator<String> i1 = s1.iterator();

            while (i1.hasNext()) {
                childWindow = i1.next();
                if (!getMainWindowHandle().equalsIgnoreCase(childWindow)) {
                    driver.switchTo().window(childWindow);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return childWindow;
    }
}
