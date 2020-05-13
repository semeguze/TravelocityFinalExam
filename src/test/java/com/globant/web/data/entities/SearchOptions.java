package com.globant.web.data.entities;

import lombok.*;

import java.time.LocalDate;

/**
 * Class to represent the search options
 *
 * @author Sebastian Mesa
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchOptions {
    private String destination;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private int amountAdults;
    private int amountRooms;
    private int amountChildren;
    private int ageKid;
}