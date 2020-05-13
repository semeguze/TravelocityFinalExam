package com.globant.web.data.entities;

import lombok.*;

/**
 * Class to represent a person
 *
 * @author Sebastian Mesa
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String countryCode;
    private String city;
    private String address;
    private String zipCode;
    private CreditCard card;
}
