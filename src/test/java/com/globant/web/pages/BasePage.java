package com.globant.web.pages;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Parent of the other classes of pages.
 *
 * @author Sebastian Mesa
 */
@Slf4j
@Getter
public class BasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-M-d");

    /**
     * Constructor.
     *
     * @param pDriver : WebDriver
     */
    public BasePage(WebDriver pDriver) {
        PageFactory.initElements(pDriver, this);
        wait = new WebDriverWait(pDriver, 20);
        driver = pDriver;
    }

    /**
     * Close the web driver.
     */
    public void dispose() {
        if (driver != null) {
            driver.quit();
        }
    }

    public LocalDate buildDateForCalendar(WebElement day) {
        return LocalDate.parse(
                day.getAttribute("data-year") + "-" +
                        day.getAttribute("data-month") + "-" +
                        day.getAttribute("data-day"), dateFormat)
                .plusMonths(1);
    }

    // WAITS

    /**
     * Wait element to be visible.
     *
     * @param element : {@link WebElement}
     */
    public void waitElementVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait element to be staleness.
     *
     * @param element : {@link WebElement}
     */
    public void waitElementStaleness(WebElement element) {
        getWait().until(ExpectedConditions.stalenessOf(element));
    }

    /**
     * Wait for all elements to be visible.
     *
     * @param element : {@link List}<{@link WebElement}>
     */
    public void waitAllElementsVisibility(List<WebElement> element) {
        getWait().until(ExpectedConditions.visibilityOfAllElements(element));
    }

    /**
     * Wait element to be invisible.
     *
     * @param element : {@link WebElement}
     */
    public void waitElementInvisibility(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Wait element to be clickable.
     *
     * @param element : {@link WebElement}
     */
    public void waitElementClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait modal to be visible and close it.
     *
     * @param modal       : {@link WebElement}
     * @param closeButton : {@link WebElement}
     */
    public void waitForModalAndCloseIt(WebElement modal, WebElement closeButton) {
        try {
            waitElementVisibility(modal);
            click(closeButton);
        } catch (Exception e) {
            log.warn("Modal is not displayed.");
        }
    }

    /**
     * Wait element to be visible and then be invisible.
     *
     * @param element : {@link WebElement}
     */
    public void waitElementAppearDisappear(WebElement element) {
        waitElementVisibility(element);
        waitElementInvisibility(element);
    }

    // ACTIONS

    /**
     * Click on element.
     *
     * @param element : {@link WebElement}
     */
    public void click(WebElement element) {
        waitElementVisibility(element);
        element.click();
    }

    /**
     * Click on clickable element.
     *
     * @param element : {@link WebElement}
     */
    public void clickClickable(WebElement element) {
        waitElementVisibility(element);
        waitElementClickable(element);
        element.click();
    }

    /**
     * Fill element with a sequence of chars as String
     *
     * @param element  : {@link WebElement}
     * @param sequence : {@link String}
     */
    public void sendKeys(WebElement element, String sequence) {
        waitElementVisibility(element);
        element.sendKeys(sequence);
    }

    /**
     * Clear existing data, and then fill element with a sequence of chars as String
     *
     * @param element  : {@link WebElement}
     * @param sequence : {@link String}
     */
    public void clearAndSendKeys(WebElement element, String sequence) {
        waitElementVisibility(element);
        element.clear();
        element.sendKeys(sequence);
    }

    /**
     * Method to move to the last open/new tab
     */
    public void moveToNewTab() {
        Set<String> tabHandling = driver.getWindowHandles();
        driver.switchTo().window(tabHandling.toArray()[tabHandling.size() - 1].toString());
    }

    /**
     * Method to move into view of an element
     *
     * @param element : {@link WebElement}
     */
    public void scrollIntoView(WebElement element) {
        waitElementVisibility(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // DATA

    /**
     * Method to get the name of the current tab
     *
     * @return String with the name of the tab
     */
    public String getTabName() {
        return driver.getTitle();
    }

    /**
     * Method to select an value from a dropdown element
     *
     * @param element : {@link WebElement}
     * @param value   : {@link String}
     * @return WebElement with the option selectd
     */
    public WebElement selectFromDropdownByValue(WebElement element, String value) {
        waitElementVisibility(element);
        Select dropdown = new Select(element);
        dropdown.selectByValue(value);
        return dropdown.getFirstSelectedOption();
    }

    public boolean areValuesInTheDropdown(WebElement element, List<String> expectedOptions) {
        waitElementVisibility(element);
        for (WebElement option : new Select(element).getOptions())
            if (!expectedOptions.contains(option.getText())) return false;
        return true;
    }

    /**
     * Method to get numbers from String
     *
     * @param input : {@link String}
     * @return array of numbers from string
     */
    public int[] getNumbersFromSting(String input) {
        return Stream.of((input.replaceAll("\\D+", "")).split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}
