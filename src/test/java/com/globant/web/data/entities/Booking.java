package com.globant.web.data.entities;

import lombok.*;

/**
 * Class to represent the Booking data
 *
 * @author Sebastian Mesa
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private SearchOptions searchOptions;
    private Hotel hotel;
    private Person person;
}
