package com.globant.web.data.dataProvider;

import com.globant.web.data.entities.Booking;
import com.globant.web.data.entities.CreditCard;
import com.globant.web.data.entities.Person;
import com.globant.web.data.entities.SearchOptions;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;

/**
 * Class provide data as Data Provider class
 *
 * @author Sebastian Mesa
 */
public class Data {
    @DataProvider(name = "bookingSearchOptions")
    public Object[][] bookingSearchOptions() {
        LocalDate today = LocalDate.now();
        LocalDate checkinDate = today.plusDays(30 - 1);
        LocalDate checkoutDate = checkinDate.plusDays(15 - 1);

        return new Object[][]{{
                Booking.builder().searchOptions(
                        SearchOptions.builder()
                                .destination("Bogotá, Colombia").checkinDate(checkinDate).checkoutDate(checkoutDate)
                                .amountAdults(3).amountRooms(1).amountChildren(1).ageKid(9).build()).person(
                        Person.builder()
                                .firstName("Sebastian").lastName("Mesa").email("sebastian@somemail.com").zipCode("1101")
                                .countryCode("co").phone("3016667788").city("Bogotá").address("123 street")
                                .card(
                                        CreditCard.builder()
                                                .holderName("SEBASTIAN MESA").cardType("Visa")
                                                .cardNumber("4770353152360510").monthExpiration("10")
                                                .yearExpiration("2021").cardCVC("123").build())
                                .build())
                        .build(),
                2
        }};
    }
}
