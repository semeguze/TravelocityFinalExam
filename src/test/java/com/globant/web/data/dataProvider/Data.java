package com.globant.web.data.dataProvider;

import com.globant.web.data.entities.*;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Class provide data as Data Provider class
 *
 * @author Sebastian Mesa
 */
public class Data {

    @DataProvider(name = "bookingSearchOptions")
    public Object[][] bookingSearchOptions() {

        LocalDate today = LocalDate.now();
        LocalDate checkinDate = today.plusDays(60 - 1);
        LocalDate checkoutDate = checkinDate.plusDays(15 - 1);

        return new Object[][]{{
                Booking.builder().searchOptions(
                        SearchOptions.builder()
                                .originCode("LAS").destinationCode("LAX")
                                .checkinDate(checkinDate).checkoutDate(checkoutDate).amountAdults(3)
                                .amountRooms(1).amountChildren(0).agesChildren(Arrays.asList()).build()).person(
                        Person.builder()
                                .firstName("Sebastian").lastName("Mesa").email("sebastian@somemail.com").zipCode("1101")
                                .countryCode("co").phone("3016667788").city("Bogotá").address("123 street")
                                .card(
                                        CreditCard.builder()
                                                .holderName("SEBASTIAN MESA").cardType("Visa")
                                                .cardNumber("4770353152360510").monthExpiration("10")
                                                .yearExpiration("2021").cardCVC("123").build())
                                .build()).selectOptions(
                        SelectOptions.builder().optionDeparture(1).optionReturn(3).build()).build(),
                2
        }};
    }

    @DataProvider(name = "sortingOptions")
    public Object[][] sortingOptions() {
        return new Object[][]{{Arrays.asList("Price (Lowest)", "Price (Highest)",
                "Duration (Shortest)", "Duration (Longest)", "Departure (Earliest)",
                "Departure (Latest)", "Arrival (Earliest)", "Arrival (Latest)")
        }};
    }

}
