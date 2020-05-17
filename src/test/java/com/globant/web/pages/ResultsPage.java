package com.globant.web.pages;

import com.globant.web.data.entities.Hotel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Represents the Results Page.
 *
 * @author Sebastian Mesa
 */
public class ResultsPage extends BasePage {

    // Labels
    @FindBy(css = ".sr_header--title h1")
    private WebElement searchTitleLabel;

    // Checkboxes
    @FindBy(css = "#filter_price [data-id='pri-5'] .css-checkbox")
    private WebElement mostExpensivePriceCheckBox;
    @FindBy(css = "#filter_class [data-id='class-5'] .css-checkbox")
    private WebElement fiveStarsCheckBox;

    // Containers
    @FindBy(className = "flight-module")
    private List<WebElement> listHotelsContainer;

    // Dropdown
    @FindBy(id = "sortDropdown")
    private WebElement sortingDropdown;

    /**
     * Constructor.
     *
     * @param driver : {@link WebDriver}
     */
    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to filter by max price
     */
    public void filterByMaxPrice() {
        scrollIntoView(mostExpensivePriceCheckBox);
        click(mostExpensivePriceCheckBox);
        //waitElementVisibility(overlayFilterContainer);
        waitElementStaleness(listHotelsContainer.get(0));
    }

    /**
     * Method to filter by five stars
     */
    public void filterByFiveStars() {
        scrollIntoView(fiveStarsCheckBox);
        click(fiveStarsCheckBox);
        //waitElementVisibility(overlayFilterContainer);
        waitElementStaleness(listHotelsContainer.get(0));
    }

    /**
     * Method to search an hotel by number option
     *
     * @param optionToBeSelected {@link Integer}
     * @return Hotel
     */
    public Hotel searchHotel(int optionToBeSelected) {
        WebElement hotelToBeSelected = listHotelsContainer.get(optionToBeSelected - 1);
        waitElementVisibility(hotelToBeSelected);
        return Hotel.builder()
                .name(hotelToBeSelected.findElement(By.className("sr-hotel__name")).getText())
                .place(hotelToBeSelected.findElement(By.className("bui-link")).getText())
                .score(hotelToBeSelected.findElement(By.className("bui-review-score__badge")).getText())
                .price(hotelToBeSelected.findElement(By.className("bui-price-display__value")).getText())
                .build();
    }

    /**
     * Method to confirm hotel selection and go to {@link RecapPage}
     *
     * @param optionToBeSelected {@link Integer}
     * @return RecapPage
     */
    public RecapPage confirmHotelSelection(int optionToBeSelected) {
        WebElement hotelSelected = listHotelsContainer.get(optionToBeSelected - 1);
        click(hotelSelected.findElement(By.className("sr_cta_button")));
        moveToNewTab();
        return new RecapPage(getDriver());
    }

    /**
     * Method to verify the fields for each hotel
     *
     * @return : {@link Boolean}
     * @apiNote Assertion
     */
    public boolean verifyBasicFieldsInResults() {
        for (WebElement hotel : listHotelsContainer)
            if (!hotel.findElement(By.className("sr-hotel__name")).isDisplayed() ||
                    !hotel.findElement(By.className("bui-review-score__badge")).isDisplayed() ||
                    !hotel.findElement(By.className("bui-price-display__value")).isDisplayed()) return false;
        return true;
    }

    /**
     * Method to verify the amount of hotel results
     *
     * @return : {@link Boolean}
     * @apiNote Assertion
     */
    public boolean verifyAmountResults() {
        return listHotelsContainer.isEmpty();
    }

    public boolean verifySortOptions(List<String> expectedSortingOptions) {
        return areValuesInTheDropdown(sortingDropdown, expectedSortingOptions);
    }

    public boolean verifySelectButtonPresence() {
        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[data-test-id='offer-listing']")));
        System.out.println("Hotels : " + listHotelsContainer.size());
        for (WebElement hotel : listHotelsContainer)
            if (!hotel.findElement(By.cssSelector(".grid-container button")).isDisplayed()) return false;
        return true;
    }

    public boolean verifyFlightDurationPresence() {
        for (WebElement hotel : listHotelsContainer)
            if (!hotel.findElement(By.className("duration-emphasis")).isDisplayed()) return false;
        return true;
    }

    public boolean verifyFlightFeesAndDetailsPresence() {
        for (WebElement hotel : listHotelsContainer)
            click(hotel.findElement(By.className("show-flight-details")));
        return true;
    }

    public void sortResults(String sortingValue) {
        selectFromDropdownByValue(sortingDropdown, sortingValue);
        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[data-test-id='offer-listing']")));
    }

    public boolean verifyListIsSorted() {
        for (WebElement hotel : listHotelsContainer)
            System.out.println("===============\n"+hotel.getText());
        return true;
    }
}
