package com.globant.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents the Home Page.
 *
 * @author Sebastian Mesa
 */
public class HomePage extends BasePage {

    // Tab
    @FindBy(id = "tab-flight-tab-hp")
    private WebElement flightTab;

    // TextFields
    @FindBy(id = "flight-origin-hp-flight")
    private WebElement originTextField;
    @FindBy(id = "flight-destination-hp-flight")
    private WebElement destinationTextField;
    @FindBy(id = "flight-departing-hp-flight")
    private WebElement checkinDateTextField;
    @FindBy(id = "flight-returning-hp-flight")
    private WebElement checkoutDateTextField;

    @FindBy(className = "xp__guests__count")
    private WebElement guestsTextField;

    // Labels
    @FindBy(css = ".sb-group__field-adults span.bui-stepper__display")
    private WebElement amountAdultsLabel;
    @FindBy(css = ".sb-group-children span.bui-stepper__display")
    private WebElement amountKidsLabel;
    @FindBy(css = ".sb-group__field-rooms span.bui-stepper__display")
    private WebElement amountRoomsLabel;

    // Buttons
    @FindBy(id = "flight-type-roundtrip-label-hp-flight")
    private WebElement roundTripButton;
    @FindBy(css = "#section-flight-tab-hp .gcw-submit")
    private WebElement searchButton;

    // Dropdown
    @FindBy(id = "flight-adults-hp-flight")
    private WebElement adultsDropdown;
    @FindBy(id = "flight-children-hp-flight")
    private WebElement childrenDropdown;
    @FindBy(css = ".children-data select")
    private List<WebElement> agesDropdown;

    // Calendar
    @FindBy(className = "datepicker-next")
    private WebElement nextMonthCalendarButton;
    @FindBy(className = "datepicker-cal")
    private WebElement calendarWidget;
    @FindBy(css = ".datepicker-cal tbody button:not(.disabled)")
    private List<WebElement> daysCalendar;

    /**
     * Constructor.
     *
     * @param driver : {@link WebDriver}
     */
    public HomePage(WebDriver driver, String url) {
        super(driver);
        driver.get(url);
        driver.manage().window().maximize();
    }

    /**
     * Method to select the flight tab
     */
    public void selectFlightTab() {
        click(flightTab);
    }

    public void selectRoundTripOption() {
        click(roundTripButton);
    }

    /**
     * Method to fill the data related to destination
     *
     * @param destination {@link String}
     */
    public void fillOriginDestination(String origin, String destination) {
        sendKeys(originTextField, origin);
        sendKeys(destinationTextField, destination);
    }

    /**
     * Method to fill the checkin and checkout dates
     *
     * @param checkinDate  {@link LocalDate}
     * @param checkoutDate {@link LocalDate}
     */
    public void fillDates(LocalDate checkinDate, LocalDate checkoutDate) {
        click(checkinDateTextField);
        selectDate(checkinDate, false);
        click(checkoutDateTextField);
        selectDate(checkoutDate, false);
    }

    /**
     * Method with the logic to select adults
     *
     * @param amountAdults {@link Integer}
     */
    public void selectAmountAdults(int amountAdults) {
        selectFromDropdownByValue(adultsDropdown, Integer.toString(amountAdults));
    }

    /**
     * Method with the logic to select children
     */
    public void selectAmountChildren(int amountChildren) {
        selectFromDropdownByValue(childrenDropdown, Integer.toString(amountChildren));
    }

    public void selectAgeChildren(List<Integer> listAgeChildren) {
        for (int i = 0; i < listAgeChildren.size(); i++)
            selectFromDropdownByValue(agesDropdown.get(i), Integer.toString(listAgeChildren.get(i)));
    }

    /**
     * Recursive method with the logic to select a date in the calendar
     *
     * @param date          {@link LocalDate}
     * @param keepSearching {@link Boolean}
     */
    private void selectDate(LocalDate date, boolean keepSearching) {
        if (keepSearching) clickClickable(nextMonthCalendarButton);
        waitElementVisibility(calendarWidget);
        for (WebElement day : daysCalendar) {
            LocalDate dayDate = buildDateForCalendar(day);
            if (dayDate.getMonthValue() < date.getMonthValue()) {
                selectDate(date, true);
                return;
            } else if (dayDate.isEqual(date)) {
                clickClickable(day);
                return;
            }
        }
    }

    /**
     * Method to confirm the search and go to {@link ResultsPage}
     *
     * @return ResultsPage
     * @apiNote Assertion
     */
    public ResultsPage confirmSearch() {
        click(searchButton);
        return new ResultsPage(getDriver());
    }

    /**
     * Method to verify the search button text
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifySearchButtonText() {
        return searchButton.getText();
    }

    /**
     * Method to verify the destination text field presence
     *
     * @return : {@link Boolean}
     * @apiNote Assertion
     */
    public boolean verifyDestinationTFPresence() {
        return destinationTextField.isDisplayed();
    }

    /**
     * Method to verify the dates text field presence
     *
     * @return : {@link Boolean}
     * @apiNote Assertion
     */
    public boolean verifyDatesTFPresence() {
        return checkinDateTextField.isDisplayed() && checkoutDateTextField.isDisplayed();
    }

    /**
     * Method to verify the guests text field presence
     *
     * @return : {@link Boolean}
     * @apiNote Assertion
     */
    public boolean verifyGuestTFPresence() {
        return guestsTextField.isDisplayed();
    }


}
