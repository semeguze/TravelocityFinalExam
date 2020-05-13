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
    @FindBy(css = ".xpb__link:first-child")
    private WebElement sleepTab;

    // TextFields
    @FindBy(id = "ss")
    private WebElement destinationTextField;
    @FindBy(className = "xp__dates__checkin")
    private WebElement checkinDateTextField;
    @FindBy(className = "xp__dates__checkout")
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
    @FindBy(css = ".sb-group__field-adults .bui-stepper__subtract-button")
    private WebElement subtractAdultsButton;
    @FindBy(css = ".sb-group-children .bui-stepper__subtract-button")
    private WebElement subtractKidsButton;
    @FindBy(css = ".sb-group__field-rooms .bui-stepper__subtract-button")
    private WebElement subtractRoomsButton;
    @FindBy(css = ".sb-group__field-adults .bui-stepper__add-button")
    private WebElement addAdultsButton;
    @FindBy(css = ".sb-group-children .bui-stepper__add-button")
    private WebElement addKidsButton;
    @FindBy(css = ".sb-group__field-rooms .bui-stepper__add-button")
    private WebElement addRoomsButton;
    @FindBy(className = "sb-searchbox__button")
    private WebElement searchButton;

    // Dropdown
    @FindBy(css = "[name='age']")
    private WebElement ageKidDropdown;

    // Calendar
    @FindBy(className = "bui-calendar__control--next")
    private WebElement nextMonthCalendarButton;
    @FindBy(className = "bui-calendar")
    private WebElement calendarWidget;
    @FindBy(css = "[class='bui-calendar__date']")
    private List<WebElement> daysCalendar;

    /**
     * Constructor.
     *
     * @param driver : {@link WebDriver}
     */
    public HomePage(WebDriver driver, String url) {
        super(driver);
        driver.get(url);
    }

    /**
     * Method to select the sleep tab
     */
    public void selectSleepTab() {
        click(sleepTab);
    }

    /**
     * Method to fill the data related to destination
     *
     * @param destination {@link String}
     */
    public void fillDestination(String destination) {
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
        selectDate(checkoutDate, false);
    }

    /**
     * Method to fill the guests data
     *
     * @param amountAdults {@link Integer}
     * @param amountRooms  {@link Integer}
     * @param amountKids   {@link Integer}
     * @param ageKid       {@link Integer}
     */
    public void selectGuests(int amountAdults, int amountRooms, int amountKids, int ageKid) {
        click(guestsTextField);
        selectAmountAdults(amountAdults);
        selectAmountRooms(amountRooms);
        selectAmountKids(amountKids, ageKid);
    }

    /**
     * Method with the logic to select children
     *
     * @param amountKids {@link Integer}
     * @param ageKid     {@link Integer}
     */
    private void selectAmountKids(int amountKids, int ageKid) {
        while (Integer.parseInt(amountKidsLabel.getText()) > 0) click(subtractAdultsButton);
        while (Integer.parseInt(amountKidsLabel.getText()) != amountKids) click(addKidsButton);
        selectFromDropdownByValue(ageKidDropdown, Integer.toString(ageKid));
    }

    /**
     * Method with the logic to select adults
     *
     * @param amountAdults {@link Integer}
     */
    private void selectAmountAdults(int amountAdults) {
        while (Integer.parseInt(amountAdultsLabel.getText()) > 1) click(subtractAdultsButton);
        while (Integer.parseInt(amountAdultsLabel.getText()) != amountAdults) click(addAdultsButton);
    }

    /**
     * Method with the logic to select rooms
     *
     * @param amountRooms {@link Integer}
     */
    private void selectAmountRooms(int amountRooms) {
        while (Integer.parseInt(amountRoomsLabel.getText()) > 1) click(subtractAdultsButton);
        while (Integer.parseInt(amountRoomsLabel.getText()) != amountRooms) click(addRoomsButton);
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
            LocalDate dayDate = LocalDate.parse(day.getAttribute("data-date"));
            if (dayDate.getMonthValue() <= (date.getMonthValue() - 1)) {
                selectDate(date, true);
                return;
            } else if (dayDate.isEqual(date)) clickClickable(day);
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
