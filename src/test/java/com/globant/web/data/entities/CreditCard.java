package com.globant.web.data.entities;

import lombok.*;

/**
 * Class to represent a credit card
 *
 * @author Sebastian Mesa
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    private String holderName;
    private String cardType;
    private String cardNumber;
    private String monthExpiration;
    private String yearExpiration;
    private String cardCVC;
}
