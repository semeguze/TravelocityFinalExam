package com.globant.web.tests;

import com.globant.web.data.dataProvider.Data;
import com.globant.web.data.entities.Booking;
import com.globant.web.data.entities.SearchOptions;
import com.globant.web.pages.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.globant.web.utils.errorMessages.ErrorFinalDataPage.*;
import static com.globant.web.utils.errorMessages.ErrorHomePage.*;
import static com.globant.web.utils.errorMessages.ErrorPersonalDataPage.*;
import static com.globant.web.utils.errorMessages.ErrorRecapPage.*;
import static com.globant.web.utils.errorMessages.ErrorResultsPage.ERROR_LIST_HOTELS_EMPTY;

/**
 * Suite to test the booking flow.
 *
 * @author Sebastian Mesa
 */
@Slf4j
public class BookingSuite extends BaseTest {

    @Test(description = "Test to validate booking", dataProvider = "bookingSearchOptions", dataProviderClass = Data.class)
    public void validateBooking(Booking booking, int hotelToSelect) {

        // ************************************************************************************************************
        // HOME PAGE

        HomePage homePage = getHomePage();
        homePage.selectSleepTab();

        SearchOptions options = booking.getSearchOptions();
        log.info("Searching destination : {} ", options.getDestination());
        Assert.assertTrue(homePage.verifyDestinationTFPresence(), ERROR_TEXTFIELD_DESTINATION_PRESENCE);
        homePage.fillDestination(options.getDestination());
        log.info("Selecting dates. Checkin date : {} & Checkout Date : {}", options.getCheckinDate(), options.getCheckoutDate());
        Assert.assertTrue(homePage.verifyDatesTFPresence(), ERROR_TEXTFIELD_DATES_PRESENCE);
        homePage.fillDates(options.getCheckinDate(), options.getCheckoutDate());
        log.info("Selecting guests. Total people : {} & Rooms : {}", options.getAmountAdults() + options.getAmountChildren(), options.getAmountRooms());
        Assert.assertTrue(homePage.verifyGuestTFPresence(), ERROR_TEXTFIELD_GUESTS_PRESENCE);
        homePage.selectGuests(options.getAmountAdults(), options.getAmountRooms(), options.getAmountChildren(), options.getAgeKid());
        Assert.assertEquals(homePage.verifySearchButtonText(), "Buscar", ERROR_BUTTON_SEARCH_TEXT);

        // ************************************************************************************************************
        // RESULTS PAGE
        ResultsPage resultsPage = homePage.confirmSearch();

        log.info("Filtering results by 5 stars");
        resultsPage.filterByFiveStars();
        Assert.assertFalse(resultsPage.verifyAmountResults(), ERROR_LIST_HOTELS_EMPTY);
        Assert.assertTrue(resultsPage.verifyBasicFieldsInResults());

        log.info("Selecting the result #{}", hotelToSelect);
        booking.setHotel(resultsPage.searchHotel(hotelToSelect));

        // ************************************************************************************************************
        // RECAP DATA PAGE
        RecapPage recapPage = resultsPage.confirmHotelSelection(hotelToSelect);

        Assert.assertTrue(recapPage.verifyNameHotel().contains(booking.getHotel().getName()), ERROR_LABEL_HOTEL_NAME_TEXT);
        Assert.assertEquals(recapPage.verifyTotalPriceHotel(), booking.getHotel().getPrice(), ERROR_LABEL_HOTEL_PRICE_TEXT);
        Assert.assertEquals(recapPage.verifyAmountAdults(), booking.getSearchOptions().getAmountAdults(), ERROR_LABEL_ADULTS_AMOUNT_TEXT);
        Assert.assertEquals(recapPage.verifyAmountChildren(), booking.getSearchOptions().getAmountChildren(), ERROR_LABEL_CHILDREN_AMOUNT_TEXT);
        Assert.assertTrue(recapPage.verifyCheckinDate().contains(Integer.toString(booking.getSearchOptions().getCheckinDate().getDayOfMonth())), ERROR_LABEL_DATE_CHECKIN_TEXT);
        Assert.assertTrue(recapPage.verifyCheckoutDate().contains(Integer.toString(booking.getSearchOptions().getCheckoutDate().getDayOfMonth())), ERROR_LABEL_DATE_CHECKOUT_TEXT);

        log.info("Confirming selection of : {}", booking.getHotel().getName());

        // ************************************************************************************************************
        // PERSONAL DATA PAGE
        PersonalDataPage personalDataPage = recapPage.confirmReservation(booking.getSearchOptions().getAmountRooms());
        personalDataPage.manageModal();

        Assert.assertEquals(personalDataPage.verifyNameHotel(), booking.getHotel().getName(), ERROR_LABEL_HOTEL_NAME_TEXT__RECAP);
        Assert.assertEquals(personalDataPage.verifyAmountStepsDisplayed(), 3, ERROR_LIST_STEPS_AMOUNT_EMPTY);
        Assert.assertTrue(personalDataPage.verifyStepsText(0).equals("Elige tu habitación")
                || personalDataPage.verifyStepsText(0).equals("Tu apartamento"), ERROR_LABEL_STEP_ONE_TEXT);
        Assert.assertEquals(personalDataPage.verifyStepsText(1), "Escribe tus datos", ERROR_LABEL_STEP_TWO_TEXT);
        Assert.assertEquals(personalDataPage.verifyStepsText(2), "Últimos datos", ERROR_LABEL_STEP_THREE_TEXT);

        log.info("Filling personal data");
        personalDataPage.fillPersonalData(booking.getPerson());

        // ************************************************************************************************************
        // FINAL DATA PAGE
        FinalDataPage finalDataPage = personalDataPage.confirmData();
        finalDataPage.manageModal();

        log.info("Filling final data");
        finalDataPage.fillFinalData(booking.getPerson());
        finalDataPage.fillCreditCardData(booking.getPerson().getCard());

        log.info("Testing multiple page validations as requested");
        log.info("1. Asserting Address tittle contains 'Introduce'");
        Assert.assertTrue(finalDataPage.verifyAddressLabel().contains("Introduce"), ERROR_LABEL_ADDRESS_TEXT);
        log.info("2. Asserting Payment tittle equals to '¿Cómo te gustaría pagar?'");
        Assert.assertEquals(finalDataPage.verifyPaymentLabel(), "¿Cómo te gustaría pagar?", ERROR_LABEL_PAYMENT_TEXT);
        log.info("3. Asserting Payment info label equals to 'No te cobraremos nada todavía, so....'");
        Assert.assertEquals(finalDataPage.verifyPaymentInfoLabel(),
                "No te cobraremos nada todavía, solo necesitamos los datos de tu tarjeta para garantizar tu reserva.", ERROR_LABEL_PAYMENT_INFO_TEXT);
        log.info("4. Asserting wrong or empty card holder name label equals to 'Introduce el nombre del titular de la tarjeta'");
        Assert.assertEquals(finalDataPage.verifyEmptyHolderNameAlertLabel(), "Introduce el nombre del titular de la tarjeta", ERROR_LABEL_WRONG_ACCOUNT_HOLDER_TEXT);
        log.info("5. Asserting the second card number label is displayed with a correct card number");
        Assert.assertTrue(finalDataPage.verifySecondCardNumberLabel(booking.getPerson().getCard().getCardNumber()), ERROR_LABEL_SECOND_CARD_NUMBER_TEXT);
        log.info("6. Asserting the second card number label is displayed with a wrong card number");
        Assert.assertTrue(finalDataPage.verifySecondCardNumberLabel("47703531"), ERROR_LABEL_SECOND_CARD_NUMBER_TEXT);
        log.info("7. Asserting name of the hotel is equals to : {}", booking.getHotel().getName());
        Assert.assertEquals(finalDataPage.verifyHotelNameLabel(), booking.getHotel().getName(), ERROR_LABEL_HOTEL_NAME_TEXT__FINAL_DATA);
        log.info("8. Asserting name of the tab contains Booking.com");
        Assert.assertTrue(finalDataPage.getTabName().contains("Booking.com"), ERROR_TITTLE_TAB_TEXT);
        log.info("9. Asserting as not equals the name for help label");
        Assert.assertNotEquals(finalDataPage.verifyHelpLabel(), "ABCD HELP", ERROR_LABEL_HELP_TEXT);
        log.info("10. Asserting credit card type dropdown doesn't contains: Apple e-Card");
        Assert.assertFalse(finalDataPage.verifyOptionsTypeCard("Apple e-Card"), ERROR_SELECT_OPTION_TEXT);
        log.info("11. Asserting the email displayed equals to : {}", booking.getPerson().getEmail());
        Assert.assertEquals(finalDataPage.verifyEmailDisplayed(), booking.getPerson().getEmail(), ERROR_LABEL_EMAIL_TEXT__FINAL_DATA);
        log.info("12. Asserting the name of the person displayed equals to : {}", booking.getPerson().getFirstName() + " " + booking.getPerson().getLastName());
        Assert.assertEquals(finalDataPage.verifyNamePersonDisplayed(), booking.getPerson().getFirstName() + " " + booking.getPerson().getLastName(), ERROR_LABEL_NAME_TEXT__FINAL_DATA);
    }
}
