package com.globant.web.pages;

import com.globant.web.data.entities.Hotel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
    @FindBy(css = ".sr-usp-overlay__container.is_stuck")
    private WebElement overlayFilterContainer;
    @FindBy(css = ".sr_item.sr_item_new")
    private List<WebElement> listHotelsContainer;

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
        waitElementVisibility(overlayFilterContainer);
        waitElementStaleness(listHotelsContainer.get(0));
    }

    /**
     * Method to filter by five stars
     */
    public void filterByFiveStars() {
        scrollIntoView(fiveStarsCheckBox);
        click(fiveStarsCheckBox);
        waitElementVisibility(overlayFilterContainer);
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
}
