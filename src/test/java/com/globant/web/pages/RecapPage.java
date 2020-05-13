package com.globant.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the Recap Page.
 *
 * @author Sebastian Mesa
 */
public class RecapPage extends BasePage {

    // Labels
    @FindBy(id = "hp_hotel_name")
    private WebElement nameHotelLabel;
    @FindBy(className = "bui-review-score__badge")
    private WebElement scoreHotelLabel;
    @FindBy(css = ".totalPrice .bui-price-display__value")
    private WebElement totalPriceHotelLabel;
    @FindBy(id = "av-summary-checkin")
    private WebElement dateCheckinLabel;
    @FindBy(id = "av-summary-checkout")
    private WebElement dateCheckoutLabel;
    @FindBy(id = "av-summary-occupancy")
    private WebElement amountGuestsLabel;

    // Buttons
    @FindBy(css = ".hprt-reservation-cta button")
    private WebElement reserveConfirmationButton;

    // Dropdowns
    @FindBy(css = "#hprt-table tbody tr:first-child select")
    private WebElement roomDropdown;

    /**
     * Constructor.
     *
     * @param driver : {@link WebDriver}
     */
    public RecapPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to confirm reservation and go to {@link PersonalDataPage}
     *
     * @param amountRooms {@link Integer}
     * @return PersonalDataPage
     */
    public PersonalDataPage confirmReservation(int amountRooms) {
        selectFromDropdownByValue(roomDropdown, Integer.toString(amountRooms));
        click(reserveConfirmationButton);
        return new PersonalDataPage(getDriver());
    }

    /**
     * Method to verify the name of the hotel
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyNameHotel() {
        return nameHotelLabel.getText();
    }

    /**
     * Method to verify the price of the hotel
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyTotalPriceHotel() {
        return totalPriceHotelLabel.getText();
    }

    /**
     * Method to verify the amount of adults
     *
     * @return : {@link Integer}
     * @apiNote Assertion
     */
    public int verifyAmountAdults() {
        return getNumbersFromSting(amountGuestsLabel.getText())[0];
    }

    /**
     * Method to verify the amount of children
     *
     * @return : {@link Integer}
     * @apiNote Assertion
     */
    public int verifyAmountChildren() {
        return getNumbersFromSting(amountGuestsLabel.getText())[1];
    }

    /**
     * Method to verify the checkin date
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyCheckinDate() {
        return dateCheckinLabel.getText();
    }

    /**
     * Method to verify the checkout date
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyCheckoutDate() {
        return dateCheckoutLabel.getText();
    }

}
