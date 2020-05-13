package com.globant.web.pages;

import com.globant.web.data.entities.CreditCard;
import com.globant.web.data.entities.Person;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Represents the Final Data Page.
 *
 * @author Sebastian Mesa
 */
public class FinalDataPage extends BasePage {

    // Dropdowns
    @FindBy(id = "cc1")
    private WebElement countryDropdown;
    @FindBy(id = "cc_type")
    private WebElement cardTypeDropdown;
    @FindBy(id = "cc_month")
    private WebElement cardMonthExpirationDropdown;
    @FindBy(id = "ccYear")
    private WebElement cardYearExpirationDropdown;

    // TextFields
    @FindBy(id = "phone")
    private WebElement phoneTextField;
    @FindBy(id = "address1")
    private List<WebElement> addressTextField;
    @FindBy(id = "city")
    private List<WebElement> cityTextField;
    @FindBy(id = "zip")
    private List<WebElement> zipTextField;
    @FindBy(id = "cc_name")
    private WebElement cardHolderTextField;
    @FindBy(id = "cc_number")
    private WebElement cardNumberTextField;
    @FindBy(id = "cc_cvc")
    private List<WebElement>  cvcTextField;

    // Labels
    @FindBy(className = "bp_hotel_name_title")
    private WebElement hotelNameLabel;
    @FindBy(className = "personal_details_reassurance_email_text")
    private WebElement emailLabel;
    @FindBy(css = ".field_name_full_name .book-form-field-value")
    private WebElement namePersonLabel;
    @FindBy(id = "booker-phone-help-text")
    private WebElement phoneHelpMessageLabel;
    @FindBy(css = ".booker-details h2")
    private WebElement addressTittleLabel;
    @FindBy(css = ".payments_block_container_title .bp_legacy_form_box__title")
    private WebElement paymentTittleLabel;
    @FindBy(css = ".payment-method__contents-policy-holder")
    private WebElement paymentInfoLabel;
    @FindBy(className = "label_cc_number__format")
    private WebElement secondCardNumberLabel;
    @FindBy(css = "#book_credit_card div:first-child p")
    private WebElement wrongAccountHolderNameLabel;
    @FindBy(css = ".bp_no_card_hint.bp_no_card_hint-margin")
    private WebElement helpLabel;

    // Buttons
    @FindBy(css = ".modal-wrapper button")
    private WebElement closeModalButton;
    @FindBy(className = "submit_holder_button")
    private WebElement continueButton;

    // Containers
    @FindBy(css = ".modal-wrapper.bp_leaving_users_light_box")
    private WebElement modalContainer;

    /**
     * Constructor
     *
     * @param driver : {@link WebDriver}
     */
    public FinalDataPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to manage the modal logic
     */
    public void manageModal() {
        waitForModalAndCloseIt(modalContainer, closeModalButton);
    }

    /**
     * Method to complete the final data from a Person
     *
     * @param person : {@link Person}
     */
    public void fillFinalData(Person person) {
        if (!cityTextField.isEmpty()) sendKeys(cityTextField.get(0), person.getCity());
        if (!addressTextField.isEmpty()) sendKeys(addressTextField.get(0), person.getAddress());
        if (!zipTextField.isEmpty()) sendKeys(zipTextField.get(0), person.getZipCode());
        selectFromDropdownByValue(countryDropdown, person.getCountryCode());
        sendKeys(phoneTextField, person.getPhone());
    }

    /**
     * Method to complete the credit card data
     *
     * @param creditCard : {@link CreditCard}
     */
    public void fillCreditCardData(CreditCard creditCard) {
        clearAndSendKeys(cardHolderTextField, creditCard.getHolderName());
        selectFromDropdownByValue(cardTypeDropdown, creditCard.getCardType());
        sendKeys(cardNumberTextField, creditCard.getCardNumber());
        selectFromDropdownByValue(cardMonthExpirationDropdown, creditCard.getMonthExpiration());
        selectFromDropdownByValue(cardYearExpirationDropdown, creditCard.getYearExpiration());
        if (!cvcTextField.isEmpty()) sendKeys(cvcTextField.get(0), creditCard.getCardCVC());
    }

    /**
     * Method to verify the address label
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyAddressLabel() {
        return addressTittleLabel.getText();
    }

    /**
     * Method to verify the payment label
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyPaymentLabel() {
        return paymentTittleLabel.getText();
    }

    /**
     * Method to verify the payment info label
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyPaymentInfoLabel() {
        return paymentInfoLabel.getText();
    }

    /**
     * Method to verify the second card number label after input
     *
     * @param creditCardNumber {@link String}
     * @return boolean is second card number is displayed
     * @apiNote Assertion
     */
    public boolean verifySecondCardNumberLabel(String creditCardNumber) {
        clearAndSendKeys(cardNumberTextField, creditCardNumber);
        return secondCardNumberLabel.isDisplayed();
    }

    /**
     * Method to verify the alert holder name label
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyEmptyHolderNameAlertLabel() {
        clearAndSendKeys(cardHolderTextField, "");
        click(paymentInfoLabel);
        waitElementVisibility(wrongAccountHolderNameLabel);
        return wrongAccountHolderNameLabel.getText();
    }

    /**
     * Method to verify the holder name label
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyHotelNameLabel() {
        return hotelNameLabel.getText();
    }

    /**
     * Method to verify the email displayed
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyEmailDisplayed() {
        return emailLabel.getText();
    }

    /**
     * Method to verify the name or person displayed
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyNamePersonDisplayed() {
        return namePersonLabel.getText();
    }

    /**
     * Method to verify the help label
     *
     * @return : {@link String}
     * @apiNote Assertion
     */
    public String verifyHelpLabel() {
        return helpLabel.getText();
    }

    /**
     * Method to verify options in the dropdown for credit card
     *
     * @return : {@link Boolean}
     * @apiNote Assertion
     */
    public boolean verifyOptionsTypeCard(String cardType) {
        for (WebElement element : new Select(cardTypeDropdown).getOptions())
            if (element.getText().equals(cardType)) return true;
        return false;
    }

}
