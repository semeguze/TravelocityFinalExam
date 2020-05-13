package com.globant.web.pages;

import com.globant.web.data.entities.Person;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Represents the Personal Data Page.
 *
 * @author Sebastian Mesa
 */
public class PersonalDataPage extends BasePage {

    // Labels
    @FindBy(className = "bp_hotel_name_title")
    private WebElement nameHotelLabel;
    @FindBy(className = "bui-nav-progress__title")
    private List<WebElement> listProgressStepsLabels;

    // TextFields
    @FindBy(id = "firstname")
    private WebElement firstNameTextField;
    @FindBy(id = "lastname")
    private WebElement lastNameTextField;
    @FindBy(id = "email")
    private WebElement emailTextField;
    @FindBy(id = "email_confirm")
    private WebElement emailConfirmTextField;

    // Buttons
    @FindBy(className = "submit_holder_button")
    private WebElement continueButton;
    @FindBy(css = ".modal-wrapper button")
    private WebElement closeModalButton;

    // Containers
    @FindBy(css = ".modal-wrapper.bp_leaving_users_light_box")
    private WebElement modalContainer;

    /**
     * Constructor.
     *
     * @param driver : {@link WebDriver}
     */
    public PersonalDataPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to manage the modal logic
     */
    public void manageModal() {
        waitForModalAndCloseIt(modalContainer, closeModalButton);
    }

    /**
     * Method to fill the data related to a person
     *
     * @param person {@link Person}
     */
    public void fillPersonalData(Person person) {
        sendKeys(firstNameTextField, person.getFirstName());
        sendKeys(lastNameTextField, person.getLastName());
        sendKeys(emailTextField, person.getEmail());
        sendKeys(emailConfirmTextField, person.getEmail());
    }

    /**
     * Method to confirm the data and go to {@link FinalDataPage}
     *
     * @return {@link FinalDataPage}
     */
    public FinalDataPage confirmData() {
        click(continueButton);
        return new FinalDataPage(getDriver());
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
     * Method to verify if steps are displayed
     *
     * @return Integer
     * @apiNote Assertion
     */
    public int verifyAmountStepsDisplayed() {
        return listProgressStepsLabels.size();
    }

    /**
     * Method to validate the text of a step
     *
     * @param step {@link Integer}
     * @return String
     */
    public String verifyStepsText(int step) {
        return listProgressStepsLabels.get(step).getText();
    }

}
